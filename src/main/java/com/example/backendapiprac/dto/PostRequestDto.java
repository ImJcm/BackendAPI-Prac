package com.example.backendapiprac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String username;
    private String title;
    private String contents;

}
