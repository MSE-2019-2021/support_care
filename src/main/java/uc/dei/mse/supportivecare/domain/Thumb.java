package uc.dei.mse.supportivecare.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * Thumb.
 */
@Entity
@Table(name = "thumb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thumb extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_thumb_id_seq")
    @SequenceGenerator(name = "gen_thumb_id_seq", sequenceName = "thumb_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Tipo da entidade.
     */
    @NotNull
    @Column(name = "entity_type", nullable = false)
    private EntityFeedback entityType;

    /**
     * Id da entidade.
     */
    @NotNull
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    /**
     * Polegar.
     */
    @NotNull
    @Column(name = "thumb", nullable = false)
    private Boolean thumb;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Thumb id(Long id) {
        this.id = id;
        return this;
    }

    public EntityFeedback getEntityType() {
        return this.entityType;
    }

    public Thumb entityType(EntityFeedback entityType) {
        this.entityType = entityType;
        return this;
    }

    public void setEntityType(EntityFeedback entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public Thumb entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Boolean getThumb() {
        return this.thumb;
    }

    public Thumb thumb(Boolean thumb) {
        this.thumb = thumb;
        return this;
    }

    public void setThumb(Boolean thumb) {
        this.thumb = thumb;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thumb)) {
            return false;
        }
        return id != null && id.equals(((Thumb) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thumb{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            ", thumb='" + getThumb() + "'" +
            "}";
    }
}
