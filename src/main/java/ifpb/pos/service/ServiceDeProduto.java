package ifpb.pos.service;

import ifpb.pos.domain.Produto;
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
public class ServiceDeProduto {

    @PersistenceContext
    private EntityManager em;

    public Produto localizarPorId(int id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> todosOsProdutos() {
        return em.createQuery("FROM Produto v", Produto.class)
                .getResultList();
    }
 
}
