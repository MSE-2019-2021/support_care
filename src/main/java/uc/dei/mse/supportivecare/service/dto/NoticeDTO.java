package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import uc.dei.mse.supportivecare.GeneratedByJHipster;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Notice} entity.
 */
@ApiModel(description = "Observação.")
@GeneratedByJHipster
public class NoticeDTO implements Serializable {

    private Long id;

    /**
     * Descrição.
     */
    @NotNull
    @ApiModelProperty(value = "Descrição.", required = true)
    private String description;

    /**
     * Avaliação.
     */
    @NotNull
    @ApiModelProperty(value = "Avaliação.", required = true)
    private String evaluation;

    /**
     * Intervenção.
     */
    @NotNull
    @ApiModelProperty(value = "Intervenção.", required = true)
    private String intervention;

    private DrugDTO drug;

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

    public DrugDTO getDrug() {
        return drug;
    }

    public void setDrug(DrugDTO drug) {
        this.drug = drug;
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
            ", drug=" + getDrug() +
            "}";
    }
}
