package uc.dei.mse.supportcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Regime terapêutico.
 */
@Entity
@Table(name = "therapeutic_regime")
public class TherapeuticRegime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Calendarização.
     */
    @NotNull
    @Column(name = "timing", nullable = false)
    private String timing;

    /**
     * Indicações sobre o regime.
     */
    @NotNull
    @Column(name = "dietary", nullable = false)
    private String dietary;

    /**
     * Efeitos secundários.
     */
    @NotNull
    @Column(name = "side_effects", nullable = false)
    private String sideEffects;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "therapeuticRegimes", allowSetters = true)
    private Drug drug;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiming() {
        return timing;
    }

    public TherapeuticRegime timing(String timing) {
        this.timing = timing;
        return this;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDietary() {
        return dietary;
    }

    public TherapeuticRegime dietary(String dietary) {
        this.dietary = dietary;
        return this;
    }

    public void setDietary(String dietary) {
        this.dietary = dietary;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public TherapeuticRegime sideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
        return this;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getCreateUser() {
        return createUser;
    }

    public TherapeuticRegime createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public TherapeuticRegime createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public TherapeuticRegime updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public TherapeuticRegime updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Drug getDrug() {
        return drug;
    }

    public TherapeuticRegime drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TherapeuticRegime)) {
            return false;
        }
        return id != null && id.equals(((TherapeuticRegime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TherapeuticRegime{" +
            "id=" + getId() +
            ", timing='" + getTiming() + "'" +
            ", dietary='" + getDietary() + "'" +
            ", sideEffects='" + getSideEffects() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
