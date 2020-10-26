package uc.dei.mse.supportivecare.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Sintoma (Efeito secundário).
 */
@Entity
@Table(name = "diagnostic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Diagnostic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Informação ao enfermeiro.
     */
    @Column(name = "notice")
    private String notice;

    @OneToMany(mappedBy = "diagnostic")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TherapeuticRegime> therapeuticRegimes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Diagnostic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return notice;
    }

    public Diagnostic notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return therapeuticRegimes;
    }

    public Diagnostic therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.therapeuticRegimes = therapeuticRegimes;
        return this;
    }

    public Diagnostic addTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.add(therapeuticRegime);
        therapeuticRegime.setDiagnostic(this);
        return this;
    }

    public Diagnostic removeTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.remove(therapeuticRegime);
        therapeuticRegime.setDiagnostic(null);
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
        if (!(o instanceof Diagnostic)) {
            return false;
        }
        return id != null && id.equals(((Diagnostic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diagnostic{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notice='" + getNotice() + "'" +
            "}";
    }
}
