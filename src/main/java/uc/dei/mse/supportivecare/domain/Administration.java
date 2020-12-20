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
 * Administração.
 */
@Entity
@Table(name = "administration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Administration extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Tipo de administração.
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @OneToMany(mappedBy = "administration")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notices", "administration", "therapeuticRegimes" }, allowSetters = true)
    private Set<ActiveSubstance> activeSubstances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Administration id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Administration type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ActiveSubstance> getActiveSubstances() {
        return this.activeSubstances;
    }

    public Administration activeSubstances(Set<ActiveSubstance> activeSubstances) {
        this.setActiveSubstances(activeSubstances);
        return this;
    }

    public Administration addActiveSubstance(ActiveSubstance activeSubstance) {
        this.activeSubstances.add(activeSubstance);
        activeSubstance.setAdministration(this);
        return this;
    }

    public Administration removeActiveSubstance(ActiveSubstance activeSubstance) {
        this.activeSubstances.remove(activeSubstance);
        activeSubstance.setAdministration(null);
        return this;
    }

    public void setActiveSubstances(Set<ActiveSubstance> activeSubstances) {
        if (this.activeSubstances != null) {
            this.activeSubstances.forEach(i -> i.setAdministration(null));
        }
        if (activeSubstances != null) {
            activeSubstances.forEach(i -> i.setAdministration(this));
        }
        this.activeSubstances = activeSubstances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Administration)) {
            return false;
        }
        return id != null && id.equals(((Administration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Administration{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
