// console.log("working.......");
// get the alert message element
const alertMessage = document.getElementById("alert-message");
// check if the alert message element exists
if (alertMessage) {
  // set a timeout to hide the alert message after 3 seconds
  setTimeout(() => {
    alertMessage.style.display = "none";
  }, 5000);
}
