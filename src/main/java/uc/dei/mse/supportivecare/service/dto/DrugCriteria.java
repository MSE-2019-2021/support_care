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
 * Criteria class for the {@link uc.dei.mse.supportivecare.domain.Drug} entity. This class is used
 * in {@link uc.dei.mse.supportivecare.web.rest.DrugResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /drugs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DrugCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private LongFilter administrationId;

    private LongFilter noticeId;

    private LongFilter therapeuticRegimeId;

    public DrugCriteria() {
    }

    public DrugCriteria(DrugCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.administrationId = other.administrationId == null ? null : other.administrationId.copy();
        this.noticeId = other.noticeId == null ? null : other.noticeId.copy();
        this.therapeuticRegimeId = other.therapeuticRegimeId == null ? null : other.therapeuticRegimeId.copy();
    }

    @Override
    public DrugCriteria copy() {
        return new DrugCriteria(this);
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

    public LongFilter getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(LongFilter administrationId) {
        this.administrationId = administrationId;
    }

    public LongFilter getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(LongFilter noticeId) {
        this.noticeId = noticeId;
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
        final DrugCriteria that = (DrugCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(administrationId, that.administrationId) &&
            Objects.equals(noticeId, that.noticeId) &&
            Objects.equals(therapeuticRegimeId, that.therapeuticRegimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        administrationId,
        noticeId,
        therapeuticRegimeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrugCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (administrationId != null ? "administrationId=" + administrationId + ", " : "") +
                (noticeId != null ? "noticeId=" + noticeId + ", " : "") +
                (therapeuticRegimeId != null ? "therapeuticRegimeId=" + therapeuticRegimeId + ", " : "") +
            "}";
    }

}
