package Profit.Solution.backend.repository.user;

import Profit.Solution.backend.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, QuerydslPredicateExecutor<User> {
    Optional<User> findByUsername(String username);
    Page<User> findAllByUsername(Pageable pageable, String username);
    Optional<User> findById(UUID Id);
    List<User> findAll();
    User findByActivationCode(String code);
    Page<User> findAllByTypeUser(Pageable pageable,String typeUser);
    List<User> findAllByTypeUser(String typeUser);
}
