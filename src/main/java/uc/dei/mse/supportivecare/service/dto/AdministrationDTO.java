package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import uc.dei.mse.supportivecare.GeneratedByJHipster;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Administration} entity.
 */
@ApiModel(description = "Administração.")
@GeneratedByJHipster
public class AdministrationDTO implements Serializable {

    private Long id;

    /**
     * Tipo de Administração.
     */
    @NotNull
    @ApiModelProperty(value = "Tipo de Administração.", required = true)
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
        if (!(o instanceof AdministrationDTO)) {
            return false;
        }

        AdministrationDTO administrationDTO = (AdministrationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administrationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrationDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
