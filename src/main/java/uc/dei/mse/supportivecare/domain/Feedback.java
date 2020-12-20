package uc.dei.mse.supportivecare.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nome da entidade.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_name", nullable = false)
    private EntityFeedback entityName;

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

    /**
     * Razão.
     */
    @Size(max = 1000)
    @Column(name = "reason", length = 1000)
    private String reason;

    /**
     * Resolvido.
     */
    @NotNull
    @Column(name = "solved", nullable = false)
    private Boolean solved;

    /**
     * Anónimo.
     */
    @NotNull
    @Column(name = "anonym", nullable = false)
    private Boolean anonym;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feedback id(Long id) {
        this.id = id;
        return this;
    }

    public EntityFeedback getEntityName() {
        return this.entityName;
    }

    public Feedback entityName(EntityFeedback entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(EntityFeedback entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public Feedback entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Boolean getThumb() {
        return this.thumb;
    }

    public Feedback thumb(Boolean thumb) {
        this.thumb = thumb;
        return this;
    }

    public void setThumb(Boolean thumb) {
        this.thumb = thumb;
    }

    public String getReason() {
        return this.reason;
    }

    public Feedback reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getSolved() {
        return this.solved;
    }

    public Feedback solved(Boolean solved) {
        this.solved = solved;
        return this;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Boolean getAnonym() {
        return this.anonym;
    }

    public Feedback anonym(Boolean anonym) {
        this.anonym = anonym;
        return this;
    }

    public void setAnonym(Boolean anonym) {
        this.anonym = anonym;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feedback)) {
            return false;
        }
        return id != null && id.equals(((Feedback) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + getId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityId=" + getEntityId() +
            ", thumb='" + getThumb() + "'" +
            ", reason='" + getReason() + "'" +
            ", solved='" + getSolved() + "'" +
            ", anonym='" + getAnonym() + "'" +
            "}";
    }
}
