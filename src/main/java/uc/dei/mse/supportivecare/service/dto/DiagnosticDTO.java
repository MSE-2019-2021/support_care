package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Diagnostic} entity.
 */
@ApiModel(description = "Sintoma (Efeito secundário).")
public class DiagnosticDTO implements Serializable {
    
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Informação ao enfermeiro.
     */
    @ApiModelProperty(value = "Informação ao enfermeiro.")
    private String notice;

    
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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiagnosticDTO)) {
            return false;
        }

        return id != null && id.equals(((DiagnosticDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiagnosticDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notice='" + getNotice() + "'" +
            "}";
    }
}
