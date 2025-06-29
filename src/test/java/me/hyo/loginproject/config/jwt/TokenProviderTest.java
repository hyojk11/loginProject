package me.hyo.loginproject.config.jwt;

import io.jsonwebtoken.Jwts;
import me.hyo.loginproject.domain.User;
import me.hyo.loginproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken() : 유저 정보, 만료 기간 전달해 토큰 만들기")
    @Test
    void generateToken() {
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken() : 토큰 만료시 유효성 검증 실패하기")
    @Test
    void validToken_invalidToken() {
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        boolean result = tokenProvider.validToken(token);

        assertThat(result).isFalse();
    }

    @DisplayName("validToken() : 토큰 유효시 유효성 검증 성공하기")
    @Test
    void validToken_validToken() {
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        boolean result = tokenProvider.validToken(token);

        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보 가져오기")
    @Test
    void getAuthentication() {
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        Authentication authentication = tokenProvider.getAuthentication(token);

        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserId() : 토큰으로 유저ID 가져오기")
    @Test
    void getUserId() {
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        Long userIdByToken = tokenProvider.getUserId(token);

        assertThat(userIdByToken).isEqualTo(userId);
    }
}
