package br.com.ciccr.simo.modules.role.model;

import br.com.ciccr.simo.common.audit.Auditable;
import br.com.ciccr.simo.common.model.BaseEntity;
import br.com.ciccr.simo.modules.role.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private RoleName name;

    @Column(length = 255)
    private String description;

}