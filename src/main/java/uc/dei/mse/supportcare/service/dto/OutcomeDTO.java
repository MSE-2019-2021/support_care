package uc.dei.mse.supportcare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportcare.domain.Outcome} entity.
 */
@ApiModel(description = "Patient Reported Outcome Measures.")
public class OutcomeDTO implements Serializable {
    
    private Long id;

    /**
     * Patient Reported Outcome Measures.
     */
    @NotNull
    @ApiModelProperty(value = "Patient Reported Outcome Measures.", required = true)
    private String description;

    /**
     * Score, between 0 and 4.
     */
    @Min(value = 0)
    @Max(value = 4)
    @ApiModelProperty(value = "Score, between 0 and 4.")
    private Integer score;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
        if (!(o instanceof OutcomeDTO)) {
            return false;
        }

        return id != null && id.equals(((OutcomeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutcomeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", score=" + getScore() +
            ", createUser='" + getCreateUser() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
