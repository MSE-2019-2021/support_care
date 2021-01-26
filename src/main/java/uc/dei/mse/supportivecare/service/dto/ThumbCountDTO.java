package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import uc.dei.mse.supportivecare.domain.projection.ThumbCount;

/**
 * A DTO for the Thumb count.
 */
@ApiModel(description = "Thumb count.")
public class ThumbCountDTO implements Serializable, ThumbCount {

    public ThumbCountDTO() {}

    public ThumbCountDTO(Long countThumbUp, Long countThumbDown, Boolean thumb) {
        this.countThumbUp = countThumbUp;
        this.countThumbDown = countThumbDown;
        this.thumb = thumb;
    }

    /**
     * Soma polegar up.
     */
    @ApiModelProperty(value = "Soma polegar up.", required = true)
    private Long countThumbUp;

    /**
     * Soma polegar down.
     */
    @ApiModelProperty(value = "Soma polegar down.", required = true)
    private Long countThumbDown;

    /**
     * Polegar.
     */
    @ApiModelProperty(value = "Polegar.", required = true)
    private Boolean thumb;

    @Override
    public Long getCountThumbUp() {
        return countThumbUp;
    }

    public void setCountThumbUp(Long countThumbUp) {
        this.countThumbUp = countThumbUp;
    }

    @Override
    public Long getCountThumbDown() {
        return countThumbDown;
    }

    public void setCountThumbDown(Long countThumbDown) {
        this.countThumbDown = countThumbDown;
    }

    @Override
    public Boolean getThumb() {
        return thumb;
    }

    public void setThumb(Boolean thumb) {
        this.thumb = thumb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThumbCountDTO thumbCountDTO = (ThumbCountDTO) o;
        return (
            countThumbUp.equals(thumbCountDTO.countThumbUp) &&
            countThumbDown.equals(thumbCountDTO.countThumbDown) &&
            Objects.equals(thumb, thumbCountDTO.thumb)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(countThumbUp, countThumbDown, thumb);
    }

    @Override
    public String toString() {
        return "ThumbCountDTO{" + "countThumbUp=" + countThumbUp + ", countThumbDown=" + countThumbDown + ", thumb=" + thumb + '}';
    }
}
