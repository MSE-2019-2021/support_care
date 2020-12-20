package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Symptom} entity.
 */
@ApiModel(description = "Sintoma (Efeito secundário).")
public class SymptomDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Informação ao enfermeiro.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Informação ao enfermeiro.")
    private String notice;

    private Set<TherapeuticRegimeDTO> therapeuticRegimes = new HashSet<>();

    private Set<OutcomeDTO> outcomes = new HashSet<>();

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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<TherapeuticRegimeDTO> getTherapeuticRegimes() {
        return therapeuticRegimes;
    }

    public void setTherapeuticRegimes(Set<TherapeuticRegimeDTO> therapeuticRegimes) {
        this.therapeuticRegimes = therapeuticRegimes;
    }

    public Set<OutcomeDTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Set<OutcomeDTO> outcomes) {
        this.outcomes = outcomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SymptomDTO)) {
            return false;
        }

        SymptomDTO symptomDTO = (SymptomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, symptomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SymptomDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notice='" + getNotice() + "'" +
            ", therapeuticRegimes=" + getTherapeuticRegimes() +
            ", outcomes=" + getOutcomes() +
            "}";
    }
}
