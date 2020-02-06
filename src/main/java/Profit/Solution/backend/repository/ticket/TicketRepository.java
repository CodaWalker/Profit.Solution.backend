package Profit.Solution.backend.repository.ticket;

import Profit.Solution.backend.model.ticket.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID>, QuerydslPredicateExecutor<Ticket> {
    Optional<Ticket> findById(UUID Id);

    List<Ticket> findAll();

    Page<Ticket> findAllByTitle(Pageable pageable, String title);

    Page<Ticket> findAllBySenderIdOrderById(Pageable pageable, UUID Id);

    Page<Ticket> findAllByRecipientIdOrderById(Pageable pageable, UUID Id);

    Page<Ticket> findAllByManagerIdOrderById(Pageable pageable, UUID Id);

    Page<Ticket> findAllByOperatorIdOrderById(Pageable pageable, UUID Id);

}
