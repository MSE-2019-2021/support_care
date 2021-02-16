package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Document} entity.
 */
@ApiModel(description = "Documento.")
public class DocumentDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Título.
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Título.", required = true)
    private String title;

    /**
     * Tamanho.
     */
    @NotNull
    @ApiModelProperty(value = "Tamanho.", required = true)
    private Long size;

    /**
     * Tipo de ficheiro: https://tools.ietf.org/html/rfc6838#section-4.2
     */
    @Size(max = 127)
    @ApiModelProperty(value = "Tipo de ficheiro.")
    private String mimeType;

    private ContentDTO content;

    private OutcomeDTO outcome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public ContentDTO getContent() {
        return content;
    }

    public void setContent(ContentDTO content) {
        this.content = content;
    }

    public OutcomeDTO getOutcome() {
        return outcome;
    }

    public void setOutcome(OutcomeDTO outcome) {
        this.outcome = outcome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentDTO)) {
            return false;
        }

        DocumentDTO documentDTO = (DocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", size=" + getSize() +
            ", mimeType='" + getMimeType() + "'" +
            ", content=" + getContent() +
            ", outcome=" + getOutcome() +
            "}";
    }

    public void addContent(byte[] data) {
        ContentDTO content = new ContentDTO();
        content.setData(data);
        content.setDataContentType("not-used"); // the field is generated by JHipster but redundant in our case, we can delete it later
        this.content = content;
    }

    public byte[] retrieveContent() {
        return content.getData();
    }
}
