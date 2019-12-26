package Profit.Solution.backend.model.ticket;

import Profit.Solution.backend.model.BaseEntity;
import Profit.Solution.backend.model.user.User;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Заявка
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TICKET")
public class Ticket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private User operator;
    //----------------------------------

    /** Номер заявки */
    @Column(name = "NUMBER", nullable = false, updatable = false, unique = true, columnDefinition = "serial")
    @Generated(GenerationTime.INSERT)
    private Long number;

    /** Дата создания заявки */
    @Builder.Default
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDate = LocalDateTime.now();

    /** Наименование заявки (заголовок) */
    @Column(name = "NAME", nullable = false)
    private String title;

    /** Описание заявки */
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    /** Признак - создано из объявления */
    @Column(name = "FROM_ADS", nullable = false)
    private boolean fromAds;

    /** Комментарий */
    @Column(name = "COMMENT")
    private String comment;

    /** Дата выполнения заявки */
    @Column(name = "COMPLETION_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime completionDate;

    /** Статус заявки */
    @Builder.Default
    @Column(name = "STATUS", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'OPEN'")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    /** Подтверждение работы, возможность смены статуса*/
    @Column(name = "ACCEPTED", nullable = false)
    private Boolean accepted;

    /** Инфиализация */
    @PrePersist
    private void setInitialValues() {
        creationDate = LocalDateTime.now();
        status = TicketStatus.NEW;
        accepted = true;
    }
}
