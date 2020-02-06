package Profit.Solution.backend.service.user.argument;

import Profit.Solution.backend.model.user.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Аргумент поиска пользователя по параметрам
 */
@Getter
@Builder
public class UserSearchArgument {

    private LocalDateTime creationDate;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String email;
    private Set<Role> roles;
    private String typeUser;
    private String specialization;


}
