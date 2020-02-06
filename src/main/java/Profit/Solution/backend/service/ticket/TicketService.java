package Profit.Solution.backend.service.ticket;

import Profit.Solution.backend.model.ticket.Ticket;
import Profit.Solution.backend.service.ticket.argument.TicketCreateArgument;
import Profit.Solution.backend.service.ticket.argument.TicketSearchArgument;
import Profit.Solution.backend.service.ticket.argument.TicketUpdateArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с сущностью "Заявка"
 *
 */
public interface TicketService {

    Ticket create(TicketCreateArgument ticketCreateArgument);

    Ticket getExisting(UUID ticketId);

    Page<Ticket> getAll(Pageable pageable);

    Page<Ticket> getAllByParam(TicketSearchArgument searchArgument, Pageable pageable);

    Ticket update(UUID ticketId, TicketUpdateArgument ticketUpdateArgument);

    Page<Ticket> findMyTicketById(Integer page, Integer pageSize, UUID uuid);

    List<Ticket> getAllWithoutPages();

    void deleteTicket(UUID id);

    Page<Ticket> getAllByAttr(Integer page, Integer pageSize, String string);

    Ticket accepted(UUID id);
}
