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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /* 회원가입 */
    @Override
    public ResponseEntity<ApiResponseDto> onSignup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        User checkuser = userRepository.findByUsername(username).orElse(null);

        /* 닉네임 중복 체크 */
        if(checkuser != null) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        /* 비밀번호와 비밀번호 확인 일치여부*/
        String password = signupRequestDto.getPassword();
        String checkpassword = signupRequestDto.getCheckpassword();
        if(!password.equals(checkpassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        /* 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입 실패 */
        if(password.length() < 4 || password.contains(username)) {
            throw new IllegalArgumentException("비밀번호가 4자리 이상 또는 닉네임이 포함되지 않아야 합니다.");
        }

        User user = new User(signupRequestDto);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "회원가입 성공"));
    }

    /* 로그인 */
    @Override
    public ResponseEntity<ApiResponseDto> onLogin(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        User checkuserByUsernameByPassword = userRepository.findByUsernameAndPassword(username,password).orElse(null);

       if(checkuserByUsernameByPassword == null) {
           throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요.");
       }

       String token = jwtUtil.createToken(username);

       jwtUtil.addJwtToCookie(token, res);

       return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "로그인 성공", token));
    }
}
