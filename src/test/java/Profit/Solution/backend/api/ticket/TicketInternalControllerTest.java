package Profit.Solution.backend.api.ticket;

import Profit.Solution.backend.api.ticket.dto.in.TicketCreateDto;
import Profit.Solution.backend.api.ticket.dto.in.TicketSearchDto;
import Profit.Solution.backend.api.ticket.dto.in.TicketUpdateDto;
import Profit.Solution.backend.api.ticket.dto.out.TicketDto;
import Profit.Solution.backend.api.ticket.mapper.TicketMapper;
import Profit.Solution.backend.model.ticket.Ticket;
import Profit.Solution.backend.model.ticket.TicketStatus;
import Profit.Solution.backend.service.ticket.TicketService;
import Profit.Solution.backend.service.ticket.TicketServiceImpl;
import Profit.Solution.backend.service.ticket.argument.TicketCreateArgument;
import Profit.Solution.backend.service.ticket.argument.TicketSearchArgument;
import Profit.Solution.backend.service.ticket.argument.TicketUpdateArgument;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketInternalControllerTest {

    private TicketMapper ticketMapper = mock(TicketMapper.class);

    private TicketService ticketService = mock(TicketServiceImpl.class);

    private TicketInternalController ticketInternalController = new TicketInternalController(ticketService, ticketMapper);

    private TicketDto ticketDto = mock(TicketDto.class);

    private Ticket ticket = mock(Ticket.class);

    private Page page = mock(Page.class);

    private String comment = "comment";

    private String description = "description";

    private String title = "title";

//    private UUID userId = UUID.randomUUID();

    private final UUID sender  = UUID.randomUUID();
    private final UUID recipient  = UUID.randomUUID();
    private final UUID manager  = UUID.randomUUID();

    private UUID id = UUID.randomUUID();

    @Test
    void getAll() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        List<TicketDto> ticketDtos = Lists.newArrayList(ticketDto);
        List<Ticket> tickets = Lists.newArrayList(ticket);

        when(ticketMapper.toDto(any())).thenReturn(ticketDto);
        when(ticketService.getAll(any(Pageable.class))).thenReturn(page);
        when(page.getContent()).thenReturn(tickets);

        ArgumentCaptor<Pageable> argumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        // Actual
        List<TicketDto> actualResult = ticketInternalController.getAll(pageNo, pageSize);

        // Assertion
        verify(ticketService).getAll(argumentCaptor.capture());
        verify(ticketMapper).toDto(ticket);
        verifyNoMoreInteractions(ticketService);
        verifyNoMoreInteractions(ticketMapper);

        Assertions.assertThat(actualResult).isEqualTo(ticketDtos);
        Assertions.assertThat(argumentCaptor.getValue().getPageNumber()).isEqualTo(pageNo);
        Assertions.assertThat(argumentCaptor.getValue().getPageSize()).isEqualTo(pageSize);
    }

    @Test
    void create() {
        // Arrange
        when(ticketService.create(any(TicketCreateArgument.class))).thenReturn(ticket);
        when(ticketMapper.toDto(any())).thenReturn(ticketDto);
        TicketCreateDto ticketCreateDto = TicketCreateDto.builder()
                                                        .sender_id(sender)
                                                        .recipient_id(recipient)
                                                        .manager_id(manager)
                                                         .comment(comment)
                                                         .description(description)
                                                         .fromAds(true)
                                                         .title(title)
//                                                         .userId(userId)
                                                         .build();

        ArgumentCaptor<TicketCreateArgument> argumentCaptor = ArgumentCaptor.forClass(TicketCreateArgument.class);

        // Actual
        TicketDto actualResult = ticketInternalController.create(ticketCreateDto);

        // Assertion
        verify(ticketService).create(argumentCaptor.capture());
        verify(ticketMapper).toDto(ticket);
        verifyNoMoreInteractions(ticketService);
        verifyNoMoreInteractions(ticketMapper);

        Assertions.assertThat(actualResult).isEqualTo(ticketDto);
        Assertions.assertThat(argumentCaptor.getValue().getComment()).isEqualTo(comment);
        Assertions.assertThat(argumentCaptor.getValue().getTitle()).isEqualTo(title);
        Assertions.assertThat(argumentCaptor.getValue().getDescription()).isEqualTo(description);
//        Assertions.assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(userId);
        Assertions.assertThat(argumentCaptor.getValue().getSender_id()).isEqualTo(sender);
        Assertions.assertThat(argumentCaptor.getValue().getRecipient_id()).isEqualTo(recipient);
        Assertions.assertThat(argumentCaptor.getValue().getManager_id()).isEqualTo(manager);
        Assertions.assertThat(argumentCaptor.getValue().isFromAds()).isEqualTo(true);
    }

    @Test
    void get() {
        // Arrange
        when(ticketService.getExisting(any(UUID.class))).thenReturn(ticket);
        when(ticketMapper.toDto(any())).thenReturn(ticketDto);

        // Actual
        TicketDto actualResult = ticketInternalController.get(id);

        // Assertion
        verify(ticketMapper).toDto(ticket);
        verify(ticketService).getExisting(id);
        verifyNoMoreInteractions(ticketMapper);
        verifyNoMoreInteractions(ticketService);

        Assertions.assertThat(actualResult).isEqualTo(ticketDto);
    }

    @Test
    void update() {
        // Arrange
        when(ticketService.update(any(UUID.class), any(TicketUpdateArgument.class))).thenReturn(ticket);
        when(ticketService.getExisting(any(UUID.class))).thenReturn(ticket);
        when(ticketMapper.toDto(any())).thenReturn(ticketDto);

        TicketUpdateDto ticketUpdateDto = TicketUpdateDto.builder()
                                                         .title(title)
                                                        .sender_id(sender)
                                                        .recipient_id(recipient)
                                                        .manager_id(manager)
                                                         .status(TicketStatus.OPEN)
                                                         .comment(comment)
                                                         .description(description)
                                                         .fromAds(true)
//                                                         .userId(userId)
                                                         .build();

        // Actual
        TicketDto actualResult = ticketInternalController.update(id, ticketUpdateDto);

        ArgumentCaptor<TicketUpdateArgument> argumentCaptor = ArgumentCaptor.forClass(TicketUpdateArgument.class);

        // Assertion
        verify(ticketMapper).toDto(ticket);
        verify(ticketService).update(eq(id), argumentCaptor.capture());
        verifyNoMoreInteractions(ticketMapper);
        verifyNoMoreInteractions(ticketService);

        Assertions.assertThat(actualResult).isEqualTo(ticketDto);

        Assertions.assertThat(argumentCaptor.getValue().getComment()).isEqualTo(comment);
        Assertions.assertThat(argumentCaptor.getValue().getDescription()).isEqualTo(description);
        Assertions.assertThat(argumentCaptor.getValue().getTitle()).isEqualTo(title);
        Assertions.assertThat(argumentCaptor.getValue().getSender_id()).isEqualTo(sender.toString());
        Assertions.assertThat(argumentCaptor.getValue().getManager_id()).isEqualTo(manager.toString());
        Assertions.assertThat(argumentCaptor.getValue().getRecipient_id()).isEqualTo(recipient.toString());
        Assertions.assertThat(argumentCaptor.getValue().isFromAds()).isEqualTo(true);
//        Assertions.assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(userId);
        Assertions.assertThat(argumentCaptor.getValue().getStatus()).isEqualTo(TicketStatus.OPEN);
    }

    @Test
    void getAllByParam() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        List<TicketDto> ticketDtos = Lists.newArrayList(ticketDto);
        List<Ticket> tickets = Lists.newArrayList(ticket);
        LocalDateTime completionDateFrom = LocalDateTime.now();
        LocalDateTime completionDateTo = LocalDateTime.now();

        when(ticketService.getAllByParam(any(TicketSearchArgument.class), any(Pageable.class))).thenReturn(page);
        when(page.getContent()).thenReturn(tickets);
        when(ticketMapper.toDto(any())).thenReturn(ticketDto);

        TicketSearchDto ticketSearchDto = TicketSearchDto.builder()
                                                        .sender_id(sender)
                                                        .recipient_id(recipient)
                                                        .manager_id(manager)
                                                         .comment(comment)
                                                         .description(description)
                                                         .title(title)
                                                         .fromAds(true)
                                                         .status(TicketStatus.OPEN)
