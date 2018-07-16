package ifpb.pos.domain;

import ifpb.pos.api.Link;
import ifpb.pos.api.ResourceClientes;
import ifpb.pos.api.ResourceProdutos;
import ifpb.pos.api.ResourceVendas;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 10:45:19
 */
@XmlRootElement
public class VendaSimples implements Serializable {

    private Link cliente;
    private String id;
    private Date criadaEm;
    private List<Link> produtos = new ArrayList<>();

//    private List<Link> operacoes = new ArrayList<>();
    private Link finalizar;

    public VendaSimples() {
    }

    public VendaSimples(Venda venda, UriInfo uriInfo) {
        this.criadaEm = venda.getCriadaEm();
        this.id = venda.getId();

        iniciarCliente(venda, uriInfo);
        iniciarProdutos(venda, uriInfo);
        iniciarOperacoes(venda, uriInfo);

    }

    private void iniciarCliente(Venda venda, UriInfo uriInfo) {
        ////http://localhost:8080/pos-vendas/api/clientes/123
        Cliente clienteDaVenda = venda.getCliente();
        String cpf = clienteDaVenda.getCpf();
        URI build = uriInfo.getBaseUriBuilder() //http://localhost:8080/pos-vendas/api/
                .path(ResourceClientes.class) //http://localhost:8080/pos-vendas/api/clientes
                .path(cpf)
                .build();
        this.cliente = new Link(clienteDaVenda.getNome(), build.toString());

    }

    private void iniciarProdutos(Venda venda, final UriInfo uriInfo) {
        this.produtos = venda.getProdutos()
                .stream()
                .map((p) -> convertToLink(p, uriInfo))
                .collect(Collectors.toList());
    }

    private Link convertToLink(final Produto produto, final UriInfo uriInfo) {
        String idProduto = String.valueOf(produto.getId());
        URI build = uriInfo.getBaseUriBuilder() //http://localhost:8080/pos-vendas/api/
                .path(ResourceProdutos.class) //http://localhost:8080/pos-vendas/api/produtos
                .path(idProduto)
                .build();
        return new Link(produto.getDescricao(), build.toString());
    }

    private void iniciarOperacoes(Venda venda, UriInfo uriInfo) {
        URI build = uriInfo.getBaseUriBuilder() //http://localhost:8080/pos-vendas/api/
                .path(ResourceVendas.class) //http://localhost:8080/pos-vendas/api/vendas
                .path(venda.getId())
                .path("finalizar")
                .build();
        this.finalizar = new Link("finalizar", build.toString());
    }

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
