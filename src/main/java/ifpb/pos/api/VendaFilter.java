package ifpb.pos.api;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/07/2018, 10:09:08
 */
@Provider
public class VendaFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String headerString = requestContext.getHeaderString("Authorization");

        if (headerString == null || headerString.trim().isEmpty()) {
            JsonObject mensagem = Json.createObjectBuilder()
                    .add("msg", "heard não informado")
                    .build();

            Response response = Response
                    .status(Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(mensagem)
                    .build();

            requestContext.abortWith(response);
        } else {
//            System.out.println("HEADER: " + headerString);
            //Basic YWRtaW46MTIzNDU2
            String heard = headerString.replaceAll("Basic ", "");
            String usuarioSenha = new String(Base64.getDecoder().decode(heard));
//            System.out.println("usuarioSenha = " + usuarioSenha);
//            String[] split = usuarioSenha.split(":");
//            String name = split[0];
//            String pass = split[1];
            StringTokenizer tokenizer = new StringTokenizer(usuarioSenha, ":");
            String usuario = tokenizer.nextToken();
            String senha = tokenizer.nextToken();

            if ("admin".equals(usuario) && "123456".endsWith(senha)) {
                System.out.println("valido");
            } else {
                JsonObject mensagem = Json.createObjectBuilder()
                        .add("msg", "usuario/senha não confere")
                        .build();

                Response response = Response
                        .status(Status.UNAUTHORIZED)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(mensagem)
                        .build();

                requestContext.abortWith(response);
            }

        }
    }

}
