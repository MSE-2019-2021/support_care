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
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    /**
     * Descrição.
     */
    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "outcome")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "content", "outcome" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @ManyToMany(mappedBy = "outcomes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "toxicityRates", "therapeuticRegimes", "outcomes" }, allowSetters = true)
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
        return this.name;
    }

    public Outcome name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Outcome description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public Outcome documents(Set<Document> documents) {
        this.setDocuments(documents);
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
        if (this.documents != null) {
            this.documents.forEach(i -> i.setOutcome(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setOutcome(this));
        }
        this.documents = documents;
    }

    public Set<Symptom> getSymptoms() {
        return this.symptoms;
    }

    public Outcome symptoms(Set<Symptom> symptoms) {
        this.setSymptoms(symptoms);
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
        if (this.symptoms != null) {
            this.symptoms.forEach(i -> i.removeOutcome(this));
        }
        if (symptoms != null) {
            symptoms.forEach(i -> i.addOutcome(this));
        }
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
