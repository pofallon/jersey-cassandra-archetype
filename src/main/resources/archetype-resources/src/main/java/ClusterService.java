package ${groupId};

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.HashSet;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;

@Path("/cluster")
public class ClusterService {

    @Context Cluster cluster;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String,HashMap<String,Object>> getClusterInfo() {

        Metadata metadata = cluster.getMetadata();
        HashMap<String,Object> clusterInfo = new HashMap<String,Object>();

        HashSet<HashMap<String,Object>> hosts = new HashSet<HashMap<String,Object>>();
        for (Host h : metadata.getAllHosts()) {
            HashMap<String,Object> hostMap = new HashMap<String,Object>();

            hostMap.put("datacenter",h.getDatacenter());
            hostMap.put("socketAddress",h.getSocketAddress());
            hostMap.put("rack",h.getRack());
            hostMap.put("state",h.getState());
            hostMap.put("tokenCount",h.getTokens().size());
            hosts.add(hostMap);

        }
        clusterInfo.put("hosts",hosts);

        HashMap<String,HashMap<String,Object>> keyspaces = new HashMap<String,HashMap<String,Object>>();
        for (KeyspaceMetadata k : metadata.getKeyspaces()) {
            HashMap<String,Object> keyspaceMap = new HashMap<String,Object>();
            keyspaceMap.put("replication",k.getReplication());
            keyspaceMap.put("tableCount",k.getTables().size());
            keyspaceMap.put("userTypeCount",k.getUserTypes().size());
            keyspaces.put(k.getName(),keyspaceMap);

        }
        clusterInfo.put("keyspaces",keyspaces);

        HashMap<String,HashMap<String,Object>> results = new HashMap<String,HashMap<String,Object>>();
        results.put(metadata.getClusterName(),clusterInfo);

        return(results);
    }

}