package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Treatment} entity.
 */
@ApiModel(description = "Tratamento.")
public class TreatmentDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    /**
     * Tipo de Tratamento.
     */
    @NotNull
    @ApiModelProperty(value = "Tipo de Tratamento.", required = true)
    private String type;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TreatmentDTO)) {
            return false;
        }

        return id != null && id.equals(((TreatmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TreatmentDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
