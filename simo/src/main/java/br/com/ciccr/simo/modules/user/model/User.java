package br.com.ciccr.simo.modules.user.model;

import br.com.ciccr.simo.common.audit.Auditable;
import br.com.ciccr.simo.common.model.BaseEntity;
import br.com.ciccr.simo.modules.role.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean locked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}