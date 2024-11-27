package gym_management.gym_management.controller;
import gym_management.gym_management.model.UserDetails;
import gym_management.gym_management.repository.MemberRepository;
import gym_management.gym_management.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gym_management.gym_management.model.MemberEntity;
import gym_management.gym_management.services.MemberService;
import java.time.*;
import java.util.*;
@RestController
@RequestMapping("/api")
// "http://localhost:5500", 
@CrossOrigin(origins={"http://127.0.0.1:5500"})
public class GymController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtService jwtService;
    

    @Autowired
    public GymController(MemberService memberService ,JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService=jwtService;
    }

    @PostMapping("/addMember")
    public ResponseEntity<String> addMember(@RequestBody MemberEntity member) {
        try {
            memberService.addMember(member);
            return new ResponseEntity<>("Member added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add member: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Integer id, @RequestBody MemberEntity updatedMember) {
        memberService.updateGymMember(id, updatedMember);
        return ResponseEntity.ok("Updated successfully");
    }

    @GetMapping("/viewallmembers")
    public List<MemberEntity> getAllMembers()
    {
        return memberService.getAllMembers();
    }
    @GetMapping("/active")
    public ResponseEntity<List<MemberEntity>> getActiveMembers() {
        List<MemberEntity> activeMembers = memberService.getActiveMembers();
        return ResponseEntity.ok(activeMembers);
    }
    @GetMapping("/inactive")
    public ResponseEntity<List<MemberEntity>> getInActiveMembers() {
        List<MemberEntity> inactiveMembers = memberService.getInActiveMembers();
        return ResponseEntity.ok(inactiveMembers);
    }
        @GetMapping("/getMemberById/{id}")
        public ResponseEntity<Object> getMemberById(@PathVariable Integer id) {
        MemberEntity member = memberService.getMemberById(id);
            if (member!=null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            }
        }
        
    @PutMapping("/extendmembership/{id}")
    public ResponseEntity<String> extendMembership(@PathVariable Integer id, @RequestBody MemberEntity request) {
        Optional<MemberEntity> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            MemberEntity member = memberOpt.get();
                if (request.getEndDate().isAfter(member.getEndDate())) {
                member.setEndDate(request.getEndDate());
                member.setStatus(determineStatus(member.getStartDate(), member.getEndDate()));
                memberRepository.save(member);
                return ResponseEntity.ok("Membership extended successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Extend date must be after the current end date");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelMembership(@PathVariable Integer id) {
        Optional<MemberEntity> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            MemberEntity member = memberOpt.get();
            member.setStatus("Inactive (cancelled)");
            member.setEndDate(LocalDate.now());
            memberRepository.save(member);
            return ResponseEntity.ok("Membership cancelled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }


    private String determineStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return "Inactive";
        } else if (today.isAfter(endDate)) {
            return "Inactive";
        } else {
            return "Active";
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMemberById(@PathVariable int id) {

        boolean isDeleted = memberService.deleteMemberById(id);
        if (isDeleted) {
            return ResponseEntity.ok("{\"message\": \"" + "Member deleted successfully" + "\"}");
        } else {
            return ResponseEntity.status(404).body("{\"message\": \"" + "Member not found" + "\"}");
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody UserDetails userDetails){
        try{
            String userName=userDetails.getUsername();
            String userPassword=userDetails.getUserPassword();
            if(userName==null || userName.trim().isEmpty()){
                return new ResponseEntity<>("userName must not be null or empty",HttpStatus.BAD_REQUEST);
            }
            else if(userPassword==null || userPassword.trim().isEmpty()){
                return new ResponseEntity<>("Password must not be null or empty",HttpStatus.BAD_REQUEST);
            }
            
            else{
                memberService.registerUser(userDetails);
                return new ResponseEntity<>("User Account registered successfully",HttpStatus.OK);
            }
            
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<String> loginUSer(@RequestBody UserDetails userDetails){
        try{
            String token=memberService.loginUser(userDetails);
            return new ResponseEntity<>(token,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("{\"message\": \"" + e.getMessage() + "\"}",HttpStatus.BAD_REQUEST);
        }
    }
}