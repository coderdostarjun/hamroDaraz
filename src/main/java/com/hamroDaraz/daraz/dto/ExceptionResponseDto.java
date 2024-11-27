package com.hamroDaraz.daraz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDto {
    private String apiPath;
    private HttpStatus statusCode;
    private String errorMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime errorTime;
}
