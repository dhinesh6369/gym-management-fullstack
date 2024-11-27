package gym_management.gym_management.services;
import gym_management.gym_management.model.UserDetails;
import gym_management.gym_management.repository.UserDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import gym_management.gym_management.model.MemberEntity;
import gym_management.gym_management.model.TokenEntity;
import gym_management.gym_management.repository.MemberRepository;
import gym_management.gym_management.repository.TokenRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;

    public MemberService(JwtService jwtService){
        this.jwtService=jwtService;
    }
    public ResponseEntity<String> addMember(MemberEntity member) {
    if (member.getEndDate().isBefore(member.getStartDate())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("End date must be after the start date");
    }

    String status = determineStatus(member.getStartDate(), member.getEndDate());
    member.setStatus(status);
    memberRepository.save(member);
    return ResponseEntity.ok("Member added successfully");
}
    

    public MemberEntity getMemberById(int memberId) {
        System.out.println("member ID :"+ memberId);
        return memberRepository.findById(memberId).orElse(null);
    }

    public List<MemberEntity> getAllMembers() {
        return memberRepository.findAll();
    }
    public String updateGymMember(Integer id, MemberEntity updatedMember) {
        return memberRepository.findById(id)
            .map(member -> {
                member.setFirstName(updatedMember.getFirstName());
                member.setLastName(updatedMember.getLastName());
                member.setEmailId(updatedMember.getEmailId());
                member.setPhoneNumber(updatedMember.getPhoneNumber());
                member.setStartDate(updatedMember.getStartDate());
                member.setEndDate(updatedMember.getEndDate());
                String status = determineStatus(updatedMember.getStartDate(), updatedMember.getEndDate());
                member.setStatus(status);
                memberRepository.save(member);
                return "Member updated successfully";
            })
            .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }
    public List<MemberEntity> getActiveMembers() {
        return memberRepository.findByStatusAndEndDateAfter("Active", LocalDate.now());
    }
    public List<MemberEntity> getInActiveMembers() {
        List<String> inactiveStatuses = List.of("Inactive", "Inactive (cancelled)");
        return memberRepository.findByStatusIn(inactiveStatuses);
    }
    public boolean deleteMemberById(int id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String determineStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = LocalDate.now();
        if ((currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.isBefore(endDate) || currentDate.isEqual(endDate))) {
            return "Active";
        } else {
            return "Inactive";
        }
    }

    public void registerUser(UserDetails userDetails) throws Exception {        
        try{

            userDetailsRepository.save(userDetails);
        }catch(Exception e){
            throw  new Exception(e);
        }
    }

    public String loginUser(UserDetails userDetails) throws Exception {
        UserDetails existingUser = userDetailsRepository.findByUsername(userDetails.getUsername());
        
        // If username is not present
        if (existingUser==null) {
            throw new Exception("Invalid username");
        }

        // Validate the password
        if (!existingUser.getUserPassword().equals(userDetails.getUserPassword())) {
            throw new Exception("Wrong password");
        }

        TokenEntity isTokenExists=tokenRepository.findByUserId(existingUser.getId()).orElse(null);
            if(isTokenExists!=null){
               if(jwtService.isTokenExpired(isTokenExists.getToken())){
                String newToken=jwtService.generateToken(userDetails.getUsername());
                isTokenExists.setToken(newToken);
                tokenRepository.save(isTokenExists);
                return isTokenExists.getToken();
               }
               else{
                return isTokenExists.getToken();
               }
            }else{
                String token=jwtService.generateToken(existingUser.getUsername());
                
                TokenEntity userToken= new TokenEntity(existingUser.getId(),existingUser.getUsername(), token,new Date(System.currentTimeMillis() + 120000));
                tokenRepository.save(userToken);
                return token;
            }
    }

}