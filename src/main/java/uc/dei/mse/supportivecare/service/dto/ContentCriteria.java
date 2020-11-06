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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Content} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.ContentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter documentId;

    public ContentCriteria() {}

    public ContentCriteria(ContentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentId = other.documentId == null ? null : other.documentId.copy();
    }

    @Override
    public ContentCriteria copy() {
        return new ContentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getDocumentId() {
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContentCriteria that = (ContentCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(documentId, that.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentId != null ? "documentId=" + documentId + ", " : "") +
            "}";
    }
}
