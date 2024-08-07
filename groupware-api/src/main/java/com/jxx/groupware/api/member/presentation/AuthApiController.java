package com.jxx.groupware.api.member.presentation;

import com.jxx.groupware.api.member.application.AuthService;
import com.jxx.groupware.api.member.application.LoginResponse;
import com.jxx.groupware.api.member.application.UserSession;
import com.jxx.groupware.api.member.dto.request.AuthenticationRequest;
import com.jxx.groupware.api.member.dto.request.LoginRequest;
import com.jxx.groupware.api.member.dto.response.LoginResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;
    private static final String COOKIE_KEY_OF_USER_SESSION = "jxx-c-id";
    private static final String COOKIE_DEFAULT_PATH = "/";

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpRequest, HttpServletResponse response) {
        LoginResponse loginResponse = authService.signIn(loginRequest);

        //세션에 저장할 사용자 세션 변환
        UserSession userSession = new UserSession(loginResponse.companyId(),
                loginResponse.companyName(),
                loginResponse.memberId(),
                loginResponse.name(),
                loginResponse.departmentId(),
                loginResponse.departmentName());


        HttpSession session = httpRequest.getSession(true);
        // 한 브라우저에서 여러 계정을 사용할 경우를 위해 처리
        if (!session.isNew()) {
            log.info("session is legacy");
            session.invalidate();
            session = httpRequest.getSession(true);
        }

        session.setMaxInactiveInterval(28800); // 세션 유효 4시간
        String sessionId = session.getId();
        session.setAttribute(sessionId, userSession);
        log.info("sessionId:{} userSession:{}",sessionId, userSession);

        //클라이언트에게 사용자 세션 키 값을 쿠키에 담아 전달
        Cookie cookie = new Cookie(COOKIE_KEY_OF_USER_SESSION, sessionId);
        cookie.setPath(COOKIE_DEFAULT_PATH);
//        cookie.setDomain("main--jxx-gw.netlify.app");
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResult<>(200, loginResponse));
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> expireSession(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        UserSession userSession = authService.getUserSession(httpRequest);
        session.invalidate();
        log.info(" userSession expired {}", userSession);



        return ResponseEntity.ok("success logout");
    }

    @PostMapping("/api/auth/check-authentication")
    public ResponseEntity<?> checkAuthentication(
            @RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest httpRequest) {
        // 쿠키 중 사용자 세션 키를 담은 쿠기 가져오기, 없다면 UNAUTHORIZED 401
        UserSession userSession = authService.getUserSession(httpRequest);
        authService.validateSessionIntegrity(userSession, authenticationRequest);
        log.info("userSession {}", userSession);
        return ResponseEntity.ok(200);
    }
}
