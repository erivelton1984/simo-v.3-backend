package br.com.ciccr.simo.common.controller;

import br.com.ciccr.simo.common.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {

        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .success(true)
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    protected ResponseEntity<ApiResponse<Void>> ok(String message) {

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message(message)
                        .build()
        );
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<T>builder()
                                .success(true)
                                .message(message)
                                .data(data)
                                .build()
                );
    }

    protected ResponseEntity<Void> noContent() {

        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<ApiResponse<Page<T>>> page(
            Page<T> page,
            String message) {

        return ResponseEntity.ok(
                ApiResponse.<Page<T>>builder()
                        .success(true)
                        .message(message)
                        .data(page)
                        .build()
        );
    }

}