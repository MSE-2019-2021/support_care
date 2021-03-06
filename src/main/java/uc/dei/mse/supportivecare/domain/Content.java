package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Conteúdo.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Content extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_content_id_seq")
    @SequenceGenerator(name = "gen_content_id_seq", sequenceName = "content_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Dados.
     */

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Column(name = "data_content_type", nullable = false)
    private String dataContentType;

    @JsonIgnoreProperties(value = { "content", "outcome" }, allowSetters = true)
    @OneToOne(mappedBy = "content")
    private Document document;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Content id(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getData() {
        return this.data;
    }

    public Content data(byte[] data) {
        this.data = data;
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return this.dataContentType;
    }

    public Content dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Document getDocument() {
        return this.document;
    }

    public Content document(Document document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(Document document) {
        if (this.document != null) {
            this.document.setContent(null);
        }
        if (document != null) {
            document.setContent(this);
        }
        this.document = document;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        return id != null && id.equals(((Content) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            "}";
    }
}
