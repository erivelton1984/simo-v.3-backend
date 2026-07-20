package br.com.ciccr.simo.common.response;

import lombok.Builder;

@Builder
public record ApiResult<T>(
        boolean success,
        String message,
        T data
) {
}