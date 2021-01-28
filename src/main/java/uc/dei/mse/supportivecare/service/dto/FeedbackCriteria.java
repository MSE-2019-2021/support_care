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
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Feedback} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.FeedbackResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feedbacks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeedbackCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EntityFeedback
     */
    public static class EntityFeedbackFilter extends Filter<EntityFeedback> {

        public EntityFeedbackFilter() {}

        public EntityFeedbackFilter(EntityFeedbackFilter filter) {
            super(filter);
        }

        @Override
        public EntityFeedbackFilter copy() {
            return new EntityFeedbackFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private EntityFeedbackFilter entityType;

    private LongFilter entityId;

    private StringFilter entityName;

    private StringFilter reason;

    private BooleanFilter solved;

    private BooleanFilter anonym;

    public FeedbackCriteria() {}

    public FeedbackCriteria(FeedbackCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.entityType = other.entityType == null ? null : other.entityType.copy();
        this.entityId = other.entityId == null ? null : other.entityId.copy();
        this.entityName = other.entityName == null ? null : other.entityName.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.solved = other.solved == null ? null : other.solved.copy();
        this.anonym = other.anonym == null ? null : other.anonym.copy();
    }

    @Override
    public FeedbackCriteria copy() {
        return new FeedbackCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public EntityFeedbackFilter getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityFeedbackFilter entityType) {
        this.entityType = entityType;
    }

    public LongFilter getEntityId() {
        return entityId;
    }

    public void setEntityId(LongFilter entityId) {
        this.entityId = entityId;
    }

    public StringFilter getEntityName() {
        return entityName;
    }

    public void setEntityName(StringFilter entityName) {
        this.entityName = entityName;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public BooleanFilter getSolved() {
        return solved;
    }

    public void setSolved(BooleanFilter solved) {
        this.solved = solved;
    }

    public BooleanFilter getAnonym() {
        return anonym;
    }

    public void setAnonym(BooleanFilter anonym) {
        this.anonym = anonym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FeedbackCriteria that = (FeedbackCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(entityName, that.entityName) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(solved, that.solved) &&
            Objects.equals(anonym, that.anonym)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityType, entityId, entityName, reason, solved, anonym);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entityType != null ? "entityType=" + entityType + ", " : "") +
                (entityId != null ? "entityId=" + entityId + ", " : "") +
                (entityName != null ? "entityName=" + entityName + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (solved != null ? "solved=" + solved + ", " : "") +
                (anonym != null ? "anonym=" + anonym + ", " : "") +
            "}";
    }
}
