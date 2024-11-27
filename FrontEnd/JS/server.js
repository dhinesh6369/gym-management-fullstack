const express = require('express');
const app = express();
app.use(express.static('public'));
app.get('/', (req, res) => {
    res.redirect('/signup.html');
});
app.listen(5500, () => {
    console.log('Server running on http://localhost:5500/');
});