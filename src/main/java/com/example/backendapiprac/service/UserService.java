package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.LoginRequestDto;
import com.example.backendapiprac.dto.SignupRequestDto;
import com.example.backendapiprac.entity.User;
import com.example.backendapiprac.jwt.JwtUtil;
import com.example.backendapiprac.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<ApiResponseDto> onSignup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        User checkuser = userRepository.findByUsername(username).orElse(null);

        /* 닉네임 중복 체크 */
        if(checkuser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "중복된 닉네임입니다."));
        }

        /* 비밀번호와 비밀번호 확인 일치여부*/
        String password = signupRequestDto.getPassword();
        String checkpassword = signupRequestDto.getCheckpassword();
        if(!password.equals(checkpassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."));
        }

        /* 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입 실패 */
        if(password.length() < 4 || password.contains(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "비밀번호가 4자리 이상 또는 닉네임이 포함되지 않아야 합니다."));
        }

        User user = new User(signupRequestDto);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "회원가입 성공"));
    }

    /* 로그인 */
    public ResponseEntity<ApiResponseDto> onLogin(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        User checkuserByUsername = userRepository.findByUsername(username).orElse(null);
        User checkuserByPassword = userRepository.findByPassword(password).orElse(null);

       if(checkuserByUsername == null || checkuserByPassword == null) {
           return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(),"닉네임 또는 패스워드를 확인해주세요."));
       }

       String token = jwtUtil.createToken(username);

       jwtUtil.addJwtToCookie(token, res);

       return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "로그인 성공", token));
    }
}
