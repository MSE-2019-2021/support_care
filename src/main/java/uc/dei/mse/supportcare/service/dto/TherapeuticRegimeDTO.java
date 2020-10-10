package uc.dei.mse.supportcare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportcare.domain.TherapeuticRegime} entity.
 */
@ApiModel(description = "Regime terapêutico.")
public class TherapeuticRegimeDTO implements Serializable {
    
    private Long id;

    /**
     * Calendarização.
     */
    @NotNull
    @ApiModelProperty(value = "Calendarização.", required = true)
    private String timing;

    /**
     * Indicações sobre o regime.
     */
    @NotNull
    @ApiModelProperty(value = "Indicações sobre o regime.", required = true)
    private String dietary;

    /**
     * Efeitos secundários.
     */
    @NotNull
    @ApiModelProperty(value = "Efeitos secundários.", required = true)
    private String sideEffects;

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


    private Long drugId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDietary() {
        return dietary;
    }

    public void setDietary(String dietary) {
        this.dietary = dietary;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
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

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
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
            ", timing='" + getTiming() + "'" +
            ", dietary='" + getDietary() + "'" +
            ", sideEffects='" + getSideEffects() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", drugId=" + getDrugId() +
            "}";
    }
}
