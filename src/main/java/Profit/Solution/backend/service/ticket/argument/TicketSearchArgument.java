package Profit.Solution.backend.service.ticket.argument;

import Profit.Solution.backend.model.ticket.TicketStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/** Аргумент поиска заявки по параметрам */
@Getter
@Builder
public class TicketSearchArgument {

//    private final UUID userId;
    private final UUID sender_id;

    private String sender_username;

    private final UUID recipient_id;

    private final UUID manager_id;

    private final UUID operator_id;

    private final String title;

    private final String description;

    private final Boolean fromAds;

    private final String comment;

    private final Long number;

    private final LocalDateTime creationDateFrom;

    private final LocalDateTime creationDateTo;

    private final LocalDateTime completionDateFrom;

    private final LocalDateTime completionDateTo;

    private final TicketStatus status;

    private final Boolean accepted;
}
