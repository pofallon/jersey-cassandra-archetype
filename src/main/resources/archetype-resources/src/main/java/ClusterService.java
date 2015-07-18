package ${groupId};

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;

@Path("/cluster")
public class ClusterService {

    @Context Cluster cluster;

    @GET
    @Path("/name")
    @Produces(MediaType.APPLICATION_JSON)
    public String getClusterName() {

        Metadata metadata = cluster.getMetadata();
        return(metadata.getClusterName());

    }

}