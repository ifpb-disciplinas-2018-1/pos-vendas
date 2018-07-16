package ifpb.pos.api;

import ifpb.pos.domain.Produto;
import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeProduto;
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
@Path("produtos")
public class ResourceProdutos {

    @Inject
    private ServiceDeProduto service;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response todos() {

        List<Produto> produtos = service.todosOsProdutos();

        GenericEntity<List<Produto>> entity = new GenericEntity<List<Produto>>(produtos) {
        };
        return Response.ok() //200
                .entity(entity)
                .build();

    }

    // ../vendas/1323
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response produtosPorId(
            @PathParam("id") int id) {

        Produto produto = service.localizarPorId(id);
        if (produto == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(produto)
                .build();

    }
}
