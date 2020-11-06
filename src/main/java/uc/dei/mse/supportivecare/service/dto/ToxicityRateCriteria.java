package uc.dei.mse.supportivecare.service.dto;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.ToxicityRate} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.ToxicityRateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /toxicity-rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ToxicityRateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter notice;

    private StringFilter autonomousIntervention;

    private StringFilter interdependentIntervention;

    private StringFilter selfManagement;

    private LongFilter symptomId;

    public ToxicityRateCriteria() {}

    public ToxicityRateCriteria(ToxicityRateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.notice = other.notice == null ? null : other.notice.copy();
        this.autonomousIntervention = other.autonomousIntervention == null ? null : other.autonomousIntervention.copy();
        this.interdependentIntervention = other.interdependentIntervention == null ? null : other.interdependentIntervention.copy();
        this.selfManagement = other.selfManagement == null ? null : other.selfManagement.copy();
        this.symptomId = other.symptomId == null ? null : other.symptomId.copy();
    }

    @Override
    public ToxicityRateCriteria copy() {
        return new ToxicityRateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getNotice() {
        return notice;
    }

    public void setNotice(StringFilter notice) {
        this.notice = notice;
    }

    public StringFilter getAutonomousIntervention() {
        return autonomousIntervention;
    }

    public void setAutonomousIntervention(StringFilter autonomousIntervention) {
        this.autonomousIntervention = autonomousIntervention;
    }

    public StringFilter getInterdependentIntervention() {
        return interdependentIntervention;
    }

    public void setInterdependentIntervention(StringFilter interdependentIntervention) {
        this.interdependentIntervention = interdependentIntervention;
    }

    public StringFilter getSelfManagement() {
        return selfManagement;
    }

    public void setSelfManagement(StringFilter selfManagement) {
        this.selfManagement = selfManagement;
    }

    public LongFilter getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(LongFilter symptomId) {
        this.symptomId = symptomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ToxicityRateCriteria that = (ToxicityRateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(notice, that.notice) &&
            Objects.equals(autonomousIntervention, that.autonomousIntervention) &&
            Objects.equals(interdependentIntervention, that.interdependentIntervention) &&
            Objects.equals(selfManagement, that.selfManagement) &&
            Objects.equals(symptomId, that.symptomId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, notice, autonomousIntervention, interdependentIntervention, selfManagement, symptomId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ToxicityRateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (notice != null ? "notice=" + notice + ", " : "") +
                (autonomousIntervention != null ? "autonomousIntervention=" + autonomousIntervention + ", " : "") +
                (interdependentIntervention != null ? "interdependentIntervention=" + interdependentIntervention + ", " : "") +
                (selfManagement != null ? "selfManagement=" + selfManagement + ", " : "") +
                (symptomId != null ? "symptomId=" + symptomId + ", " : "") +
            "}";
    }
}
