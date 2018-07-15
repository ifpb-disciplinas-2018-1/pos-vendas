package ifpb.pos.service;

import ifpb.pos.domain.Venda;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 12/07/2018, 10:50:05
 */
@Stateless
public class ServiceDeVenda {

    @PersistenceContext
    private EntityManager em;

    public Venda localizarPorId(String id) {
        return em.find(Venda.class, id);
    }

    public Venda novaVendaParaCliente(String cliente) {
        Venda venda = new Venda(cliente);
        em.persist(venda);
        return venda;
    }

    public List<Venda> todasAsVendas() {
        return em.createQuery("FROM Venda v", Venda.class)
                .getResultList();
    }

    public Venda atualizarClienteDaVenda(String idDaVenda, String cliente) {
        Venda venda = localizarPorId(idDaVenda);
        venda.setCliente(cliente);
        return venda;
    }

    public Venda novoProduto(String idDaVenda, String produto) {
        Venda venda = localizarPorId(idDaVenda);
        venda.novoProduto(produto);
        return venda;
    }
    public Venda novosProdutos( String idDaVenda,  List<String> produtos) {
        Venda venda = localizarPorId(idDaVenda);
        venda.novosProdutos(produtos);
        return venda;
    }

}
