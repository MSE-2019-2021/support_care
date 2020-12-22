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
 * Substância Ativa (ou DCI: Denominação Comum Internacional).
 */
@Entity
@Table(name = "active_substance", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "dosage", "form" }) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActiveSubstance extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_active_substance_id_seq")
    @SequenceGenerator(name = "gen_active_substance_id_seq", sequenceName = "active_substance_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    /**
     * Dosagem.
     */
    @NotNull
    @Size(max = 30)
    @Column(name = "dosage", length = 30, nullable = false)
    private String dosage;

    /**
     * Forma Farmacêutica.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "form", length = 255, nullable = false)
    private String form;

    /**
     * Descrição geral.
     */
    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "activeSubstance", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activeSubstance" }, allowSetters = true)
    private Set<Notice> notices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "activeSubstances" }, allowSetters = true)
    private Administration administration;

    @ManyToMany(mappedBy = "activeSubstances")
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

    public ActiveSubstance id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ActiveSubstance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return this.dosage;
    }

    public ActiveSubstance dosage(String dosage) {
        this.dosage = dosage;
        return this;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getForm() {
        return this.form;
    }

    public ActiveSubstance form(String form) {
        this.form = form;
        return this;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDescription() {
        return this.description;
    }

    public ActiveSubstance description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Notice> getNotices() {
        return this.notices;
    }

    public ActiveSubstance notices(Set<Notice> notices) {
        this.setNotices(notices);
        return this;
    }

    public ActiveSubstance addNotice(Notice notice) {
        this.notices.add(notice);
        notice.setActiveSubstance(this);
        return this;
    }

    public ActiveSubstance removeNotice(Notice notice) {
        this.notices.remove(notice);
        notice.setActiveSubstance(null);
        return this;
    }

    public void setNotices(Set<Notice> notices) {
        if (this.notices != null) {
            this.notices.forEach(i -> i.setActiveSubstance(null));
        }
        if (notices != null) {
            notices.forEach(i -> i.setActiveSubstance(this));
        }
        this.notices = notices;
    }

    public Administration getAdministration() {
        return this.administration;
    }

    public ActiveSubstance administration(Administration administration) {
        this.setAdministration(administration);
        return this;
    }

    public void setAdministration(Administration administration) {
        this.administration = administration;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return this.therapeuticRegimes;
    }

    public ActiveSubstance therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.setTherapeuticRegimes(therapeuticRegimes);
        return this;
    }

    public ActiveSubstance addTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.add(therapeuticRegime);
        therapeuticRegime.getActiveSubstances().add(this);
        return this;
    }

    public ActiveSubstance removeTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.remove(therapeuticRegime);
        therapeuticRegime.getActiveSubstances().remove(this);
        return this;
    }

    public void setTherapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        if (this.therapeuticRegimes != null) {
            this.therapeuticRegimes.forEach(i -> i.removeActiveSubstance(this));
        }
        if (therapeuticRegimes != null) {
            therapeuticRegimes.forEach(i -> i.addActiveSubstance(this));
        }
        this.therapeuticRegimes = therapeuticRegimes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActiveSubstance)) {
            return false;
        }
        return id != null && id.equals(((ActiveSubstance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActiveSubstance{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dosage='" + getDosage() + "'" +
            ", form='" + getForm() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
