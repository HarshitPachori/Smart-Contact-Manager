// console.log("working.......");
// get the alert message element
const alertMessage = document.getElementById("alert-message");
const alertError = document.getElementById("alert-error");
const alertLogin = document.getElementById("alert-login");
// check if the alert message element exists
if (alertMessage) {
  // set a timeout to hide the alert message after 3 seconds
  setTimeout(() => {
    alertMessage.style.display = "none";
  }, 3000);
}

if (alertError) {
  setTimeout(() => {
    alertError.style.display = "none";
  }, 3000);
}

if (alertLogin) {
  setTimeout(() => {
    alertLogin.style.display = "none";
  }, 3000);
}

// for sidebar
const toggleSidebar = () => {
  // kya sidebar dikh raha hai ?
  if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};
