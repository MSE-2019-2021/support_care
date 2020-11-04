package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Drug} entity.
 */
@ApiModel(description = "Medicamento.")
public class DrugDTO implements Serializable {
    
    private Long id;

    /**
     * Nome do medicamento.
     */
    @NotNull
    @ApiModelProperty(value = "Nome do medicamento.", required = true)
    private String name;

    /**
     * Descrição.
     */
    @ApiModelProperty(value = "Descrição.")
    private String description;


    private Long administrationId;

    private String administrationType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(Long administrationId) {
        this.administrationId = administrationId;
    }

    public String getAdministrationType() {
        return administrationType;
    }

    public void setAdministrationType(String administrationType) {
        this.administrationType = administrationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrugDTO)) {
            return false;
        }

        return id != null && id.equals(((DrugDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrugDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", administrationId=" + getAdministrationId() +
            ", administrationType='" + getAdministrationType() + "'" +
            "}";
    }
}
