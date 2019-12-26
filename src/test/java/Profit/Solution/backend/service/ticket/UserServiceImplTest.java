package Profit.Solution.backend.service.ticket;

import Profit.Solution.backend.repository.ticket.TicketRepository;
import com.querydsl.core.types.Predicate;
import Profit.Solution.backend.model.ticket.Ticket;
import Profit.Solution.backend.model.ticket.TicketStatus;
import Profit.Solution.backend.service.ticket.argument.TicketCreateArgument;
import Profit.Solution.backend.service.ticket.argument.TicketSearchArgument;
import Profit.Solution.backend.service.ticket.argument.TicketUpdateArgument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Pavel Zaytsev
 */
class UserServiceImplTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final TicketService ticketService = new TicketServiceImpl(ticketRepository);
    private final static String TITLE = "title";
    private final static String COMMENT = "comment";
    private final static String DESCRIPTION = "description";
    private final static boolean FROM_ADS = true;
//    private final static UUID USER_ID = UUID.randomUUID();
    private final static UUID SENDER= UUID.randomUUID();
    private final static UUID RECIPIENT= UUID.randomUUID();
    private final static UUID MANAGER = UUID.randomUUID();
    private final static UUID TICKET_ID = UUID.randomUUID();

    @Test
    void create() {
        // Arrange
        Ticket ticket = mock(Ticket.class);
        TicketCreateArgument createArgument = TicketCreateArgument.builder()
                                                                  .title(TITLE)
                                                                  .comment(COMMENT)
                                                                  .description(DESCRIPTION)
                                                                  .fromAds(FROM_ADS)
                                                                  .sender_id(SENDER)
                                                                  .recipient_id(RECIPIENT)
                                                                  .manager_id(MANAGER)
                                                                  .build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        ArgumentCaptor<Ticket> argumentCaptor = ArgumentCaptor.forClass(Ticket.class);

        // Actual
        Ticket actualResult = ticketService.create(createArgument);

        // Assertion
        verify(ticketRepository).save(argumentCaptor.capture());
        verifyNoMoreInteractions(ticketRepository);

        Assertions.assertThat(actualResult).isSameAs(ticket);
        Assertions.assertThat(argumentCaptor.getValue().getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(argumentCaptor.getValue().getComment()).isEqualTo(COMMENT);
        Assertions.assertThat(argumentCaptor.getValue().getDescription()).isEqualTo(DESCRIPTION);
        Assertions.assertThat(argumentCaptor.getValue().getManager()).isEqualTo(MANAGER);
        Assertions.assertThat(argumentCaptor.getValue().getRecipient()).isEqualTo(RECIPIENT);
        Assertions.assertThat(argumentCaptor.getValue().getSender()).isEqualTo(SENDER);
    }

    @Test
    void getExisting() {
        // Arrange
        Ticket ticket = mock(Ticket.class);
        when(ticket.getId()).thenReturn(TICKET_ID);
        when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));

        // Actual
        Ticket actualResult = ticketService.getExisting(TICKET_ID);

        // Assertion
        verify(ticketRepository).findById(TICKET_ID);
        verifyNoMoreInteractions(ticketRepository);

        Assertions.assertThat(actualResult).isSameAs(ticket);
    }

    @Test
    void getAll() {
        // Arrange
        Page<Ticket> page = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(ticketRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Actual
        Page<Ticket> actualResult = ticketService.getAll(pageable);

        // Assertion
        verify(ticketRepository).findAll(pageable);
        verifyNoMoreInteractions(ticketRepository);

        Assertions.assertThat(actualResult).isSameAs(page);
    }

    @Test
    void update() {
        // Arrange
        UUID ticketId = UUID.randomUUID();
        Ticket ticket = mock(Ticket.class);

        when(ticketRepository.findById(any(UUID.class))).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any())).thenReturn(ticket);

        TicketUpdateArgument ticketUpdateArgument = TicketUpdateArgument.builder()
                                                                        .title(TITLE)
                                                                        .description(DESCRIPTION)
                                                                        .fromAds(true)
//                                                                        .userId(USER_ID)
                                                                        .manager_id(MANAGER)
                                                                        .sender_id(SENDER)
                                                                        .recipient_id(RECIPIENT)
                                                                        .status(TicketStatus.OPEN)
                                                                        .build();


        // Actual
        Ticket actualResult = ticketService.update(ticketId, ticketUpdateArgument);

        // Assertion
        Assertions.assertThat(actualResult).isSameAs(ticket);

        verify(ticketRepository).save(ticket);
        verify(ticketRepository).findById(ticketId);
        verify(ticketService).getExisting(ticketId);
        verifyNoMoreInteractions(ticketRepository);
    }

    @Test
    void getAllByParam() {
        // Arrange
        TicketSearchArgument ticketSearchArgument = TicketSearchArgument.builder()
                                                                        .number(1L)
                                                                        .title(TITLE)
                                                                        .status(TicketStatus.OPEN)
                                                                        .comment(COMMENT)
                                                                        .description(DESCRIPTION)
                                                                        .fromAds(true)
                                                                        .manager_id(MANAGER)
                                                                        .sender_id(SENDER)
                                                                        .recipient_id(RECIPIENT)
//                                                                        .userId(USER_ID)
                                                                        .build();

        Page<Ticket> page = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(ticketRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(page);

        // Actual
        Page<Ticket> actualResult = ticketService.getAllByParam(ticketSearchArgument, pageable);

        ArgumentCaptor<Predicate> argumentCaptor = ArgumentCaptor.forClass(Predicate.class);

        // Assertion
        verify(ticketRepository).findAll(argumentCaptor.capture(), eq(pageable));
        verifyNoMoreInteractions(ticketRepository);

        Assertions.assertThat(actualResult).isSameAs(page);
        Assertions.assertThat(argumentCaptor.getValue().toString())
                  .isEqualTo("containsIc(ticket.name,name) " +
                             "&& containsIc(ticket.comment,comment) " +
                             "&& containsIc(ticket.description,description) " +
//                             "&& ticket.userId = " + USER_ID + " " +
                             "&& ticket.senderId = " + SENDER + " " +
                             "&& ticket.recipientId = " + RECIPIENT + " " +
                             "&& ticket.managerId = " + MANAGER + " " +
                             "&& ticket.fromAds = true " +
                             "&& ticket.number = 1 " +
                             "&& ticket.status = OPEN");
    }
}