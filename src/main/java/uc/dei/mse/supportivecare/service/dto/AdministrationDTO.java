package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Administration} entity.
 */
@ApiModel(description = "Administração.")
public class AdministrationDTO extends AbstractAuditingDTO implements Serializable {
    
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

        return id != null && id.equals(((AdministrationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
