package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.ToxicityRate} entity.
 */
@ApiModel(description = "Grau de Toxicidade.")
public class ToxicityRateDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 250)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Descrição.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Descrição.")
    private String description;

    /**
     * Informação ao doente.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Informação ao doente.")
    private String notice;

    /**
     * Intervenção autónoma.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Intervenção autónoma.")
    private String autonomousIntervention;

    /**
     * Intervenção interdependente.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Intervenção interdependente.")
    private String interdependentIntervention;

    /**
     * Suporte para auto-gestão.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Suporte para auto-gestão.")
    private String selfManagement;

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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAutonomousIntervention() {
        return autonomousIntervention;
    }

    public void setAutonomousIntervention(String autonomousIntervention) {
        this.autonomousIntervention = autonomousIntervention;
    }

    public String getInterdependentIntervention() {
        return interdependentIntervention;
    }

    public void setInterdependentIntervention(String interdependentIntervention) {
        this.interdependentIntervention = interdependentIntervention;
    }

    public String getSelfManagement() {
        return selfManagement;
    }

    public void setSelfManagement(String selfManagement) {
        this.selfManagement = selfManagement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ToxicityRateDTO)) {
            return false;
        }

        ToxicityRateDTO toxicityRateDTO = (ToxicityRateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, toxicityRateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ToxicityRateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", notice='" + getNotice() + "'" +
            ", autonomousIntervention='" + getAutonomousIntervention() + "'" +
            ", interdependentIntervention='" + getInterdependentIntervention() + "'" +
            ", selfManagement='" + getSelfManagement() + "'" +
            "}";
    }
}
