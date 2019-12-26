package Profit.Solution.backend.service.ticket.argument;

import Profit.Solution.backend.error.TicketError;
import Profit.Solution.backend.util.validator.Validator;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/** Аргумент создания заявки */
@Getter
@Builder
public class TicketCreateArgument {

//    private final UUID userId;

    private final UUID sender_id;

    private final UUID recipient_id;

    private final UUID manager_id;

    private final UUID operator_id;

    private final String title;

    private final String description;

    private final boolean fromAds;

    private final String comment;

    public TicketCreateArgument(UUID sender_id, UUID recipient_id, UUID manager_id, UUID operator_id, String title, String description, boolean fromAds, String comment) {
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.manager_id = manager_id;
        this.operator_id = operator_id;


//        Validator.validateObjectParam(userId, TicketError.TICKET_USER_ID_IS_MANDATORY);
        Validator.validateObjectParam(title, TicketError.TICKET_NAME_IS_MANDATORY);
        Validator.validateObjectParam(description, TicketError.TICKET_DESCRIPTION_IS_MANDATORY);

//        this.userId = userId;
        this.title = title;
        this.description = description;
        this.fromAds = fromAds;
        this.comment = comment;
    }
}
