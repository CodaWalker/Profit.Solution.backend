package Profit.Solution.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Базовый класс для всех сохраняемых сущностей
 */
@MappedSuperclass
public abstract class BaseEntity implements Persistable<UUID> {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(
            name = "ID",
            unique = true,
            nullable = false,
            insertable = true,
            updatable = false,
            length = 36,
            precision = 0
    )
    private UUID id;

    public BaseEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && Hibernate.getClass(this) == Hibernate.getClass(o)) {
            BaseEntity that = (BaseEntity) o;
            return Objects.equals(this.id, that.getId());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }
}
