package uc.dei.mse.supportivecare.domain.projection;

/**
 * An interface projection for the Thumb count.
 */
public interface ThumbCount {
    Long getCountThumbUp();

    Long getCountThumbDown();

    Boolean getThumb();
}
