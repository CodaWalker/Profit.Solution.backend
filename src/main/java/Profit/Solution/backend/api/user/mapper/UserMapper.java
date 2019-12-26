package Profit.Solution.backend.api.user.mapper;

import Profit.Solution.backend.api.user.dto.out.UserDto;
import Profit.Solution.backend.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toDto(User entity);

    List<UserDto> toDtoListFromDB(List<User> entityList);
}
