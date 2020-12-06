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
 * Medicamento.
 */
@Entity
@Table(name = "drug")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Drug extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    /**
     * Descrição geral.
     */
    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_drug__notice",
        joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "notice_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties(value = { "drugs" }, allowSetters = true)
    private Set<Notice> notices = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "drugs" }, allowSetters = true)
    private Administration administration;

    @ManyToMany(mappedBy = "drugs")
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

    public Drug id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Drug name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Drug description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Notice> getNotices() {
        return this.notices;
    }

    public Drug notices(Set<Notice> notices) {
        this.setNotices(notices);
        return this;
    }

    public Drug addNotice(Notice notice) {
        this.notices.add(notice);
        notice.getDrugs().add(this);
        return this;
    }

    public Drug removeNotice(Notice notice) {
        this.notices.remove(notice);
        notice.getDrugs().remove(this);
        return this;
    }

    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }

    public Administration getAdministration() {
        return this.administration;
    }

    public Drug administration(Administration administration) {
        this.setAdministration(administration);
        return this;
    }

    public void setAdministration(Administration administration) {
        this.administration = administration;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return this.therapeuticRegimes;
    }

    public Drug therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.setTherapeuticRegimes(therapeuticRegimes);
        return this;
    }

    public Drug addTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.add(therapeuticRegime);
        therapeuticRegime.getDrugs().add(this);
        return this;
    }

    public Drug removeTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.remove(therapeuticRegime);
        therapeuticRegime.getDrugs().remove(this);
        return this;
    }

    public void setTherapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        if (this.therapeuticRegimes != null) {
            this.therapeuticRegimes.forEach(i -> i.removeDrug(this));
        }
        if (therapeuticRegimes != null) {
            therapeuticRegimes.forEach(i -> i.addDrug(this));
        }
        this.therapeuticRegimes = therapeuticRegimes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drug)) {
            return false;
        }
        return id != null && id.equals(((Drug) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drug{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
