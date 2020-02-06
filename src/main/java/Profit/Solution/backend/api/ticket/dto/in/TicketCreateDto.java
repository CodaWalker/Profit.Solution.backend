package Profit.Solution.backend.api.ticket.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

/**
 * ДТО создания заявки
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TicketCreateDto {

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
}
