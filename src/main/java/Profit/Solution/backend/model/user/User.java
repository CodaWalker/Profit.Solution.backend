package Profit.Solution.backend.model.user;

import Profit.Solution.backend.model.BaseEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USR")
public class User extends BaseEntity {

    /** Дата создания учетной записи */
    @Builder.Default
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDate = LocalDateTime.now();

    /** имя пользователя (логин) */
    @Column(name = "USERNAME", nullable = false)
    private String username;

    /** Имя */
    @Column(name = "FIRST_NAME")
    private String firstName;

    /** Фамилия */
    @Column(name = "LAST_NAME")
    private String lastName;

    /** Отчество */
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    /** Пароль */
    @Column(name = "PASSWORD")
    private String password;

    /** Тип */
    @Column(name = "TYPE_USER")
    private String typeUser;

    /** Email */
    @Column(name = "EMAIL")
    private String email;

    /** Activation_code */
    @Column(name = "ACTIVATION_CODE")
    private String activationCode;

    /** Роль */
    @Builder.Default
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @PrePersist
    private void setInitialValues() {
        creationDate = LocalDateTime.now();
    }
}
