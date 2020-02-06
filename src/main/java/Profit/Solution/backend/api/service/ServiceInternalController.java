package Profit.Solution.backend.api.service;

import Profit.Solution.backend.api.user.dto.in.UserCreateDto;
import Profit.Solution.backend.api.user.dto.out.UserDto;
import Profit.Solution.backend.api.user.mapper.UserMapper;
import Profit.Solution.backend.service.user.UserService;
import Profit.Solution.backend.service.user.argument.UserCreateArgument;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("service")
@Api("Внутренний контроллер сервиса")
public class ServiceInternalController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public ServiceInternalController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Value("${client.hostnameFull}")
    private String clientHostname;

    @ApiOperation("Регистрация")
    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public UserDto create(@RequestBody UserCreateDto dto) {
        return userMapper.toDto(userService.create(
                UserCreateArgument.builder()
                        .username(dto.getUsername())
                        .password(dto.getPassword())
                        .email(dto.getEmail())
                        .creationDate(dto.getCreationDate())
                        .typeUser(dto.getTypeUser())
                        .build()));
    }

    @ApiOperation("Вход в систему")
    @GetMapping("/login")
    public UserDto login(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return null;
        }
        return userMapper.toDto(userService.findByUsername(principal.getName()));
    }

    @GetMapping("activate/{code}")
    public void activateUser(HttpServletResponse response, @PathVariable String code) throws IOException {
        boolean isActivated = userService.activateUser(code);

        if (!isActivated) {
            response.sendRedirect(String.format("http://%s/home", clientHostname) + "?activate=false");
        } else {
            response.sendRedirect(String.format("http://%s/home", clientHostname) + "?activate=true");
        }
    }
}
