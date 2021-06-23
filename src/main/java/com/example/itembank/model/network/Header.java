package com.example.itembank.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    // api 통신시간
    private LocalDateTime transactionTime;

    // api 응답 코드
    private String resultCode;

    // api 부가 설명
    private String description;

    @Valid
    private T data;

    // pagination
    private Pagination pagination;

    // OK
    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(HttpStatus.OK.toString())
                .description(HttpStatus.OK.toString())
                .build();
    }


    // DATA OK
    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(HttpStatus.OK.toString())
                .description(HttpStatus.OK.toString())
                .data(data)
                .build();
    }

    public static <T> Header<T> OK(T data, Pagination pagination){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(HttpStatus.OK.toString())
                .description(HttpStatus.OK.toString())
                .data(data)
                .pagination(pagination)
                .build();
    }

    // ERROR
    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .description(description)
                .build();
    }
}
