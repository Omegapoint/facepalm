$(document).ready(function () {
    $("#post-image").click(function () {
        console.log("Adding post");
        var modal = new PostModal();
        modal.show();
    });
});