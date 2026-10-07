package movielog.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import movielog.auth.dto.LoginRequest;
import movielog.auth.dto.LoginResponse;
import movielog.auth.dto.SignupRequest;
import movielog.auth.jwt.JwtTokenProvider;
import movielog.common.exception.DuplicateEmailException;
import movielog.common.exception.InvalidCredentialsException;
import movielog.user.domain.User;
import movielog.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignupRequest request) {

        if(userRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }//if

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User(request.email(), encodedPassword, request.nickname());

        userRepository.save(user);
    }//signup

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("이메일 혹은 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException(
                    "이메일 혹은 비밀번호가 올바르지 않습니다."
            );
        }

        return new LoginResponse(jwtTokenProvider.createToken(user.getEmail()));
    }//login
}
