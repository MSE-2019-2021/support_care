package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Thumb} entity.
 */
@ApiModel(description = "Thumb.")
public class ThumbDTO extends AbstractAuditingDTO implements Serializable {

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
     * Polegar.
     */
    @ApiModelProperty(value = "Polegar.", required = true)
    private Boolean thumb;

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

    public Boolean getThumb() {
        return thumb;
    }

    public void setThumb(Boolean thumb) {
        this.thumb = thumb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThumbDTO)) {
            return false;
        }

        ThumbDTO thumbDTO = (ThumbDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thumbDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThumbDTO{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            ", thumb='" + getThumb() + "'" +
            "}";
    }
}
