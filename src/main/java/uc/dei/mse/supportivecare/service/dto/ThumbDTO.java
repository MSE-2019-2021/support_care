package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import uc.dei.mse.supportivecare.domain.Thumb;

/**
 * A DTO for the Thumb count.
 */
@ApiModel(description = "Thumb.")
public class ThumbDTO implements Serializable, Thumb {

    public ThumbDTO() {}

    public ThumbDTO(Long countThumbUp, Long countThumbDown, Boolean thumb) {
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
        ThumbDTO thumbDTO = (ThumbDTO) o;
        return (
            countThumbUp.equals(thumbDTO.countThumbUp) &&
            countThumbDown.equals(thumbDTO.countThumbDown) &&
            Objects.equals(thumb, thumbDTO.thumb)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(countThumbUp, countThumbDown, thumb);
    }

    @Override
    public String toString() {
        return "ThumbDTO{" + "countThumbUp=" + countThumbUp + ", countThumbDown=" + countThumbDown + ", thumb=" + thumb + '}';
    }
}
