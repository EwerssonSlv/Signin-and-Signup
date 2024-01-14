async function login() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    const userData = {
        email: email,
        password: password,
    };

    try {
        const response = await fetch("http://localhost:8082/signin", {
            mode:"cors",
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "*/*",
                "Access-Control-Allow-Origin": "*"
            },
            body: JSON.stringify(userData),
        });        

        if (response.ok) {
            response.json().then(data => {
                const token = data.token; 

                //console.log("token: ", token);

                localStorage.setItem("token", token);
                showToast("#okToast");

               // window.setTimeout(function () {
               //     window.location.href = "../index.html";  // Logic to direct the user to the homepage of a website
               // }, 2000);

            });
        } else {
            showToast("#errorToast");
        }
        
    } catch (error) {
        console.error("Error while logging in:", error);
        showToast("#errorToast");
    }
}

function showToast(id) {
    var toastElList = [].slice.call(document.querySelectorAll(id));
    var toastList = toastElList.map(function (toastEl) {
        return new bootstrap.Toast(toastEl);
    });
    toastList.forEach((toast) => toast.show());
}