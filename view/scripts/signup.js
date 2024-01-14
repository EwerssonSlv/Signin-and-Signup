async function signup() {
    let username = document.getElementById("username").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
  
    if (!username || !email || !password || !confirmPassword) {
      return; 
    }
  
    if (password !== confirmPassword) {
      alert("Passwords do not match. Please correct.");
      return; 
    }
  
    const userData = {
      username: username,
      email: email,
      password: password,
    };
  
    try {
      const response = await fetch("http://localhost:8082/user", {
        mode: "cors",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Accept": "*/*",
        },
        body: JSON.stringify(userData),
      });
  
      if (response.ok) {
  
        var successToast = new bootstrap.Toast(document.getElementById("successToast"));
        successToast.show();  
        //window.setTimeout(function () {
        //  window.location.href = "../index.html";  // Logic to direct the user to the homepage of a website
        //}, 2000);  
      } else {
        alert("Error registering.");
      }
    } catch (error) {
      console.error("Error registering:", error);
      alert("Error registering.");
    }
  }