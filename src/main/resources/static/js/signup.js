document.addEventListener("DOMContentLoaded", (event) => {

    //아이디 중복체크
    const emailCheckBtn = document.getElementById("emailCheckBtn");

    if(emailCheckBtn) {
        emailCheckBtn.addEventListener("click", function () {
            const emailInput = document.getElementById("email");
            const email = emailInput.value;
            const emailOkMsg =  document.getElementById("emailOkMsg");
            const emailFailMsg =  document.getElementById("emailFailMsg");

            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if(email === "") {
                alert("이메일을 입력해주세요.");
                emailInput.focus();
                return;
            }

            if(!emailPattern.test(email)) {
                alert("올바른 이메일 형식이 아닙니다.");
                emailInput.focus();
                return;
            }

            fetch(`/user/checkEmail?email=${email}`)
                .then(res => res.json())
                .then(data => {
                    if(data.exists) {
                        fail();
                    } else {
                        success();
                    }
                })
                .catch(err => {
                    console.log("에러 발생:", err);
                    fail();
                });

            function success() {
                emailCheckBtn.disabled = true;
                emailInput.readOnly = true;
                if (emailOkMsg) emailOkMsg.style.display = "block";
                if (emailFailMsg) emailFailMsg.style.display = "none";
            }
            function fail() {
                emailCheckBtn.disabled = false;
                emailInput.readOnly = false;
                if (emailOkMsg) emailOkMsg.style.display = "none";
                if (emailFailMsg) emailFailMsg.style.display = "block";
            }
        });
    }

});

    // 이메일 중복 체크여부, 비밀번호 패턴 검사
    function validateForm() {
        const emailCheckBtn = document.getElementById("emailCheckBtn");
        const passwordInput = document.getElementById("password");
        const password = passwordInput.value;

        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if(!emailCheckBtn.disabled) {
            alert('이메일 중복확인이 필요합니다.');
            return  false;
        }

        if(!passwordPattern.test(password)) {
            alert('비밀번호 형식이 맞지 않습니다.');
            return false;
        }
        return true;
    }