//                                                         .userId(userId)
                                                         .number(1L)
                                                         .completionDateFrom(completionDateFrom)
                                                         .completionDateTo(completionDateTo)
                                                         .build();

        ArgumentCaptor<TicketSearchArgument> ticketSearchArgumentArgumentCaptor = ArgumentCaptor.forClass(TicketSearchArgument.class);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        // Actual
//        List<TicketDto> actualResult = ticketInternalController.getAllByParam(pageNo, pageSize, title, Sort.Direction.DESC, ticketSearchDto);

        // Assertion
        verify(ticketService).getAllByParam(ticketSearchArgumentArgumentCaptor.capture(), pageableArgumentCaptor.capture());
        verify(ticketMapper).toDto(ticket);
        verifyNoMoreInteractions(ticketMapper);
        verifyNoMoreInteractions(ticketService);

//        Assertions.assertThat(actualResult).isEqualTo(ticketDtos);

        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getComment()).isEqualTo(comment);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getCompletionDateFrom()).isEqualTo(completionDateFrom);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getCompletionDateTo()).isEqualTo(completionDateTo);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getDescription()).isEqualTo(description);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getTitle()).isEqualTo(title);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getSender_id()).isEqualTo(sender.toString());
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getRecipient_id()).isEqualTo(recipient.toString());
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getManager_id()).isEqualTo(manager.toString());
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getNumber()).isEqualTo(1L);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getFromAds()).isEqualTo(true);
//        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getUserId()).isEqualTo(userId);
        Assertions.assertThat(ticketSearchArgumentArgumentCaptor.getValue().getStatus()).isEqualTo(TicketStatus.OPEN);

        Assertions.assertThat(pageableArgumentCaptor.getValue().getPageSize()).isEqualTo(pageSize);
        Assertions.assertThat(pageableArgumentCaptor.getValue().getPageNumber()).isEqualTo(pageNo);
    }

}