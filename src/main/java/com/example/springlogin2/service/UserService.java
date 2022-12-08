package com.example.springlogin2.service;

import com.example.springlogin2.domain.User;
import com.example.springlogin2.exepction.AppException;
import com.example.springlogin2.exepction.ErrorCode;
import com.example.springlogin2.repository.UserRepository;
import com.example.springlogin2.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;

    private Long expireTimeMs = 1000 * 60 * 60L;

    public String join(String userName, String password) {
        userRepository.findByUserName(userName).ifPresent(user -> {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "중복된 아이디");
        });
        //userName 중복 check
        //저장
        User user = User.builder()
                .userName(userName)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        userRepository.save(user);
        return "SUCESS";
    }


    public String login(String userName, String password) {
        //userName 없음
        User selectedUser = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(
                ErrorCode.USERNAME_NOT_FOUND, userName + "없습니다."
        ));

        //password 틀림
        if (!bCryptPasswordEncoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호가 다릅니다.");
        }

        String token = JwtTokenUtil.generateToken(selectedUser.getUserName(), key, expireTimeMs);

        //앞에서 Exception안났으면 토큰 발행


        return token;
    }

}
