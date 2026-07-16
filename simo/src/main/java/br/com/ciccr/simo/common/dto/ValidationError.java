package br.com.ciccr.simo.common.dto;

import lombok.Builder;

@Builder
public record ValidationError(

        String field,

        String message

) {
}