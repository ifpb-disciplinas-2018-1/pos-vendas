package ifpb.pos.service;

import ifpb.pos.domain.Cliente;
import ifpb.pos.domain.Produto;
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

    public Venda novaVendaParaCliente(String nome, String cpf, String email) {
        Cliente cliente = new Cliente(cpf, email, nome);
        Venda venda = new Venda(cliente);
        em.persist(venda);
        return venda;
    }

    public List<Venda> todasAsVendas() {
        return em.createQuery("FROM Venda v", Venda.class)
                .getResultList();
    }

    public Venda atualizarClienteDaVenda(String idDaVenda,
            String nome) {
        Venda venda = localizarPorId(idDaVenda);
        venda.getCliente().setNome(nome);
        return venda;
    }

    public Venda novoProduto(String idDaVenda, String descricao, double preco) {
        Venda venda = localizarPorId(idDaVenda);
        Produto produto = new Produto(descricao, preco);
        em.persist(produto);
        venda.novoProduto(produto);
        return venda;
    }

    public Venda finalizarVendaComId(String idDaVenda) {
        Venda venda = localizarPorId(idDaVenda);
        venda.finalizar();
        return venda;
    }
//    public Venda novosProdutos( String idDaVenda,  List<String> produtos) {
//        Venda venda = localizarPorId(idDaVenda);
//        venda.novosProdutos(produtos);
//        return venda;
//    }

}
