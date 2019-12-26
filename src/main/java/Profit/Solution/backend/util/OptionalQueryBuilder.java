package Profit.Solution.backend.util;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;

/**
 * Билдер для опциональных параметров запросов QueryDsl
 *
 * @author Pavel Zaytsev
 */
public class OptionalQueryBuilder {

    private BooleanExpression predicate;

    private OptionalQueryBuilder() {
    }

    private OptionalQueryBuilder(BooleanExpression predicate) {
        this.predicate = predicate;
    }

    public static OptionalQueryBuilder getBuilder() {
        return new OptionalQueryBuilder();
    }

    public static OptionalQueryBuilder getBuilder(BooleanExpression predicate) {
        return new OptionalQueryBuilder(predicate);
    }

    /**
     * Возвращает предикат поиска
     */
    public BooleanExpression build() {
        if (predicate != null) {
            return predicate;
        } else {
            return Expressions.TRUE.eq(Expressions.TRUE);
        }
    }

    /**
     * Добавляет условие and, если значение не равно null
     *
     * @param function    функция для создания условного выражения на основе значения
     * @param value       передаваемое значение
     * @param <FieldType> тип передаваемого значения
     */
    public <FieldType> OptionalQueryBuilder optionalAnd(Function<FieldType, BooleanExpression> function, FieldType value) {
        if (value != null) {
            save(function.apply(value));
        }
        return this;
    }

    /**
     * Добавляет условие and, если строковое значение не равно null и не пустая строка
     *
     * @param function функция для создания условного выражения на основе значения
     * @param value    передаваемое значение
     */
    public OptionalQueryBuilder optionalStringAnd(Function<String, BooleanExpression> function, @Nullable String value) {
        if (!StringUtils.isEmpty(value)) {
            save(function.apply(value));
        }
        return this;
    }

    /**
     * Добавляет условие and, если коллекция не null и не пустая
     *
     * @param function         функция для создания условного выражения на основе значения
     * @param value            передаваемое значение
     * @param <CollectionType> тип элементов коллекции
     */
    public <CollectionType> OptionalQueryBuilder optionalCollectionAnd(Function<Collection<CollectionType>, BooleanExpression> function, @Nullable Collection<CollectionType> value) {
        if (!CollectionUtils.isEmpty(value)) {
            save(function.apply(value));
        }
        return this;
    }

    /**
     * Метод для инициализации и сохранения промежуточного
     * состояния предиката при добавлении новых условий
     *
     * @param value добавляемое выражение
     */
    private void save(BooleanExpression value) {
        if (predicate == null) {
            predicate = value;
        } else {
            predicate = predicate.and(value);
        }
    }
}
