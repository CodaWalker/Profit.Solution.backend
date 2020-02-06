package Profit.Solution.backend.api.ticket.mapper;

import Profit.Solution.backend.api.ticket.dto.out.TicketDto;
import Profit.Solution.backend.model.ticket.Ticket;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TicketMapper {

    Ticket toEntity(TicketDto dto);

    @Mapping(target = "sender_id", source = "entity.sender.id")
    @Mapping(target = "sender_username", source = "entity.sender.username")
    @Mapping(target = "recipient_id", source = "entity.recipient.id")
    @Mapping(target = "recipient_username", source = "entity.recipient.username")
    @Mapping(target = "operator_id", source = "entity.operator.id")
    @Mapping(target = "operator_username", source = "entity.operator.username")
    @Mapping(target = "manager_id", source = "entity.manager.id")
    @Mapping(target = "manager_username", source = "entity.manager.username")
    TicketDto

    toDto(Ticket entity);

    @IterableMapping(qualifiedByName = "toDto")
        // won't work without it
    List<TicketDto> toDtoListFromDB(List<Ticket> entityList);

}


