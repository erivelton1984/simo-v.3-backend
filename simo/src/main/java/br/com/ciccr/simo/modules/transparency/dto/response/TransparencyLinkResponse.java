package br.com.ciccr.simo.modules.transparency.dto.response;

import br.com.ciccr.simo.modules.transparency.enums.EmploymentStatus;
import br.com.ciccr.simo.modules.transparency.enums.LinkType;

public record TransparencyLinkResponse(

        String institution,
        String position,
        String functionalFramework,

        EmploymentStatus employmentStatus,

        LinkType linkType

) {
}