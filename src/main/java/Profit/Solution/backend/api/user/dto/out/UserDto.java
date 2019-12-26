package Profit.Solution.backend.api.user.dto.out;

import Profit.Solution.backend.model.user.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Полная ДТО для сущности "Заявка"
 *
 * @author Pavel Zaytsev
 */
@Getter
@Setter
@ApiModel("Пользователь")
public class UserDto {

    @ApiModelProperty("Идентификатор пользователя")
    private UUID id;

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
