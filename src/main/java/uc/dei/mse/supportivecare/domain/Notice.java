package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Observação.
 */
@Entity
@Table(name = "notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notice extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Descrição.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    /**
     * Avaliação.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "evaluation", length = 1000, nullable = false)
    private String evaluation;

    /**
     * Intervenção interdependente.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "intervention", length = 1000, nullable = false)
    private String intervention;

    @ManyToMany(mappedBy = "notices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notices", "administration", "therapeuticRegimes" }, allowSetters = true)
    private Set<Drug> drugs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notice id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Notice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluation() {
        return this.evaluation;
    }

    public Notice evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getIntervention() {
        return this.intervention;
    }

    public Notice intervention(String intervention) {
        this.intervention = intervention;
        return this;
    }

    public void setIntervention(String intervention) {
        this.intervention = intervention;
    }

    public Set<Drug> getDrugs() {
        return this.drugs;
    }

    public Notice drugs(Set<Drug> drugs) {
        this.setDrugs(drugs);
        return this;
    }

    public Notice addDrug(Drug drug) {
        this.drugs.add(drug);
        drug.getNotices().add(this);
        return this;
    }

    public Notice removeDrug(Drug drug) {
        this.drugs.remove(drug);
        drug.getNotices().remove(this);
        return this;
    }

    public void setDrugs(Set<Drug> drugs) {
        if (this.drugs != null) {
            this.drugs.forEach(i -> i.removeNotice(this));
        }
        if (drugs != null) {
            drugs.forEach(i -> i.addNotice(this));
        }
        this.drugs = drugs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notice)) {
            return false;
        }
        return id != null && id.equals(((Notice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notice{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", intervention='" + getIntervention() + "'" +
            "}";
    }
}
