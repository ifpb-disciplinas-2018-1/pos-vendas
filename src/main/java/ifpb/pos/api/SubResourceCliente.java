package ifpb.pos.api;

import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeVenda;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 10:22:33
 */
@Stateless
//@Path("")
public class SubResourceCliente {

    @Inject
    private ServiceDeVenda service;

    @GET
//    @Path("{id}/cliente")
//    @Produces(MediaType.APPLICATION_JSON)
    public Response exibirCliente(
            @PathParam("id") String idDaVenda) {

        Venda venda = service.localizarPorId(idDaVenda);

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda.getCliente())
                .build();
    }

    // ../api/vendas/{id}/cliente
    @PUT
//    @Path("{id}/cliente")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarNomeDoCliente(
            @PathParam("id") String idDaVenda, JsonObject json) {

        Venda venda = service.atualizarClienteDaVenda(
                idDaVenda,
                json.getString("nome")
        );

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();
    }
}
