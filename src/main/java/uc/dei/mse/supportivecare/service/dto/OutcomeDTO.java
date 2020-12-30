package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;
import uc.dei.mse.supportivecare.domain.Document;
import uc.dei.mse.supportivecare.domain.Outcome;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Outcome} entity.
 */
@ApiModel(description = "PROM.")
public class OutcomeDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Nome.", required = true)
    private String name;

    /**
     * Descrição.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Descrição.")
    private String description;

    private Set<OutcomeDTO> outcomes = new HashSet<>();

    private Set<DocumentDTO> documents = new HashSet<>();

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

    public void setOutcome(Set<OutcomeDTO> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<OutcomeDTO> getOutcomes() {
        return outcomes;
    }

    public Set<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentDTO> documents) {
        this.documents = documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutcomeDTO)) {
            return false;
        }

        OutcomeDTO outcomeDTO = (OutcomeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, outcomeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutcomeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", outcomes='" + getOutcomes() + "'" +
            "}";
    }

    public OutcomeDTO addDocument(DocumentDTO document) {
        this.documents.add(document);
        document.setOutcome(this);
        return this;
    }
}
