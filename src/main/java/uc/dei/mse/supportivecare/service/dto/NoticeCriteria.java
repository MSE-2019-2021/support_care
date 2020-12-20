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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Notice} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.NoticeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NoticeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter evaluation;

    private StringFilter intervention;

    private LongFilter activeSubstanceId;

    public NoticeCriteria() {}

    public NoticeCriteria(NoticeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.intervention = other.intervention == null ? null : other.intervention.copy();
        this.activeSubstanceId = other.activeSubstanceId == null ? null : other.activeSubstanceId.copy();
    }

    @Override
    public NoticeCriteria copy() {
        return new NoticeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(StringFilter evaluation) {
        this.evaluation = evaluation;
    }

    public StringFilter getIntervention() {
        return intervention;
    }

    public void setIntervention(StringFilter intervention) {
        this.intervention = intervention;
    }

    public LongFilter getActiveSubstanceId() {
        return activeSubstanceId;
    }

    public void setActiveSubstanceId(LongFilter activeSubstanceId) {
        this.activeSubstanceId = activeSubstanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NoticeCriteria that = (NoticeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(intervention, that.intervention) &&
            Objects.equals(activeSubstanceId, that.activeSubstanceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, evaluation, intervention, activeSubstanceId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (intervention != null ? "intervention=" + intervention + ", " : "") +
                (activeSubstanceId != null ? "activeSubstanceId=" + activeSubstanceId + ", " : "") +
            "}";
    }
}
