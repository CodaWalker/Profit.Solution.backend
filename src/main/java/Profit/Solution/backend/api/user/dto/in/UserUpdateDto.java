package Profit.Solution.backend.api.user.dto.in;

import Profit.Solution.backend.model.user.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/** ДТО обновления пользователя */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserUpdateDto {

    @ApiModelProperty("Дата создания записи")
    private LocalDateTime creationDate;

    @ApiModelProperty("Имя пользователя, логин")
    private String username;

    @ApiModelProperty("Имя")
    private String firstName;

    @ApiModelProperty("Фамилия")
    private String lastName;

    @ApiModelProperty("Отчество")
    private String middleName;

    @ApiModelProperty("Пароль пользователя")
    private String password;

    @ApiModelProperty("Почта пользователя")
    private String email;

    @ApiModelProperty("Роли пользователя")
    private Set<Role> roles;

    @ApiModelProperty("Тип пользователя")
    private String typeUser;

}
