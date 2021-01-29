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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Outcome} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.OutcomeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /outcomes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OutcomeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter link;

    private LongFilter documentId;

    private LongFilter symptomId;

    public OutcomeCriteria() {}

    public OutcomeCriteria(OutcomeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.documentId = other.documentId == null ? null : other.documentId.copy();
        this.symptomId = other.symptomId == null ? null : other.symptomId.copy();
    }

    @Override
    public OutcomeCriteria copy() {
        return new OutcomeCriteria(this);
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

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public LongFilter getDocumentId() {
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
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
        final OutcomeCriteria that = (OutcomeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(link, that.link) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(symptomId, that.symptomId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, link, documentId, symptomId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutcomeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (documentId != null ? "documentId=" + documentId + ", " : "") +
                (symptomId != null ? "symptomId=" + symptomId + ", " : "") +
            "}";
    }
}
