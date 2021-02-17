package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Notice} entity.
 */
@ApiModel(description = "Observação.")
public class NoticeDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Descrição.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Descrição.", required = true)
    private String description;

    /**
     * Avaliação.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Avaliação.", required = true)
    private String evaluation;

    /**
     * Intervenção interdependente.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Intervenção interdependente.", required = true)
    private String intervention;

    private ActiveSubstanceDTO activeSubstance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getIntervention() {
        return intervention;
    }

    public void setIntervention(String intervention) {
        this.intervention = intervention;
    }

    public ActiveSubstanceDTO getActiveSubstance() {
        return activeSubstance;
    }

    public void setActiveSubstance(ActiveSubstanceDTO activeSubstance) {
        this.activeSubstance = activeSubstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticeDTO)) {
            return false;
        }

        NoticeDTO noticeDTO = (NoticeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noticeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", intervention='" + getIntervention() + "'" +
            ", activeSubstance=" + getActiveSubstance() +
            "}";
    }
}
