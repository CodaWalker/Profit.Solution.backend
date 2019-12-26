package Profit.Solution.backend.service.user;

import Profit.Solution.backend.error.CommonError;
import Profit.Solution.backend.error.UserError;
import Profit.Solution.backend.model.user.Role;
import Profit.Solution.backend.model.user.User;
import Profit.Solution.backend.repository.user.UserRepository;
import Profit.Solution.backend.service.user.argument.UserCreateArgument;
import Profit.Solution.backend.util.validator.Validator;
import Profit.Solution.backend.service.user.argument.UserUpdateArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private MailSender mailSender;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${server.hostnameFull}")
    private String SHostname;

    @Value("${client.hostnameFull}")
    private String CHostname;

    @Override
    @Transactional
    public User create(UserCreateArgument userCreateArgument) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        Validator.validateObjectParam(userCreateArgument, UserError.USER_CREATE_ARGUMENT_IS_MANDATORY);
        User user = User.builder()
                              .username(userCreateArgument.getUsername())
                              .password(new BCryptPasswordEncoder().encode(userCreateArgument.getPassword()))
                              .email(userCreateArgument.getEmail())
                                .typeUser(userCreateArgument.getTypeUser())
                                .roles(roles)
                              .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getExisting(UUID userId) {
        Validator.validateObjectParam(userId, UserError.USER_ID_IS_MANDATORY);
        return userRepository.findById(userId)
                               .orElseThrow(() -> new EntityNotFoundException(UserError.USER_NOT_FOUND));
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    @Override
    public Page<User> findAllByTypeUser(int pageNo, int pageSize, String typeUser) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAllByTypeUser(pageable,typeUser);
    }

    @Override
    public List<User> findAllByTypeUserWithoutPages(String typeUser) {
        return userRepository.findAllByTypeUser(typeUser);
    }

    @Override
    public Page<User> getAllByAttr(Integer page, Integer pageSize, String string) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.findAllByUsername(pageable,string);
    }


    @Override
    public void deleteUser(UUID id) {
          userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAll(Pageable pageable) {
        Validator.validateObjectParam(pageable, CommonError.PAGEABLE_IS_MANDATORY);

        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User update(UUID userId, UserUpdateArgument userUpdateArgument) {
        Validator.validateObjectParam(userUpdateArgument, UserError.USER_UPDATE_ARGUMENT_IS_MANDATORY);

        User updatingUser = getExisting(userId);
        updatingUser.setUsername(userUpdateArgument.getUsername());
        updatingUser.setEmail(userUpdateArgument.getEmail());
        updatingUser.setPassword(userUpdateArgument.getPassword());
        updatingUser.setRoles(userUpdateArgument.getRoles());
        updatingUser.setFirstName(userUpdateArgument.getFirstName());
        updatingUser.setLastName(userUpdateArgument.getLastName());
        updatingUser.setMiddleName(userUpdateArgument.getMiddleName());
        updatingUser.setCreationDate(userUpdateArgument.getCreationDate());
        updatingUser.setTypeUser(userUpdateArgument.getTypeUser());
//        updatingUser.setSpecialization(userUpdateArgument.getSpecialization());
        return userRepository.save(updatingUser);
    }

    @Override
    public List<User> getAllWithoutPages() {
        return userRepository.findAll();
    }


    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        sendMessUserActivateSuccess(user);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }



    private void sendMessageUserActivate(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Добро пожаловать!, %s! \n"  +
                            ".Для активации учетной записи в SD Solution, перейдтие по ссылке: http://%s/service/activate/%s",
                    user.getUsername(),
                    SHostname,
                    user.getActivationCode()
            );

//            mailSender.send(user.getEmail(), "Activation code", message);
            mailSender.send();
        }
    }
    private void sendMessUserActivateSuccess(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            // user.setRoles(Collections.singleton(Role.ADMIN));

            String message = String.format(
                    "Поздравляем!, %s! \n" +
                            ".Ваша учетная запись SD Solution активирована, перейдтие по ссылке для входа: http://%s",
                    user.getUsername(),
                    CHostname
            );
//            mailSender.send(user.getEmail(), "Success activation code", message);
            mailSender.send();
        }
    }
}
