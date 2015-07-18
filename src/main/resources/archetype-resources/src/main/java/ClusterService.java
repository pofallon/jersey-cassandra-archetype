package ${groupId};

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.HashSet;

import com.datastax.driver.core.*;

@Path("/cluster")
public class ClusterService {

    @Context Cluster cluster;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String,HashMap<String,Object>> getClusterInfo() {

        HashMap<String,Object> clusterInfo = new HashMap<String,Object>();

        Metadata metadata = cluster.getMetadata();

        HashSet<HashMap<String,Object>> hosts = new HashSet<HashMap<String,Object>>();
        for (Host h : metadata.getAllHosts()) {
            HashMap<String,Object> hostMap = new HashMap<String,Object>();

            hostMap.put("datacenter",h.getDatacenter());
            hostMap.put("address",h.getAddress());
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

        Metrics metrics = cluster.getMetrics();
        HashMap<String,Object> clusterMetrics = new HashMap<String,Object>();
        clusterMetrics.put("knownHosts",metrics.getKnownHosts().getValue());
        clusterMetrics.put("openConnections",metrics.getOpenConnections().getValue());
        clusterInfo.put("metrics",clusterMetrics);

        HashMap<String,HashMap<String,Object>> results = new HashMap<String,HashMap<String,Object>>();
        results.put(metadata.getClusterName(),clusterInfo);

        return(results);
    }

}