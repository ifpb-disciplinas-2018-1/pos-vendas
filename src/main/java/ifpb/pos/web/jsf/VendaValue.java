package ifpb.pos.web.jsf;

import ifpb.pos.api.Link;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 11:35:07
 */
public class VendaValue {

    private Link cliente;
    private Link finalizar;
    private String id;
    private Date criadaEm;
    private List<Link> produtos = new ArrayList<>();

    public Link getCliente() {
        return cliente;
    }

    public void setCliente(Link cliente) {
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(Date criadaEm) {
        this.criadaEm = criadaEm;
    }

    public List<Link> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Link> produtos) {
        this.produtos = produtos;
    }

    public Link getFinalizar() {
        return finalizar;
    }

    public void setFinalizar(Link finalizar) {
        this.finalizar = finalizar;
    }
    

}
