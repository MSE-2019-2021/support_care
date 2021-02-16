package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Feedback} entity.
 */
@ApiModel(description = "Feedback.")
public class FeedbackDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Tipo da entidade.
     */
    @NotNull
    @ApiModelProperty(value = "Tipo da entidade.", required = true)
    private EntityFeedback entityType;

    /**
     * Id da entidade.
     */
    @NotNull
    @ApiModelProperty(value = "Id da entidade.", required = true)
    private Long entityId;

    /**
     * Nome da entidade.
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Nome da entidade.")
    private String entityName;

    /**
     * Razão.
     */
    @Size(max = 3000)
    @ApiModelProperty(value = "Razão.")
    private String reason;

    /**
     * Resolvido.
     */
    @NotNull
    @ApiModelProperty(value = "Resolvido.", required = true)
    private Boolean solved;

    /**
     * Anónimo.
     */
    @NotNull
    @ApiModelProperty(value = "Anónimo.", required = true)
    private Boolean anonym;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityFeedback getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityFeedback entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Boolean getAnonym() {
        return anonym;
    }

    public void setAnonym(Boolean anonym) {
        this.anonym = anonym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackDTO)) {
            return false;
        }

        FeedbackDTO feedbackDTO = (FeedbackDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feedbackDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackDTO{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", reason='" + getReason() + "'" +
            ", solved='" + getSolved() + "'" +
            ", anonym='" + getAnonym() + "'" +
            "}";
    }
}
