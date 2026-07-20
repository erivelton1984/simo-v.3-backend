package br.com.ciccr.simo.modules.transparency.dto.response;

public record SessionContext(

        String cookie,

        String viewState,

        String windowId

) {
}