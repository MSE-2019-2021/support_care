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
 * PROM.
 */
@Entity
@Table(name = "outcome")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Outcome extends AbstractAuditingEntity implements Serializable {

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
     * Descrição.
     */
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "outcome")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "content", "outcome" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @ManyToMany(mappedBy = "outcomes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "therapeuticRegimes", "outcomes", "toxicityRates" }, allowSetters = true)
    private Set<Symptom> symptoms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Outcome id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Outcome name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Outcome description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Outcome documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Outcome addDocument(Document document) {
        this.documents.add(document);
        document.setOutcome(this);
        return this;
    }

    public Outcome removeDocument(Document document) {
        this.documents.remove(document);
        document.setOutcome(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public Outcome symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public Outcome addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.getOutcomes().add(this);
        return this;
    }

    public Outcome removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.getOutcomes().remove(this);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Outcome)) {
            return false;
        }
        return id != null && id.equals(((Outcome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Outcome{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
