package ifpb.pos.api.venda;

import ifpb.pos.domain.Produto;
import ifpb.pos.domain.Venda;
import ifpb.pos.service.ServiceDeVenda;
import java.util.List;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 10:22:33
 */
public class SubResourceProduto {

    private ServiceDeVenda service;
    private String idDaVenda;

    public SubResourceProduto(ServiceDeVenda service, String idDaVenda) {
        this.service = service;
        this.idDaVenda = idDaVenda;
    }

    @GET
    public Response exibirProduto() {

        Venda venda = this.service.localizarPorId(this.idDaVenda);

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
    public Response adicionarProduto(JsonObject json) {

        Venda venda = this.service.novoProduto(
                this.idDaVenda,
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
