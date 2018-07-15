package ifpb.pos.api;

import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeVenda;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
 * @since 12/07/2018, 10:38:31
 */
@Path("vendas") // .../vendas
@Stateless
public class ResourceVendas {

    @Inject
    private ServiceDeVenda service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response todas() {

        List<Venda> vendas = service.todasAsVendas();

        GenericEntity<List<Venda>> entity = new GenericEntity<List<Venda>>(vendas) {
        };
        return Response.ok() //200
                .entity(entity)
                .build();

    }

    // ../vendas/1323
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response vendaPorId(
            @PathParam("id") String id) {

        Venda venda = service.localizarPorId(id);
        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response novaVenda(JsonObject json) {

        Venda venda = service.novaVendaParaCliente(json.getString("cliente"));

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();
    }

    // ../api/vendas/{id}/cliente
    @PUT
    @Path("{id}/cliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarNomeDoCliente(
            @PathParam("id") String idDaVenda, JsonObject json) {

        Venda venda = service.atualizarClienteDaVenda(idDaVenda, json.getString("nome"));

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();
    }

    // ../api/vendas/{id}/produto
    @PUT
    @Path("{id}/produto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionarProduto(
            @PathParam("id") String idDaVenda, JsonObject json) {

        Venda venda = service.novoProduto(idDaVenda, json.getString("produto"));

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();
    }

    // ../api/vendas/{id}/produto
    @PUT
    @Path("{id}/produtos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionarDiversosProdutos(
            @PathParam("id") String idDaVenda, JsonObject json) {

        JsonArray jsonArray = json.getJsonArray("produtos");
        List<String> collect = jsonArray
                .stream()
                .map((JsonValue t) -> (JsonString) t)
                .map(c -> c.getChars().toString()) // "oi" -> oi
                .collect(Collectors.toList());

        Venda venda = service.novosProdutos(idDaVenda, collect);

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda)
                .build();
    }

}
