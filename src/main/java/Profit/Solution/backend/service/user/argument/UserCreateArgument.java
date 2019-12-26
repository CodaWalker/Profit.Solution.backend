package Profit.Solution.backend.service.user.argument;

import Profit.Solution.backend.model.user.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

/** Аргумент создания пользователя */
@Getter
@Builder
public class UserCreateArgument {
    public UserCreateArgument(LocalDateTime creationDate, String username, String firstName, String lastName, String middleName, String password, String email, Set<Role> roles, String typeUser) {

//        Validator.validateObjectParam(userId, TicketError.TICKET_USER_ID_IS_MANDATORY);
//        Validator.validateObjectParam(name, TicketError.TICKET_NAME_IS_MANDATORY);
//        Validator.validateObjectParam(description, TicketError.TICKET_DESCRIPTION_IS_MANDATORY);
        this.typeUser = typeUser;
        this.creationDate = creationDate;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.email = email;
        this.roles = roles;

    }

    private LocalDateTime creationDate;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String email;
    private Set<Role> roles;
    private String typeUser;


}
