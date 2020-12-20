package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Treatment} entity.
 */
@ApiModel(description = "Tratamento.")
public class TreatmentDTO implements Serializable {

    private Long id;

    /**
     * Tipo de tratamento.
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "Tipo de tratamento.", required = true)
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

        TreatmentDTO treatmentDTO = (TreatmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, treatmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
