package br.com.ciccr.simo.common.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiError(

        LocalDateTime timestamp,

        Integer status,

        String error,

        String message,

        String path,

        List<ValidationError> errors

) {
}