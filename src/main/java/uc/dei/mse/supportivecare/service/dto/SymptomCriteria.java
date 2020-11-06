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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Symptom} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.SymptomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /symptoms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SymptomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter notice;

    private LongFilter therapeuticRegimeId;

    private LongFilter outcomeId;

    private LongFilter toxicityRateId;

    public SymptomCriteria() {}

    public SymptomCriteria(SymptomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.notice = other.notice == null ? null : other.notice.copy();
        this.therapeuticRegimeId = other.therapeuticRegimeId == null ? null : other.therapeuticRegimeId.copy();
        this.outcomeId = other.outcomeId == null ? null : other.outcomeId.copy();
        this.toxicityRateId = other.toxicityRateId == null ? null : other.toxicityRateId.copy();
    }

    @Override
    public SymptomCriteria copy() {
        return new SymptomCriteria(this);
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

    public StringFilter getNotice() {
        return notice;
    }

    public void setNotice(StringFilter notice) {
        this.notice = notice;
    }

    public LongFilter getTherapeuticRegimeId() {
        return therapeuticRegimeId;
    }

    public void setTherapeuticRegimeId(LongFilter therapeuticRegimeId) {
        this.therapeuticRegimeId = therapeuticRegimeId;
    }

    public LongFilter getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(LongFilter outcomeId) {
        this.outcomeId = outcomeId;
    }

    public LongFilter getToxicityRateId() {
        return toxicityRateId;
    }

    public void setToxicityRateId(LongFilter toxicityRateId) {
        this.toxicityRateId = toxicityRateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SymptomCriteria that = (SymptomCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(notice, that.notice) &&
            Objects.equals(therapeuticRegimeId, that.therapeuticRegimeId) &&
            Objects.equals(outcomeId, that.outcomeId) &&
            Objects.equals(toxicityRateId, that.toxicityRateId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, notice, therapeuticRegimeId, outcomeId, toxicityRateId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SymptomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (notice != null ? "notice=" + notice + ", " : "") +
                (therapeuticRegimeId != null ? "therapeuticRegimeId=" + therapeuticRegimeId + ", " : "") +
                (outcomeId != null ? "outcomeId=" + outcomeId + ", " : "") +
                (toxicityRateId != null ? "toxicityRateId=" + toxicityRateId + ", " : "") +
            "}";
    }
}
