package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.TherapeuticRegime} entity.
 */
@ApiModel(description = "Regime terapêutico.")
public class TherapeuticRegimeDTO extends AbstractAuditingDTO implements Serializable {
    
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Acrónimo.
     */
    @ApiModelProperty(value = "Acrónimo.")
    private String acronym;

    /**
     * Propósito.
     */
    @NotNull
    @ApiModelProperty(value = "Propósito.", required = true)
    private String purpose;

    /**
     * Condição para administração.
     */
    @NotNull
    @ApiModelProperty(value = "Condição para administração.", required = true)
    private String condition;

    /**
     * Calendarização.
     */
    @ApiModelProperty(value = "Calendarização.")
    private String timing;

    /**
     * Indicação para prescrição.
     */
    @NotNull
    @ApiModelProperty(value = "Indicação para prescrição.", required = true)
    private String indication;

    /**
     * Critério de redução de dose.
     */
    @NotNull
    @ApiModelProperty(value = "Critério de redução de dose.", required = true)
    private String criteria;

    /**
     * Outras informações.
     */
    @ApiModelProperty(value = "Outras informações.")
    private String notice;

    private Set<DrugDTO> drugs = new HashSet<>();

    private Long treatmentId;

    private String treatmentType;
    
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<DrugDTO> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<DrugDTO> drugs) {
        this.drugs = drugs;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TherapeuticRegimeDTO)) {
            return false;
        }

        return id != null && id.equals(((TherapeuticRegimeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TherapeuticRegimeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", condition='" + getCondition() + "'" +
            ", timing='" + getTiming() + "'" +
            ", indication='" + getIndication() + "'" +
            ", criteria='" + getCriteria() + "'" +
            ", notice='" + getNotice() + "'" +
            ", drugs='" + getDrugs() + "'" +
            ", treatmentId=" + getTreatmentId() +
            ", treatmentType='" + getTreatmentType() + "'" +
            "}";
    }
}
