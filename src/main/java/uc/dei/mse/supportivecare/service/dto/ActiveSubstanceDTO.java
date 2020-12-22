package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.ActiveSubstance} entity.
 */
@ApiModel(description = "Substância Ativa (ou DCI: Denominação Comum Internacional).")
public class ActiveSubstanceDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Dosagem.
     */
    @NotNull
    @Size(max = 30)
    @ApiModelProperty(value = "Dosagem.", required = true)
    private String dosage;

    /**
     * Forma Farmacêutica.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Forma Farmacêutica.", required = true)
    private String form;

    /**
     * Descrição geral.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Descrição geral.")
    private String description;

    private Set<NoticeDTO> notices = new HashSet<>();

    private AdministrationDTO administration;

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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<NoticeDTO> getNotices() {
        return notices;
    }

    public void setNotices(Set<NoticeDTO> notices) {
        this.notices = notices;
    }

    public AdministrationDTO getAdministration() {
        return administration;
    }

    public void setAdministration(AdministrationDTO administration) {
        this.administration = administration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActiveSubstanceDTO)) {
            return false;
        }

        ActiveSubstanceDTO activeSubstanceDTO = (ActiveSubstanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, activeSubstanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActiveSubstanceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dosage='" + getDosage() + "'" +
            ", form='" + getForm() + "'" +
            ", description='" + getDescription() + "'" +
            ", notices=" + getNotices() +
            ", administration=" + getAdministration() +
            "}";
    }
}
