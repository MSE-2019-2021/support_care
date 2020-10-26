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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Diagnostic} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.DiagnosticResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /diagnostics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DiagnosticCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter notice;

    private LongFilter therapeuticRegimeId;

    public DiagnosticCriteria() {
    }

    public DiagnosticCriteria(DiagnosticCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.notice = other.notice == null ? null : other.notice.copy();
        this.therapeuticRegimeId = other.therapeuticRegimeId == null ? null : other.therapeuticRegimeId.copy();
    }

    @Override
    public DiagnosticCriteria copy() {
        return new DiagnosticCriteria(this);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DiagnosticCriteria that = (DiagnosticCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(notice, that.notice) &&
            Objects.equals(therapeuticRegimeId, that.therapeuticRegimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        notice,
        therapeuticRegimeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiagnosticCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (notice != null ? "notice=" + notice + ", " : "") +
                (therapeuticRegimeId != null ? "therapeuticRegimeId=" + therapeuticRegimeId + ", " : "") +
            "}";
    }

}
