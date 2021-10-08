# Role-based access control with Quarkus and Microsoft Azure Active Directory

In this article we'll demonstrate how to connect your Quarkus application to Microsoft Azure Active Directory
to authenticate and authorize users. We'll show how you can give users access to certain parts of your 
application by mapping Active Directory groups to application roles.

## Getting started

Create a new quarkus application using the code generation tool at 
[code.quarkus.io](https://code.quarkus.io/?g=no.kantega&a=quarkus-ad&e=resteasy&e=resteasy-qute&e=oidc). Be sure
to select `OpenID Connect` and `RESTEasy Qute`. Click "Generate your application", and download the
generated project as a zip file. 

Decompress and open the project in your favorite IDE. You can then start the application 
by running `./mvnw quarkus:dev`

```
$ ./mvnv quarkus:dev

__  ____  __  _____   ___  __ ____  ______
--/ __ \/ / / / _ | / _ \/ //_/ / / / __/
-/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/
2021-10-08 14:23:46,994 INFO  [io.quarkus] (Quarkus Main Thread) quarkus-ad 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.3.0.Final) started in 124.864s. Listening on: http://localhost:8080
2021-10-08 14:23:46,996 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2021-10-08 14:23:47,003 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [cdi, oidc, qute, resteasy, resteasy-qute, security, smallrye-context-propagation, vertx]
```

Quarkus will generate a class called `SomePage` which has a JAX-RS endpoint mapped to `/some-page`

```java
@Path("/some-page")
public class SomePage {
    private final Template page;

    public SomePage(Template page) {
        this.page = requireNonNull(page, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        return page.data("name", name);
    }
}
```

Open http://localhost:8080/some-page and you should see the following page: 

![Hello Qute](hello-qute.png)

