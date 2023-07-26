package com.example.backendapiprac.entity;

import com.example.backendapiprac.dto.LoginRequestDto;
import com.example.backendapiprac.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String checkpassword;

    public User(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.checkpassword = signupRequestDto.getCheckpassword();
    }

    public User(LoginRequestDto loginRequestDto) {
        this.username = loginRequestDto.getUsername();
        this.password = loginRequestDto.getPassword();
    }
}
