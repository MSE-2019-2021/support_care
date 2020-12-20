package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Outcome} entity.
 */
@ApiModel(description = "PROM.")
public class OutcomeDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Descrição.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Descrição.")
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutcomeDTO)) {
            return false;
        }

        OutcomeDTO outcomeDTO = (OutcomeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, outcomeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutcomeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
