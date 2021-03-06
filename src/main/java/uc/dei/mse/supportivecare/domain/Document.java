package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Documento.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_document_id_seq")
    @SequenceGenerator(name = "gen_document_id_seq", sequenceName = "document_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Título.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    /**
     * Tamanho.
     */
    @NotNull
    @Column(name = "size", nullable = false)
    private Long size;

    /**
     * Tipo de ficheiro: https://tools.ietf.org/html/rfc6838#section-4.2
     */
    @Size(max = 127)
    @Column(name = "mime_type", length = 127)
    private String mimeType;

    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Content content;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "documents", "symptoms" }, allowSetters = true)
    private Outcome outcome;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Document title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSize() {
        return this.size;
    }

    public Document size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Document mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Content getContent() {
        return this.content;
    }

    public Document content(Content content) {
        this.setContent(content);
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Outcome getOutcome() {
        return this.outcome;
    }

    public Document outcome(Outcome outcome) {
        this.setOutcome(outcome);
        return this;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", size=" + getSize() +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }
}
