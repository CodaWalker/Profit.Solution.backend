package Profit.Solution.backend.api.ticket.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import Profit.Solution.backend.model.ticket.TicketStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Полная ДТО для сущности "Заявка"
 * */
@Getter
@Setter
@ApiModel("Заявка")
public class TicketDto {

    @ApiModelProperty("Идентификатор заявки")
    private UUID id;

//    @ApiModelProperty("Идентификатор пользователя")
//    private UUID userId;

    @ApiModelProperty("Идентификатор отправитель заявки")
    private UUID sender_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Логин отправителя")
    private String sender_username;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Логин получателя")
    private String recipient_username;

    @ApiModelProperty("Идентификатор получатель заявки")
    private UUID recipient_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Логин менеджера")
    private String manager_username;

    @ApiModelProperty("Идентификатор менеджер заявки")
    private UUID manager_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Логин оператора")
    private String operator_username;

    @ApiModelProperty("Идентификатор получатель заявки")
    private UUID operator_id;

    @ApiModelProperty("Номер заявки")
    private Long number;

    @ApiModelProperty("Дата создания заявки")
    private LocalDateTime creationDate = LocalDateTime.now();

    @ApiModelProperty("Наименование заявки (заголовок)")
    private String title;

    @ApiModelProperty("Описание заявки")
    private String description;

    @ApiModelProperty("Признак - создано из объявления")
    private boolean fromAds;

    @ApiModelProperty("Комментарий")
    private String comment;

    @ApiModelProperty("Дата выполнения заявки")
    private LocalDateTime completionDate;

    @ApiModelProperty("Статус заявки")
    private TicketStatus status;

    @ApiModelProperty("Подтверждена")
    private Boolean accepted;
}
