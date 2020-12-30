package uc.dei.mse.supportivecare.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import uc.dei.mse.supportivecare.domain.Document;

/**
 * A DTO for the {@link uc.dei.mse.supportivecare.domain.Content} entity.
 */
@ApiModel(description = "Conteúdo.")
public class ContentDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    /**
     * Dados.
     */

    @ApiModelProperty(value = "Dados.", required = true)
    @Lob
    private byte[] data;

    private String dataContentType;

    private DocumentDTO document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public DocumentDTO getDocument() {
        return this.document;
    }

    public ContentDTO document(DocumentDTO document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(DocumentDTO document) {
        if (this.document != null) {
            this.document.setContent(null);
        }
        if (document != null) {
            document.setContent(this);
        }
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDTO)) {
            return false;
        }

        ContentDTO contentDTO = (ContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
