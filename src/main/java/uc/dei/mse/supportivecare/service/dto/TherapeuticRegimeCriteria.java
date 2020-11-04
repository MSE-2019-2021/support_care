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
import uc.dei.mse.supportivecare.GeneratedByJHipster;

/**
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.TherapeuticRegime} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.TherapeuticRegimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /therapeutic-regimes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@GeneratedByJHipster
public class TherapeuticRegimeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter acronym;

    private StringFilter purpose;

    private StringFilter condition;

    private StringFilter timing;

    private StringFilter indication;

    private StringFilter criteria;

    private StringFilter notice;

    private LongFilter drugId;

    private LongFilter treatmentId;

    public TherapeuticRegimeCriteria() {}

    public TherapeuticRegimeCriteria(TherapeuticRegimeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.acronym = other.acronym == null ? null : other.acronym.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
        this.condition = other.condition == null ? null : other.condition.copy();
        this.timing = other.timing == null ? null : other.timing.copy();
        this.indication = other.indication == null ? null : other.indication.copy();
        this.criteria = other.criteria == null ? null : other.criteria.copy();
        this.notice = other.notice == null ? null : other.notice.copy();
        this.drugId = other.drugId == null ? null : other.drugId.copy();
        this.treatmentId = other.treatmentId == null ? null : other.treatmentId.copy();
    }

    @Override
    public TherapeuticRegimeCriteria copy() {
        return new TherapeuticRegimeCriteria(this);
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

    public StringFilter getAcronym() {
        return acronym;
    }

    public void setAcronym(StringFilter acronym) {
        this.acronym = acronym;
    }

    public StringFilter getPurpose() {
        return purpose;
    }

    public void setPurpose(StringFilter purpose) {
        this.purpose = purpose;
    }

    public StringFilter getCondition() {
        return condition;
    }

    public void setCondition(StringFilter condition) {
        this.condition = condition;
    }

    public StringFilter getTiming() {
        return timing;
    }

    public void setTiming(StringFilter timing) {
        this.timing = timing;
    }

    public StringFilter getIndication() {
        return indication;
    }

    public void setIndication(StringFilter indication) {
        this.indication = indication;
    }

    public StringFilter getCriteria() {
        return criteria;
    }

    public void setCriteria(StringFilter criteria) {
        this.criteria = criteria;
    }

    public StringFilter getNotice() {
        return notice;
    }

    public void setNotice(StringFilter notice) {
        this.notice = notice;
    }

    public LongFilter getDrugId() {
        return drugId;
    }

    public void setDrugId(LongFilter drugId) {
        this.drugId = drugId;
    }

    public LongFilter getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(LongFilter treatmentId) {
        this.treatmentId = treatmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TherapeuticRegimeCriteria that = (TherapeuticRegimeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(acronym, that.acronym) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(condition, that.condition) &&
            Objects.equals(timing, that.timing) &&
            Objects.equals(indication, that.indication) &&
            Objects.equals(criteria, that.criteria) &&
            Objects.equals(notice, that.notice) &&
            Objects.equals(drugId, that.drugId) &&
            Objects.equals(treatmentId, that.treatmentId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, acronym, purpose, condition, timing, indication, criteria, notice, drugId, treatmentId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TherapeuticRegimeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (acronym != null ? "acronym=" + acronym + ", " : "") +
                (purpose != null ? "purpose=" + purpose + ", " : "") +
                (condition != null ? "condition=" + condition + ", " : "") +
                (timing != null ? "timing=" + timing + ", " : "") +
                (indication != null ? "indication=" + indication + ", " : "") +
                (criteria != null ? "criteria=" + criteria + ", " : "") +
                (notice != null ? "notice=" + notice + ", " : "") +
                (drugId != null ? "drugId=" + drugId + ", " : "") +
                (treatmentId != null ? "treatmentId=" + treatmentId + ", " : "") +
            "}";
    }
}
