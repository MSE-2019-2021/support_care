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
 * Criteria class for the {@link uc.dei.mse.supportcare.domain.Drug} entity. This class is used
 * in {@link uc.dei.mse.supportcare.web.rest.DrugResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /drugs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DrugCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter drugName;

    private StringFilter createUser;

    private InstantFilter createDate;

    private StringFilter updateUser;

    private InstantFilter updateDate;

    public DrugCriteria() {
    }

    public DrugCriteria(DrugCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.drugName = other.drugName == null ? null : other.drugName.copy();
        this.createUser = other.createUser == null ? null : other.createUser.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.updateUser = other.updateUser == null ? null : other.updateUser.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
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

    public StringFilter getDrugName() {
        return drugName;
    }

    public void setDrugName(StringFilter drugName) {
        this.drugName = drugName;
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
            Objects.equals(drugName, that.drugName) &&
            Objects.equals(createUser, that.createUser) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        drugName,
        createUser,
        createDate,
        updateUser,
        updateDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrugCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (drugName != null ? "drugName=" + drugName + ", " : "") +
                (createUser != null ? "createUser=" + createUser + ", " : "") +
                (createDate != null ? "createDate=" + createDate + ", " : "") +
                (updateUser != null ? "updateUser=" + updateUser + ", " : "") +
                (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            "}";
    }

}
