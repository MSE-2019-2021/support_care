package uc.dei.mse.supportivecare.service.dto;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Thumb} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.ThumbResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /thumbs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThumbCriteria implements Serializable, Criteria {

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

    private BooleanFilter thumb;

    public ThumbCriteria() {}

    public ThumbCriteria(ThumbCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.entityType = other.entityType == null ? null : other.entityType.copy();
        this.entityId = other.entityId == null ? null : other.entityId.copy();
        this.thumb = other.thumb == null ? null : other.thumb.copy();
    }

    @Override
    public ThumbCriteria copy() {
        return new ThumbCriteria(this);
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

    public BooleanFilter getThumb() {
        return thumb;
    }

    public void setThumb(BooleanFilter thumb) {
        this.thumb = thumb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThumbCriteria that = (ThumbCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(thumb, that.thumb)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityType, entityId, thumb);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThumbCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entityType != null ? "entityType=" + entityType + ", " : "") +
                (entityId != null ? "entityId=" + entityId + ", " : "") +
                (thumb != null ? "thumb=" + thumb + ", " : "") +
            "}";
    }
}
