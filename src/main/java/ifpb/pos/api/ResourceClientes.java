package ifpb.pos.api;

import ifpb.pos.domain.Cliente;
import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeCliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 10:39:43
 */
@Stateless
@Path("clientes")
public class ResourceClientes {

    @Inject
    private ServiceDeCliente service;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response todos() {

        List<Cliente> clientes = service.todosOsClientes();

        GenericEntity<List<Cliente>> entity = new GenericEntity<List<Cliente>>(clientes) {
        };
        return Response.ok() //200
                .entity(entity)
                .build();

    }

    // ../vendas/1323
    @GET
    @Path("{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clientePorCPF(
            @PathParam("cpf") String cpf) {

        Cliente cliente = service.localizarPorCPF(cpf);
        if (cliente == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(cliente)
                .build();

    }
}
