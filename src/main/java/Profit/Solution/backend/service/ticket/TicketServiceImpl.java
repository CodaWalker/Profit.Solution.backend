package Profit.Solution.backend.service.ticket;

import Profit.Solution.backend.error.CommonError;
import Profit.Solution.backend.error.TicketError;
import Profit.Solution.backend.model.ticket.Ticket;
import Profit.Solution.backend.repository.ticket.TicketRepository;
import Profit.Solution.backend.service.user.UserService;
import Profit.Solution.backend.util.validator.Validator;
import Profit.Solution.backend.service.ticket.argument.TicketCreateArgument;
import Profit.Solution.backend.service.ticket.argument.TicketSearchArgument;
import Profit.Solution.backend.service.ticket.argument.TicketUpdateArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    @Autowired
    private UserService userService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public Ticket create(TicketCreateArgument ticketCreateArgument) {
        Ticket ticket;
        Validator.validateObjectParam(ticketCreateArgument, TicketError.TICKET_CREATE_ARGUMENT_IS_MANDATORY);
        if (ticketCreateArgument.getOperator_id() == null) {
            ticket = Ticket.builder()
                    .title(ticketCreateArgument.getTitle())
                    .sender(userService.getExisting(ticketCreateArgument.getSender_id()))
                    .recipient(null)
                    .manager(null)
                    .operator(null)
                    .fromAds(ticketCreateArgument.isFromAds())
                    .description(ticketCreateArgument.getDescription())
                    .comment(ticketCreateArgument.getComment())
                    .build();
        } else {
            ticket = Ticket.builder()
                    .title(ticketCreateArgument.getTitle())
                    .sender(userService.getExisting(ticketCreateArgument.getSender_id()))
                    .recipient(userService.getExisting(ticketCreateArgument.getRecipient_id()))
                    .manager(userService.getExisting(ticketCreateArgument.getOperator_id())) //Менеджер
                    .operator(userService.getExisting(ticketCreateArgument.getOperator_id()))
                    .fromAds(ticketCreateArgument.isFromAds())
                    .description(ticketCreateArgument.getDescription())
                    .comment(ticketCreateArgument.getComment())
                    .build();
        }
        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket getExisting(UUID ticketId) {
        Validator.validateObjectParam(ticketId, TicketError.TICKET_ID_IS_MANDATORY);

        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(TicketError.TICKET_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ticket> getAll(Pageable pageable) {
        Validator.validateObjectParam(pageable, CommonError.PAGEABLE_IS_MANDATORY);

        return ticketRepository.findAll(pageable);
    }

    @Override
    public Ticket accepted(UUID id) {
        Ticket acceptedTicket = getExisting(id);
        acceptedTicket.setAccepted(true);
        return ticketRepository.save(acceptedTicket);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Ticket update(UUID ticketId, TicketUpdateArgument ticketUpdateArgument) {
        Validator.validateObjectParam(ticketUpdateArgument, TicketError.TICKET_UPDATE_ARGUMENT_IS_MANDATORY);

        Ticket updatingTicket = getExisting(ticketId);
        updatingTicket.setTitle(ticketUpdateArgument.getTitle());

        updatingTicket.setComment(ticketUpdateArgument.getComment());
        updatingTicket.setDescription(ticketUpdateArgument.getDescription());
        updatingTicket.setSender(userService.getExisting(ticketUpdateArgument.getSender_id()));
        updatingTicket.setManager(userService.getExisting(ticketUpdateArgument.getOperator_id())); //менеджер
        updatingTicket.setRecipient(userService.getExisting(ticketUpdateArgument.getRecipient_id()));
        updatingTicket.setOperator(userService.getExisting(ticketUpdateArgument.getOperator_id()));
        updatingTicket.setFromAds(ticketUpdateArgument.isFromAds());

        if (updatingTicket.getStatus() != ticketUpdateArgument.getStatus()) {
            updatingTicket.setAccepted(false);
            updatingTicket.setStatus(ticketUpdateArgument.getStatus());
        } else {
            updatingTicket.setAccepted(ticketUpdateArgument.getAccepted());
        }

        return ticketRepository.save(updatingTicket);
    }

    @Override
    public Page<Ticket> findMyTicketById(Integer page, Integer pageSize, UUID uuid) {
        Pageable pageable;
        if (page == null || pageSize == null) {
            pageable = PageRequest.of(0, 10);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }

        switch (userService.getExisting(uuid).getTypeUser()) {
            case "client":
                return ticketRepository.findAllBySenderIdOrderById(pageable, uuid);

            case "specialist":
                //specialist
                return ticketRepository.findAllByRecipientIdOrderById(pageable, uuid);

            case "manager":
                //manager
                return ticketRepository.findAllByManagerIdOrderById(pageable, uuid);

            case "operator":
                //operator
                return ticketRepository.findAllByOperatorIdOrderById(pageable, uuid);
        }
        return null;
    }


    @Override
    public List<Ticket> getAllWithoutPages() {
        return ticketRepository.findAll();
    }

    @Override
    public Page<Ticket> getAllByAttr(Integer page, Integer pageSize, String string) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ticketRepository.findAllByTitle(pageable, string);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Ticket> getAllByParam(TicketSearchArgument searchArgument, Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public void deleteTicket(UUID id) {
        ticketRepository.deleteById(id);
    }
}
