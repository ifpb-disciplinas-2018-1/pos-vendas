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
public class ServiceDeCliente {

    @PersistenceContext
    private EntityManager em;

    public Cliente localizarPorCPF(String cpf) {
        return em.find(Cliente.class, cpf);
    }

     

    public List<Cliente> todosOsClientes() {
        return em.createQuery("FROM Cliente v", Cliente.class)
                .getResultList();
    }
 
}
