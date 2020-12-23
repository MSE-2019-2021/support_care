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
 * Tratamento.
 */
@Entity
@Table(name = "treatment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Treatment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_treatment_id_seq")
    @SequenceGenerator(name = "gen_treatment_id_seq", sequenceName = "treatment_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Tipo de tratamento.
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activeSubstances", "treatment", "symptoms" }, allowSetters = true)
    private Set<TherapeuticRegime> therapeuticRegimes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Treatment id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Treatment type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return this.therapeuticRegimes;
    }

    public Treatment therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.setTherapeuticRegimes(therapeuticRegimes);
        return this;
    }

    public Treatment addTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.add(therapeuticRegime);
        therapeuticRegime.setTreatment(this);
        return this;
    }

    public Treatment removeTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.remove(therapeuticRegime);
        therapeuticRegime.setTreatment(null);
        return this;
    }

    public void setTherapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        if (this.therapeuticRegimes != null) {
            this.therapeuticRegimes.forEach(i -> i.setTreatment(null));
        }
        if (therapeuticRegimes != null) {
            therapeuticRegimes.forEach(i -> i.setTreatment(this));
        }
        this.therapeuticRegimes = therapeuticRegimes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Treatment)) {
            return false;
        }
        return id != null && id.equals(((Treatment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
