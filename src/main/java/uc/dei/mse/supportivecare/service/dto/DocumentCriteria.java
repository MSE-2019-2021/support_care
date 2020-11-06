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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Document} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.DocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LongFilter size;

    private StringFilter mimeType;

    private LongFilter contentId;

    private LongFilter outcomeId;

    public DocumentCriteria() {}

    public DocumentCriteria(DocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.size = other.size == null ? null : other.size.copy();
        this.mimeType = other.mimeType == null ? null : other.mimeType.copy();
        this.contentId = other.contentId == null ? null : other.contentId.copy();
        this.outcomeId = other.outcomeId == null ? null : other.outcomeId.copy();
    }

    @Override
    public DocumentCriteria copy() {
        return new DocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getSize() {
        return size;
    }

    public void setSize(LongFilter size) {
        this.size = size;
    }

    public StringFilter getMimeType() {
        return mimeType;
    }

    public void setMimeType(StringFilter mimeType) {
        this.mimeType = mimeType;
    }

    public LongFilter getContentId() {
        return contentId;
    }

    public void setContentId(LongFilter contentId) {
        this.contentId = contentId;
    }

    public LongFilter getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(LongFilter outcomeId) {
        this.outcomeId = outcomeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentCriteria that = (DocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(size, that.size) &&
            Objects.equals(mimeType, that.mimeType) &&
            Objects.equals(contentId, that.contentId) &&
            Objects.equals(outcomeId, that.outcomeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, size, mimeType, contentId, outcomeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (size != null ? "size=" + size + ", " : "") +
                (mimeType != null ? "mimeType=" + mimeType + ", " : "") +
                (contentId != null ? "contentId=" + contentId + ", " : "") +
                (outcomeId != null ? "outcomeId=" + outcomeId + ", " : "") +
            "}";
    }
}
