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
 * Criteria class for the {@link uc.dei.mse.supportcare.domain.Protocol} entity. This class is used
 * in {@link uc.dei.mse.supportcare.web.rest.ProtocolResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /protocols?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProtocolCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter toxicityDiagnosis;

    private StringFilter createUser;

    private InstantFilter createDate;

    private StringFilter updateUser;

    private InstantFilter updateDate;

    private LongFilter therapeuticRegimeId;

    private LongFilter outcomeId;

    private LongFilter guideId;

    public ProtocolCriteria() {
    }

    public ProtocolCriteria(ProtocolCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.toxicityDiagnosis = other.toxicityDiagnosis == null ? null : other.toxicityDiagnosis.copy();
        this.createUser = other.createUser == null ? null : other.createUser.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.updateUser = other.updateUser == null ? null : other.updateUser.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.therapeuticRegimeId = other.therapeuticRegimeId == null ? null : other.therapeuticRegimeId.copy();
        this.outcomeId = other.outcomeId == null ? null : other.outcomeId.copy();
        this.guideId = other.guideId == null ? null : other.guideId.copy();
    }

    @Override
    public ProtocolCriteria copy() {
        return new ProtocolCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getToxicityDiagnosis() {
        return toxicityDiagnosis;
    }

    public void setToxicityDiagnosis(StringFilter toxicityDiagnosis) {
        this.toxicityDiagnosis = toxicityDiagnosis;
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

    public LongFilter getTherapeuticRegimeId() {
        return therapeuticRegimeId;
    }

    public void setTherapeuticRegimeId(LongFilter therapeuticRegimeId) {
        this.therapeuticRegimeId = therapeuticRegimeId;
    }

    public LongFilter getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(LongFilter outcomeId) {
        this.outcomeId = outcomeId;
    }

    public LongFilter getGuideId() {
        return guideId;
    }

    public void setGuideId(LongFilter guideId) {
        this.guideId = guideId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProtocolCriteria that = (ProtocolCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(toxicityDiagnosis, that.toxicityDiagnosis) &&
            Objects.equals(createUser, that.createUser) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(therapeuticRegimeId, that.therapeuticRegimeId) &&
            Objects.equals(outcomeId, that.outcomeId) &&
            Objects.equals(guideId, that.guideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        toxicityDiagnosis,
        createUser,
        createDate,
        updateUser,
        updateDate,
        therapeuticRegimeId,
        outcomeId,
        guideId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProtocolCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (toxicityDiagnosis != null ? "toxicityDiagnosis=" + toxicityDiagnosis + ", " : "") +
                (createUser != null ? "createUser=" + createUser + ", " : "") +
                (createDate != null ? "createDate=" + createDate + ", " : "") +
                (updateUser != null ? "updateUser=" + updateUser + ", " : "") +
                (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
                (therapeuticRegimeId != null ? "therapeuticRegimeId=" + therapeuticRegimeId + ", " : "") +
                (outcomeId != null ? "outcomeId=" + outcomeId + ", " : "") +
                (guideId != null ? "guideId=" + guideId + ", " : "") +
            "}";
    }

}
