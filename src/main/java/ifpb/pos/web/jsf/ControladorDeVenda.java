package ifpb.pos.web.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 16/07/2018, 11:17:49
 */
@Named
@RequestScoped
public class ControladorDeVenda {

    private String id;

    private ClienteDeVenda service = new ClienteDeVenda();

    private VendaValue venda;

    public String carregar() {
        this.venda = service.lerVendaComId(id);
        this.id = "";
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VendaValue getVenda() {
        return venda;
    }

    public void setVenda(VendaValue venda) {
        this.venda = venda;
    }
    
}
