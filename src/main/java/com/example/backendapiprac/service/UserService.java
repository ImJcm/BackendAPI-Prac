package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.SignupRequestDto;
import com.example.backendapiprac.entity.User;
import com.example.backendapiprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<ApiResponseDto> onSignup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        User checkuser = userRepository.findByUsername(username);

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
}
