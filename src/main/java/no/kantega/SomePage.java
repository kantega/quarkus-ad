package no.kantega;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;

@Path("/some-page")
@Authenticated
public class SomePage {
    private final Template page;
    private final Template forbidden;

    @Inject
    SecurityIdentity identity;

    public SomePage(Template page, Template forbidden) {
        this.page = requireNonNull(page, "page is required");
        this.forbidden = requireNonNull(forbidden, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        if (identity.hasRole("admin")) {
            return page
                    .data("name", name)
                    .data("identity", identity);
        } else {
            return forbidden
                    .data("identity", identity);
        }
    }
}
