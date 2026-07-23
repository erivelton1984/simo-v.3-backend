package br.com.ciccr.simo.modules.transparency.dto.response;

import java.util.List;

public record TransparencySearchResult(

        String viewState,

        List<TransparencySearchItem> items

) {
}