package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

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


    private Long drugId;

    private String drugName;
    
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

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticeDTO)) {
            return false;
        }

        return id != null && id.equals(((NoticeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", intervention='" + getIntervention() + "'" +
            ", drugId=" + getDrugId() +
            ", drugName='" + getDrugName() + "'" +
            "}";
    }
}
