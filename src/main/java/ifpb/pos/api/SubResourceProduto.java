package ifpb.pos.api;

import ifpb.pos.domain.Produto;
import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeVenda;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 10:22:33
 */
@Stateless
@Path("")
public class SubResourceProduto {

    @Inject
    private ServiceDeVenda service;

    @GET
    public Response exibirProduto(
            @PathParam("id") String idDaVenda) {

        Venda venda = service.localizarPorId(idDaVenda);

        if (venda == null) {
            return Response.noContent().build();
        }

        GenericEntity<List<Produto>> entity
                = new GenericEntity<List<Produto>>(
                        venda.getProdutos()
                ) {
        };

        return Response.ok() //200
                .entity(entity)
                .build();
    }

    // ../api/vendas/{id}/produto
    @PUT
    public Response adicionarProduto(
            @PathParam("id") String idDaVenda, JsonObject json) {

        Venda venda = service.novoProduto(
                idDaVenda,
                json.getString("produto"),
                json.getJsonNumber("preco").doubleValue()
        );

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();
    }
}
