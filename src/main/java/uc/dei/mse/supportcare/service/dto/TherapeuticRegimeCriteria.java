package uc.dei.mse.supportcare.service.dto;

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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link uc.dei.mse.supportcare.domain.TherapeuticRegime} entity. This class is used
 * in {@link uc.dei.mse.supportcare.web.rest.TherapeuticRegimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /therapeutic-regimes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TherapeuticRegimeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter timing;

    private StringFilter dietary;

    private StringFilter sideEffects;

    private StringFilter createUser;

    private InstantFilter createDate;

    private StringFilter updateUser;

    private InstantFilter updateDate;

    private LongFilter drugId;

    public TherapeuticRegimeCriteria() {
    }

    public TherapeuticRegimeCriteria(TherapeuticRegimeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.timing = other.timing == null ? null : other.timing.copy();
        this.dietary = other.dietary == null ? null : other.dietary.copy();
        this.sideEffects = other.sideEffects == null ? null : other.sideEffects.copy();
        this.createUser = other.createUser == null ? null : other.createUser.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.updateUser = other.updateUser == null ? null : other.updateUser.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.drugId = other.drugId == null ? null : other.drugId.copy();
    }

    @Override
    public TherapeuticRegimeCriteria copy() {
        return new TherapeuticRegimeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTiming() {
        return timing;
    }

    public void setTiming(StringFilter timing) {
        this.timing = timing;
    }

    public StringFilter getDietary() {
        return dietary;
    }

    public void setDietary(StringFilter dietary) {
        this.dietary = dietary;
    }

    public StringFilter getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(StringFilter sideEffects) {
        this.sideEffects = sideEffects;
    }

    public StringFilter getCreateUser() {
        return createUser;
    }

    public void setCreateUser(StringFilter createUser) {
        this.createUser = createUser;
    }

    public InstantFilter getCreateDate() {
        return createDate;
    }

    public void setCreateDate(InstantFilter createDate) {
        this.createDate = createDate;
    }

    public StringFilter getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(StringFilter updateUser) {
        this.updateUser = updateUser;
    }

    public InstantFilter getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(InstantFilter updateDate) {
        this.updateDate = updateDate;
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
        final TherapeuticRegimeCriteria that = (TherapeuticRegimeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timing, that.timing) &&
            Objects.equals(dietary, that.dietary) &&
            Objects.equals(sideEffects, that.sideEffects) &&
            Objects.equals(createUser, that.createUser) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(drugId, that.drugId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timing,
        dietary,
        sideEffects,
        createUser,
        createDate,
        updateUser,
        updateDate,
        drugId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TherapeuticRegimeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timing != null ? "timing=" + timing + ", " : "") +
                (dietary != null ? "dietary=" + dietary + ", " : "") +
                (sideEffects != null ? "sideEffects=" + sideEffects + ", " : "") +
                (createUser != null ? "createUser=" + createUser + ", " : "") +
                (createDate != null ? "createDate=" + createDate + ", " : "") +
                (updateUser != null ? "updateUser=" + updateUser + ", " : "") +
                (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
                (drugId != null ? "drugId=" + drugId + ", " : "") +
            "}";
    }

}
