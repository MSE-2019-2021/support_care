package uc.dei.mse.supportcare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportcare.domain.Drug} entity.
 */
@ApiModel(description = "Medicamento.")
public class DrugDTO implements Serializable {
    
    private Long id;

    /**
     * Nome do medicamento.
     */
    @NotNull
    @ApiModelProperty(value = "Nome do medicamento.", required = true)
    private String drugName;

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

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrugDTO)) {
            return false;
        }

        return id != null && id.equals(((DrugDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrugDTO{" +
            "id=" + getId() +
            ", drugName='" + getDrugName() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
