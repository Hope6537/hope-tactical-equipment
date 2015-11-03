var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {
    for(var i = 0 ; i < 100000;i++){
        for(var j = 0 ; j < 100000 ; j++){
           var total = i * j;
            for(var k = 0 ; k < 1000000 ; k += 10){
                if(total%k == 0){
                }
            }
        }

    }
    res.render('index', {title: 'Express', body: '<h1>HelloWorld</h1>'});
});
router.get("/admin", function (req, res, next) {
    res.render('admin', {title: 'MotherFucker!' , content:'<h1>Guest</h1>'})
})

module.exports = router;
