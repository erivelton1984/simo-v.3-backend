package br.com.ciccr.simo.common.controller;

import br.com.ciccr.simo.common.response.ApiResult;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResult<T>> ok(String message, T data) {

        return ResponseEntity.ok(
                ApiResult.<T>builder()
                        .success(true)
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    protected ResponseEntity<ApiResult<Void>> ok(String message) {

        return ResponseEntity.ok(
                ApiResult.<Void>builder()
                        .success(true)
                        .message(message)
                        .build()
        );
    }

    protected <T> ResponseEntity<ApiResult<T>> created(String message, T data) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResult.<T>builder()
                                .success(true)
                                .message(message)
                                .data(data)
                                .build()
                );
    }

    protected ResponseEntity<Void> noContent() {

        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<ApiResult<Page<T>>> page(
            Page<T> page,
            String message) {

        return ResponseEntity.ok(
                ApiResult.<Page<T>>builder()
                        .success(true)
                        .message(message)
                        .data(page)
                        .build()
        );
    }

}