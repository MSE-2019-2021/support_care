package uc.dei.mse.supportivecare.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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

    private LongFilter drugId;

    public NoticeCriteria() {
    }

    public NoticeCriteria(NoticeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.evaluation = other.evaluation == null ? null : other.evaluation.copy();
        this.intervention = other.intervention == null ? null : other.intervention.copy();
        this.drugId = other.drugId == null ? null : other.drugId.copy();
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

    public LongFilter getDrugId() {
        return drugId;
    }

    public void setDrugId(LongFilter drugId) {
        this.drugId = drugId;
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
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(intervention, that.intervention) &&
            Objects.equals(drugId, that.drugId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        evaluation,
        intervention,
        drugId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (evaluation != null ? "evaluation=" + evaluation + ", " : "") +
                (intervention != null ? "intervention=" + intervention + ", " : "") +
                (drugId != null ? "drugId=" + drugId + ", " : "") +
            "}";
    }

}
