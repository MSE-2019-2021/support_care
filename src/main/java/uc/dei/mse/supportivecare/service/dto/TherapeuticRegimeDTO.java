package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.TherapeuticRegime} entity.
 */
@ApiModel(description = "Regime Terapêutico.")
public class TherapeuticRegimeDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Acrônimo.
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Acrônimo.")
    private String acronym;

    /**
     * Propósito.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Propósito.", required = true)
    private String purpose;

    /**
     * Condições para administração.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Condições para administração.", required = true)
    private String condition;

    /**
     * Calendarização.
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Calendarização.")
    private String timing;

    /**
     * Indicação para prescrição.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Indicação para prescrição.", required = true)
    private String indication;

    /**
     * Critérios de redução de dose.
     */
    @NotNull
    @Size(max = 3000)
    @ApiModelProperty(value = "Critérios de redução de dose.", required = true)
    private String criteria;

    /**
     * Outras informações.
     */
    @Size(max = 3000)
    @ApiModelProperty(value = "Outras informações.")
    private String notice;

    private Set<ActiveSubstanceDTO> activeSubstances = new HashSet<>();

    private TreatmentDTO treatment;

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

    public Set<ActiveSubstanceDTO> getActiveSubstances() {
        return activeSubstances;
    }

    public void setActiveSubstances(Set<ActiveSubstanceDTO> activeSubstances) {
        this.activeSubstances = activeSubstances;
    }

    public TreatmentDTO getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentDTO treatment) {
        this.treatment = treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TherapeuticRegimeDTO)) {
            return false;
        }

        TherapeuticRegimeDTO therapeuticRegimeDTO = (TherapeuticRegimeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, therapeuticRegimeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
            ", activeSubstances=" + getActiveSubstances() +
            ", treatment=" + getTreatment() +
            "}";
    }
}
