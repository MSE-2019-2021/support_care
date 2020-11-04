package uc.dei.mse.supportivecare.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Administração.
 */
@Entity
@Table(name = "administration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Administration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Tipo de Administração.
     */
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "administration")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Drug> drugs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Administration type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public Administration drugs(Set<Drug> drugs) {
        this.drugs = drugs;
        return this;
    }

    public Administration addDrug(Drug drug) {
        this.drugs.add(drug);
        drug.setAdministration(this);
        return this;
    }

    public Administration removeDrug(Drug drug) {
        this.drugs.remove(drug);
        drug.setAdministration(null);
        return this;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
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
        return 31;
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
