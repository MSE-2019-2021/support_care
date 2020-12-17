package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Drug} entity.
 */
@ApiModel(description = "Medicamento.")
public class DrugDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 250)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Descrição geral.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Descrição geral.")
    private String description;

    private AdministrationDTO administration;

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

    public AdministrationDTO getAdministration() {
        return administration;
    }

    public void setAdministration(AdministrationDTO administration) {
        this.administration = administration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrugDTO)) {
            return false;
        }

        DrugDTO drugDTO = (DrugDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, drugDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrugDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", administration=" + getAdministration() +
            "}";
    }
}
