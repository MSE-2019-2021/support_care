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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Tipo de Tratamento.
     */
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "drugs", "treatment", "symptoms" }, allowSetters = true)
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
        return type;
    }

    public Treatment type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return therapeuticRegimes;
    }

    public Treatment therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.therapeuticRegimes = therapeuticRegimes;
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
        return 31;
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
