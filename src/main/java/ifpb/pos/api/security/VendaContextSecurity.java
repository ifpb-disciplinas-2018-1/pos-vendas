package ifpb.pos.api.security;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/07/2018, 19:08:10
 */
@Provider
public class VendaContextSecurity implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        boolean contains = requestContext
                .getUriInfo()
                .getPath()
                .contains("vendas");
        if (!contains) {
            return;
        }
        String headerString = requestContext
                .getHeaderString("Authorization");

        boolean vazio = (headerString == null || headerString.isEmpty());
        
        if (vazio) {
            JsonObject add = Json
                    .createObjectBuilder()
                    .add("msg", "header not valid")
                    .build();
            Response response = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(add)
                    .build();
            requestContext.abortWith(response);
        } else {

            String withoutBasic = headerString.replaceAll("Basic ", "");
            byte[] decode = Base64.getDecoder().decode(withoutBasic);
            String usuarioComSenha = new String(decode);
            StringTokenizer string = new StringTokenizer(usuarioComSenha, ":");
            String usuario = string.nextToken();
            String senha = string.nextToken();
            boolean usarioExiste = "admin".equals(usuario) && "mesmacoisa".equals(senha);
            if (!usarioExiste) {
                JsonObject add = Json.createObjectBuilder()
                        .add("msg", "user not find")
                        .build();
                Response response = Response
                        .status(Response.Status.UNAUTHORIZED)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(add)
                        .build();
                requestContext.abortWith(response);
            }
        }
    }

}
