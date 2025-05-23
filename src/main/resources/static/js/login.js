document.addEventListener("DOMContentLoaded", () => {
    const body = document.querySelector("body");
    const signupSuccess = body.dataset.signupSuccess === "true";
    if (signupSuccess) {
        alert("회원가입이 완료되었습니다. 로그인 해주세요.");
    }
});