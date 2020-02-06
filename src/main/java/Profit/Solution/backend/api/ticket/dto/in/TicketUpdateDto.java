package Profit.Solution.backend.api.ticket.dto.in;

import Profit.Solution.backend.model.ticket.TicketStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * ДТО обновления заявки
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TicketUpdateDto {

//    @ApiModelProperty("Идентификатор пользователя")
//    private UUID userId;

    @ApiModelProperty("Идентификатор отправитель заявки")
    private UUID sender_id;

    @ApiModelProperty("Идентификатор получатель заявки")
    private UUID recipient_id;

    @ApiModelProperty("Идентификатор менеджер заявки")
    private UUID manager_id;

    @ApiModelProperty("Идентификатор оператора заявки")
    private UUID operator_id;

    @ApiModelProperty("Наименование заявки (заголовок)")
    private String title;

    @ApiModelProperty("Описание заявки")
    private String description;

    @ApiModelProperty("Признак - создано из объявления")
    private Boolean fromAds;

    @ApiModelProperty("Комментарий")
    private String comment;

    @ApiModelProperty("Статус")
    private TicketStatus status;

    @ApiModelProperty("Подтверждена")
    private Boolean accepted;
}
