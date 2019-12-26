package Profit.Solution.backend.model.ticket;

/**
 * Статус заявки
 */
public enum TicketStatus {
    NEW,// Новая
    OPEN, // Открыта
    IN_PROGRESS, // В работе
    CANCELED, // Отменена
    COMPLETED, // Выполнена
    ARCHIVED, // В архиве
    DELETED // Удалена
}
