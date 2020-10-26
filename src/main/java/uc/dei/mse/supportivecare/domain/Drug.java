package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
     * Nome do medicamento.
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Descrição.
     */
    @Column(name = "description")
    private String description;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Administration administration;

    @OneToMany(mappedBy = "drug")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Notice> notices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "drugs", allowSetters = true)
    private TherapeuticRegime therapeuticRegime;

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

    public Drug name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Drug description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Administration getAdministration() {
        return administration;
    }

    public Drug administration(Administration administration) {
        this.administration = administration;
        return this;
    }

    public void setAdministration(Administration administration) {
        this.administration = administration;
    }

    public Set<Notice> getNotices() {
        return notices;
    }

    public Drug notices(Set<Notice> notices) {
        this.notices = notices;
        return this;
    }

    public Drug addNotice(Notice notice) {
        this.notices.add(notice);
        notice.setDrug(this);
        return this;
    }

    public Drug removeNotice(Notice notice) {
        this.notices.remove(notice);
        notice.setDrug(null);
        return this;
    }

    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }

    public TherapeuticRegime getTherapeuticRegime() {
        return therapeuticRegime;
    }

    public Drug therapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegime = therapeuticRegime;
        return this;
    }

    public void setTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegime = therapeuticRegime;
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
        return 31;
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
