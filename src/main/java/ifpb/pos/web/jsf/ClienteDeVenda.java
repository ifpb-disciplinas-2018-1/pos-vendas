package ifpb.pos.web.jsf;

import ifpb.pos.api.Link;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 11:24:10
 */
public class ClienteDeVenda {

    private String uri = "http://localhost:8080/pos-vendas/api/vendas/";
    private Client client = ClientBuilder.newClient();
    private WebTarget root = client.target(uri);

    public VendaValue lerVendaComId(String id) {
        WebTarget vendaPorId = root.path("{id}").resolveTemplate("id", id);
        VendaValue get = vendaPorId.request().get(VendaValue.class);
        return get;
    }
}


