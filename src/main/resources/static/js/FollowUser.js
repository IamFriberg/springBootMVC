$(document).ready(function() {

    var url = window.location;

    // TOGGLE SUBSRIPTION
    $(":button").click(function() {
        var self = $(this);
        if(self.text() == "Follow") {
            ajaxPost(self.attr("value"));
            self.text('Unfollow');
            self.removeClass("btn btn-success");
            self.addClass("btn btn-danger");
        } else if(self.text() == "Unfollow") {
            ajaxDelete(self.attr("value"));
            self.text('Follow');
            self.removeClass("btn btn-danger");
            self.addClass("btn btn-success");
        }

    });

    function ajaxPost(userName){
        console.log(userName);
        // DO POST
        $.ajax({
            type : "POST",
            url : url,
            data : {userName : userName},
            success : function(result) {
                //TODO fix better handling
            },
            error : function(e) {
                //TODO fix better handling
                console.log("ERROR: ", e);
            }
        });

    }

    function ajaxDelete(userName){
        console.log(userName);
        // DO POST
        $.ajax({
            type : "DELETE",
            url : url + "?userName=" + userName,
            //data : {userName : userName},
            success : function(result) {
                //TODO fix better handling
            },
            error : function(e) {
                //TODO fix better handling
                console.log("ERROR: ", e);
            }
        });

    }

});