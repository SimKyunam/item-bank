package com.example.itembank.base.exceptionAdvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String field;

    private String message;

    private String invalidValue;
}
