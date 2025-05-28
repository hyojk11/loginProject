# OAuth2 + JWT 통합 로그인 시스템
> Spring Security 기반의 로그인 시스템으로,  
> 구글 / 네이버 / 카카오 소셜 로그인과 JWT 인증, 리프레시 토큰 발급 기능을 구현했습니다.

---

## 주요 화면

###  1. 로그인 화면
> 구글 / 네이버 / 카카오 버튼으로 간편 로그인 가능
![image](https://github.com/user-attachments/assets/5b361a73-052e-427b-b515-1cad9f6fc8de)

###  2. 회원가입 화면
> 이메일 중복 확인 및 비밀번호 유효성 검사를 포함한 폼
![image](https://github.com/user-attachments/assets/112cd7be-82b2-42dd-a2ac-27a1bf22ddef)

###  3. 메인 화면
> 로그인 성공 후 access token이 발급되고, 메인 페이지로 이동
![image](https://github.com/user-attachments/assets/acff035b-3b1a-4d4c-9d6c-a8fd3a089db9)

---

## 주요 기능 요약

| 기능 | 설명 |
|------|------|
| **소셜 로그인** | Google, Naver, Kakao OAuth2 연동 |
| **JWT 인증** | Access Token + Refresh Token 발급 및 갱신 |
| **토큰 저장소** | AccessToken → LocalStorage, RefreshToken → HttpOnly Cookie |
| **회원 자동 등록** | 최초 로그인 시 사용자 정보(email, name)로 DB 자동 저장 |
| **보안 강화** | Spring Security + BCryptPasswordEncoder 적용 |
| **이메일 중복 확인** | 회원가입 시 실시간 중복 체크 제공 (AJAX) |
| **비밀번호 정규식 검사** | 숫자+영문+특수문자 포함 8자 이상 조건 |

---

## 기술 스택

| 구분 | 내용 |
|------|------|
| Backend | Java 17, Spring Boot 3, Spring Security 6, Spring Data JPA |
| Auth | OAuth2 (구글/네이버/카카오), JWT (jjwt), BCrypt |
| DB | MySQL |
| View | Thymeleaf, Bootstrap 5 |
| 기타 | Git, IntelliJ, GitHub Projects |

---

## 디렉토리 구조 (일부)

```
├── config
│   └── WebOAuthSecurityConfig.java
│   └── TokenAuthenticationFilter.java
├── oauth
│   └── OAuth2UserCustomService.java
│   └── OAuth2SuccessHandler.java
├── service
│   └── UserService.java
│   └── TokenService.java
├── domain
│   └── User.java
│   └── RefreshToken.java
├── controller
│   └── UserApiController.java
│   └── TokenApiController.java
└── ...
```

---

## 구현 포인트 & 학습 내용

- OAuth2 프로토콜 흐름, Redirect URI 처리 원리 이해
- 사용자 정보 응답 구조(JSON)의 서비스별 차이 분석 및 통합 파싱 구현
- JWT의 구조 (Header.Payload.Signature)와 유효성 검증 로직 직접 구현
- SecurityContextHolder와 Authentication 객체 처리 구조 학습
