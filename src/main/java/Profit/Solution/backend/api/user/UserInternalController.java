package Profit.Solution.backend.api.user;

import Profit.Solution.backend.api.user.dto.in.UserCreateDto;
import Profit.Solution.backend.api.user.dto.in.UserUpdateDto;
import Profit.Solution.backend.api.user.dto.out.UserDto;
import Profit.Solution.backend.api.user.mapper.UserMapper;
import Profit.Solution.backend.service.user.UserService;
import Profit.Solution.backend.service.user.argument.UserCreateArgument;
import Profit.Solution.backend.service.user.argument.UserUpdateArgument;
import Profit.Solution.backend.util.mapper.MapperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "api/users")
@Api("Внутренний контроллер пользователей")
public class UserInternalController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserInternalController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @ApiOperation("Получить список заявок без пагинации")
    @GetMapping(value = "/all")
    public List<UserDto> getAllWithoutPages() {
        return userMapper.toDtoListFromDB(userService.getAllWithoutPages());
    }


    @ApiOperation("Получить список пользователей")
    @GetMapping(value = "/all/pagination")
    public List<UserDto> getAll(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize) {
        return userMapper.toDtoListFromDB(
                userService.getAll(PageRequest.of(pageNo, pageSize)).getContent());
    }

    @ApiOperation("Получить список пользователей с указанным типом")
    @GetMapping(value = "/all&typeUser={typeUser}/pagination")
    public List<UserDto> getAllIncludeType(@PathVariable String typeUser,@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize) {
        return userMapper.toDtoListFromDB(
                userService.findAllByTypeUser(pageNo, pageSize,typeUser).getContent());
    }

    @ApiOperation("Получить список пользователей с указанным типом без пагинации")
    @GetMapping(value = "/all&typeUser={typeUser}")
    public List<UserDto> getAllIncludeTypeWithoutPage(@PathVariable String typeUser) {
        return userMapper.toDtoListFromDB(
                userService.findAllByTypeUserWithoutPages(typeUser));
    }

    @ApiOperation("Создать пользователя")
    @PostMapping(value = "/create")
    @ResponseStatus(CREATED)
    public UserDto create(@RequestBody UserCreateDto dto) {

        return userMapper.toDto(userService.create(
                    UserCreateArgument.builder()
                          .username(dto.getUsername())
                          .password(dto.getPassword())
                          .email(dto.getEmail())
                          .roles(dto.getRoles())
                          .creationDate(dto.getCreationDate())
                          .firstName(dto.getFirstName())
                          .lastName(dto.getLastName())
                          .middleName(dto.getMiddleName())
                            .typeUser(dto.getTypeUser())
                          .build()));
    }

    @ApiOperation("Получить пользователя по идентификатору")
    @GetMapping("/{id}")
    public UserDto get(@PathVariable UUID id) {
        return userMapper.toDto(userService.getExisting(id));
    }
    @ApiOperation("Удалить заявку по идентификатору")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.deleteUser(id); //Add dto
    }

    @ApiOperation("Обновить пользователя")
    @PostMapping("/{id}")
    public UserDto update(@PathVariable UUID id,
                            @RequestBody UserUpdateDto updateDto) {
        return MapperUtils.getMapper(userMapper::toDto)
                          .apply(userService.update(id, UserUpdateArgument.builder()
                                                                        .username(updateDto.getUsername())
                                                                          .password(updateDto.getPassword())
                                                                          .email(updateDto.getEmail())
                                                                          .roles(updateDto.getRoles())
                                                                          .creationDate(updateDto.getCreationDate())
                                                                          .firstName(updateDto.getFirstName())
                                                                          .lastName(updateDto.getLastName())
                                                                          .typeUser(updateDto.getTypeUser())
                                                                          .middleName(updateDto.getMiddleName())
                                                                             .build()));
    }

    @ApiOperation("Получить заявки по параметрам с сортировкой и пейджинацией и поиском")
    @GetMapping("/search/{string}")
    public List<UserDto> getAllSearch(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,@RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize,@PathVariable String string){
        return userMapper.toDtoListFromDB(userService.getAllByAttr(pageNo,pageSize,string).getContent());
    }
}
