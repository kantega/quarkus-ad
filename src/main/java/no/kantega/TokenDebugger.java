package no.kantega;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jose4j.base64url.Base64;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenDebugger implements SecurityIdentityAugmentor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity securityIdentity, AuthenticationRequestContext authenticationRequestContext) {
        if (securityIdentity.getPrincipal() instanceof JsonWebToken) {
            JsonWebToken principal = (JsonWebToken) securityIdentity.getPrincipal();

            System.out.println("Received token:");
            for (String part : principal.getRawToken().split("\\.")) {
                String decoded = new String(Base64.decode(part));
                System.out.println(toPrettyJson(decoded));
            }
        }
        return Uni.createFrom().item(securityIdentity);
    }

    private String toPrettyJson(String json) {
        try {
            Object value = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
