package Profit.Solution.backend.api.ticket.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import Profit.Solution.backend.model.ticket.TicketStatus;
import Profit.Solution.backend.model.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ДТО поиска заявки
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TicketSearchDto {

    //    @ApiModelProperty("Идентификатор пользователя")
//    private UUID userId;
    @ApiModelProperty("Идентификатор отправитель заявки")
    private UUID sender_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Отправитель")
    private User sender;

    @ApiModelProperty("Идентификатор получатель заявки")
    private UUID recipient_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Получатель")
    private User recipient;

    @ApiModelProperty("Идентификатор менеджер заявки")
    private UUID manager_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Менеджер")
    private User manager;

    @ApiModelProperty("Идентификатор оператора заявки")
    private UUID operator_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty("Оператор")
    private User operator;

    @ApiModelProperty("Наименование заявки (заголовок)")
    private String title;

    @ApiModelProperty("Описание заявки")
    private String description;

    @ApiModelProperty("Признак - создано из объявления")
    private Boolean fromAds;

    @ApiModelProperty("Комментарий")
    private String comment;

    @ApiModelProperty("Номер заявки")
    private Long number;

    @ApiModelProperty("Дата создания заявки от")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDateFrom;

    @ApiModelProperty("Дата создания заявки до")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDateTo;

    @ApiModelProperty("Дата выполнения заявки от")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime completionDateFrom;

    @ApiModelProperty("Дата выполнения заявки до")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime completionDateTo;

    @ApiModelProperty("Статус заявки")
    private TicketStatus status;

    @ApiModelProperty("Подтверждена")
    private Boolean accepted;
}
