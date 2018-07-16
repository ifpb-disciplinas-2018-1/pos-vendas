package ifpb.pos.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 12/07/2018, 10:34:50
 */
@Entity
@XmlRootElement
public class Venda implements Serializable {

    @Id
    private String id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criadaEm;

    @ManyToMany
    private List<Produto> produtos = new ArrayList<>();

    private Status status;

    public Venda() {
        this.criadaEm = Date.from(Instant.now());
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.status = Status.CRIADA;
    }

    public Venda(Cliente cliente) {
        this();
        this.cliente = cliente;

    }

    public void novoProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public void finalizar() {
        this.status = Status.FINALIZADA;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(Date criadaEm) {
        this.criadaEm = criadaEm;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.cliente);
        hash = 79 * hash + Objects.hashCode(this.criadaEm);
        hash = 79 * hash + Objects.hashCode(this.produtos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.criadaEm, other.criadaEm)) {
            return false;
        }
        if (!Objects.equals(this.produtos, other.produtos)) {
            return false;
        }
        return true;
    }

}

enum Status {
    CRIADA,
    FINALIZADA
}
