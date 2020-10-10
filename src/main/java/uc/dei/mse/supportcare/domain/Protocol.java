package uc.dei.mse.supportcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Protocolo.
 */
@Entity
@Table(name = "protocol")
public class Protocol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Diagnóstico de toxicidade.
     */
    @NotNull
    @Column(name = "toxicity_diagnosis", nullable = false)
    private String toxicityDiagnosis;

    /**
     * Utilizador que criou o registo.
     */
    @NotNull
    @Column(name = "create_user", nullable = false)
    private String createUser;

    /**
     * Data de criação.
     */
    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    /**
     * Utilizador que actualizou o registo.
     */
    @NotNull
    @Column(name = "update_user", nullable = false)
    private String updateUser;

    /**
     * Data de actualização.
     */
    @NotNull
    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private TherapeuticRegime therapeuticRegime;

    @ManyToOne
    @JsonIgnoreProperties(value = "protocols", allowSetters = true)
    private Outcome outcome;

    @ManyToOne
    @JsonIgnoreProperties(value = "protocols", allowSetters = true)
    private Guide guide;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToxicityDiagnosis() {
        return toxicityDiagnosis;
    }

    public Protocol toxicityDiagnosis(String toxicityDiagnosis) {
        this.toxicityDiagnosis = toxicityDiagnosis;
        return this;
    }

    public void setToxicityDiagnosis(String toxicityDiagnosis) {
        this.toxicityDiagnosis = toxicityDiagnosis;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Protocol createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Protocol createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public Protocol updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Protocol updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public TherapeuticRegime getTherapeuticRegime() {
        return therapeuticRegime;
    }

    public Protocol therapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegime = therapeuticRegime;
        return this;
    }

    public void setTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegime = therapeuticRegime;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Protocol outcome(Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Guide getGuide() {
        return guide;
    }

    public Protocol guide(Guide guide) {
        this.guide = guide;
        return this;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Protocol)) {
            return false;
        }
        return id != null && id.equals(((Protocol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Protocol{" +
            "id=" + getId() +
            ", toxicityDiagnosis='" + getToxicityDiagnosis() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
