package uc.dei.mse.supportcare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportcare.domain.Protocol} entity.
 */
@ApiModel(description = "Protocolo.")
public class ProtocolDTO implements Serializable {
    
    private Long id;

    /**
     * Diagnóstico de toxicidade.
     */
    @NotNull
    @ApiModelProperty(value = "Diagnóstico de toxicidade.", required = true)
    private String toxicityDiagnosis;

    /**
     * Utilizador que criou o registo.
     */
    @NotNull
    @ApiModelProperty(value = "Utilizador que criou o registo.", required = true)
    private String createUser;

    /**
     * Data de criação.
     */
    @NotNull
    @ApiModelProperty(value = "Data de criação.", required = true)
    private Instant createDate;

    /**
     * Utilizador que actualizou o registo.
     */
    @NotNull
    @ApiModelProperty(value = "Utilizador que actualizou o registo.", required = true)
    private String updateUser;

    /**
     * Data de actualização.
     */
    @NotNull
    @ApiModelProperty(value = "Data de actualização.", required = true)
    private Instant updateDate;


    private Long therapeuticRegimeId;

    private Long outcomeId;

    private Long guideId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToxicityDiagnosis() {
        return toxicityDiagnosis;
    }

    public void setToxicityDiagnosis(String toxicityDiagnosis) {
        this.toxicityDiagnosis = toxicityDiagnosis;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Long getTherapeuticRegimeId() {
        return therapeuticRegimeId;
    }

    public void setTherapeuticRegimeId(Long therapeuticRegimeId) {
        this.therapeuticRegimeId = therapeuticRegimeId;
    }

    public Long getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Long outcomeId) {
        this.outcomeId = outcomeId;
    }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProtocolDTO)) {
            return false;
        }

        return id != null && id.equals(((ProtocolDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProtocolDTO{" +
            "id=" + getId() +
            ", toxicityDiagnosis='" + getToxicityDiagnosis() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", therapeuticRegimeId=" + getTherapeuticRegimeId() +
            ", outcomeId=" + getOutcomeId() +
            ", guideId=" + getGuideId() +
            "}";
    }
}
