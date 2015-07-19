package ${groupId};

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import com.datastax.driver.core.Cluster;

import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("")
public class Application extends ResourceConfig {

    private final Cluster cluster;
    private final ServiceDao dao;

    public Application() {

        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        dao = new ServiceDao(cluster);

        register(new AbstractBinder() {
            protected void configure() {
                bind(cluster).to(Cluster.class);
                bind(dao).to(ServiceDao.class);
            }
        });

        register(new JacksonJsonProvider().
                configure(SerializationFeature.INDENT_OUTPUT, true).
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false));

        packages("${groupId}");

    }

    @PreDestroy
    private void shutdown() {
        dao.close();
        cluster.close();
    }

}