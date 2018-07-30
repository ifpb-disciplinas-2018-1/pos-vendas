package ifpb.pos.api.venda;

import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeVenda;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 10:22:33
 */
public class SubResourceCliente {

    private ServiceDeVenda service;
    private String idDaVenda;

    public SubResourceCliente(ServiceDeVenda service, String idDaVenda) {
        this.service = service;
        this.idDaVenda = idDaVenda;
    }

    @GET
    public Response exibirCliente() {

        Venda venda = this.service.localizarPorId(this.idDaVenda);

        if (venda == null) {
            return Response.noContent().build();
        }

        return Response.ok() //200
                .entity(venda.getCliente())
                .build();
    }

    // ../api/vendas/{id}/cliente
    @PUT
    public Response atualizarNomeDoCliente(
            @PathParam("id") String idDaVenda, JsonObject json) {

        Venda venda = this.service.atualizarClienteDaVenda(
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
