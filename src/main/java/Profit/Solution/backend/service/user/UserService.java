package Profit.Solution.backend.service.user;

import Profit.Solution.backend.model.user.User;
import Profit.Solution.backend.service.user.argument.UserCreateArgument;
import Profit.Solution.backend.service.user.argument.UserUpdateArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с сущностью "Пользователь"
 *
 * @author Pavel Zaytsev
 */
public interface UserService {

    User create(UserCreateArgument userCreateArgument);

    User getExisting(UUID userId);

    Page<User> getAll(Pageable pageable);

    User update(UUID userId, UserUpdateArgument userUpdateArgument);

    List<User> getAllWithoutPages();

    User findByUsername(String username);

    Page<User> getAllByAttr(Integer page, Integer pageSize, String string);

    void deleteUser(UUID id);

    boolean activateUser(String code);

    Page<User> findAllByTypeUser(int pageNo, int pageSize, String typeUser);

    List<User> findAllByTypeUserWithoutPages(String typeUser);
}
