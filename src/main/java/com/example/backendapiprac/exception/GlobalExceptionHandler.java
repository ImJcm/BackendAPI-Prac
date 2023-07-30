package com.example.backendapiprac.exception;

import com.example.backendapiprac.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(
          apiResponseDto,
          HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResponseDto> handleNotFoundException(NotFoundException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NotOwnerException.class})
    public ResponseEntity<ApiResponseDto> handleNotFoundException(NotOwnerException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }
}
