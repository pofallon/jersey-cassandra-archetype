package ${groupId};

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("")
public class Application extends ResourceConfig {

    private final Cluster cluster;
    private final Session session;

    public Application() {

        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect();

        register(new AbstractBinder() {
            protected void configure() {
                bind(cluster).to(Cluster.class);
                bind(session).to(Session.class);
            }
        });

        register(new JacksonJsonProvider().
                configure(SerializationFeature.INDENT_OUTPUT, true));

        packages("${groupId}");

    }

    @PreDestroy
    private void shutdown() {
        session.close();
        cluster.close();
    }

}