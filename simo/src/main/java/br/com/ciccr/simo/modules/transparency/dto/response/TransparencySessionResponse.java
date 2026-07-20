package br.com.ciccr.simo.modules.transparency.dto.response;

public record TransparencySessionResponse(

        String cookie,
        String viewState,
        String windowId

) {
}