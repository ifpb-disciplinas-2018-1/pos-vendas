package ifpb.pos.api;

import ifpb.pos.api.venda.SubResourceProduto;
import ifpb.pos.api.venda.SubResourceCliente;
import ifpb.pos.domain.Venda;
import ifpb.pos.api.venda.VendaSimples;
import ifpb.pos.service.ServiceDeVenda;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 12/07/2018, 10:38:31
 */
@Path("vendas") // .../vendas
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class ResourceVendas {

    @Inject
    private ServiceDeVenda service;

    @Context
    private ResourceContext context;

    @GET
    public Response todas() {
        List<Venda> vendas = service.todasAsVendas();
        GenericEntity<List<Venda>> entity = new GenericEntity<List<Venda>>(vendas) {
        };
        return Response.ok() //200
                .entity(entity)
                .build();

    }

    @POST
    public Response novaVenda(JsonObject json) {
        Venda venda = service.novaVendaParaCliente(
                json.getString("nome"),
                json.getString("cpf"),
                json.getString("email")
        );
        if (venda == null) {
            return Response.noContent().build();
        }
        return Response.ok() //200
                .entity(venda)
                .build();
    }

    // ../vendas/1323
    @GET
    @Path("{id}")
    public Response vendaPorId(
            @PathParam("id") String id, @Context UriInfo uriInfo) {
        Venda venda = service.localizarPorId(id);
        if (venda == null) {
            return Response.noContent().build();
        }
        VendaSimples simples = new VendaSimples(venda, uriInfo);
        return Response.ok() //200
                .entity(simples)
                .build();
    }

    @GET
    @Path("{id}/finalizar")
    public Response finalizarVendaPorId(
            @PathParam("id") String id, @Context UriInfo uriInfo) {
        Venda venda = service.finalizarVendaComId(id);
        if (venda == null) {
            return Response.noContent().build();
        }
        boolean finalizada = venda.finalizada();
        JsonObject resposta = Json.createObjectBuilder()
                .add("msg", finalizada)
                .build();
        return Response.ok() //200
                .entity(resposta)
                .build();
    }

    @GET
    @Path("{id}/status")
    public Response statusVendaPorId(
            @PathParam("id") String id, @Context UriInfo uriInfo) {
        Venda venda = service.localizarPorId(id);
        if (venda == null) {
            return Response.noContent().build();
        }
//        boolean finalizada = venda.finalizada();
        JsonObject resposta = Json.createObjectBuilder()
                .add("status", venda.status())
                .build();
        return Response.ok() //200
                .entity(resposta)
                .build();
    }

    @Path("{id}/cliente")
    public SubResourceCliente exibirCliente(@PathParam("id") String id) {
        return this.context.initResource(
                new SubResourceCliente(service, id)
        );
    }

    @Path("{id}/produto")
    public SubResourceProduto exibirProduto(@PathParam("id") String id) {
        return this.context.initResource(
                new SubResourceProduto(service, id)
        );
    }
}
