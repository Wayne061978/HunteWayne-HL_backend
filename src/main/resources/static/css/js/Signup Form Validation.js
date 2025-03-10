// Function to validate passwords for all forms
function validatePasswords(formId) {
    const password = document.getElementById(formId + "-password").value;
    const confirmPassword = document.getElementById(formId + "-confirm-password") ?
        document.getElementById(formId + "-confirm-password").value : "";

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }

    if (password.length < 6) {
        alert("Password must be at least 6 characters long.");
        return false;
    }
    return true;
}

// Validate provider signup
document.getElementById("provider-form").addEventListener("submit", function(event) {
    if (!validatePasswords("provider")) {
        event.preventDefault();
    }
});

// Validate nurse signup
document.getElementById("nurse-form").addEventListener("submit", function(event) {
    if (!validatePasswords("nurse")) {
        event.preventDefault();
    }
});

// Validate patient signup
document.getElementById("patient-form").addEventListener("submit", function(event) {
    if (!validatePasswords("patient")) {
        event.preventDefault();
    }
});

// Form validation for all roles
document.addEventListener("DOMContentLoaded", function() {
    const forms = ["provider", "nurse", "patient"];
    forms.forEach(function(role) {
        const form = document.getElementById(role + "-form");
        if (form) {
            form.addEventListener("submit", function(event) {
                const name = document.getElementById(role + "-name").value;
                const email = document.getElementById(role + "-email").value;

                if (name === "") {
                    alert(role.charAt(0).toUpperCase() + role.slice(1) + " name is required.");
                    event.preventDefault();
                    return;
                }

                if (!validateEmail(email)) {
                    alert("Please enter a valid email address.");
                    event.preventDefault();
                    return;
                }

                if (role === "patient") {
                    const dob = document.getElementById("patient-dob").value;
                    const gender = document.getElementById("patient-gender").value;
                    const phone = document.getElementById("patient-phone").value;

                    if (dob === "") {
                        alert("Date of Birth is required.");
                        event.preventDefault();
                        return;
                    }

                    if (gender === "") {
                        alert("Gender is required.");
                        event.preventDefault();
                        return;
                    }

                    if (phone === "") {
                        alert("Phone number is required.");
                        event.preventDefault();
                        return;
                    }
                }
            });
        }
    });
});

// Email validation function
function validateEmail(email) {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return emailPattern.test(email);
}