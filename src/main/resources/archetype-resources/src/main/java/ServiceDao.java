package ${groupId};

import import com.datastax.driver.core.Cluster;

public class ServiceDao extends AbstractServiceDao {

    public ServiceDao(Cluster cluster) {
        // super(cluster, "keyspace name");
        // addPreparedStatement("STATEMENT_ID","CQL Query");
    }

    // DAO Methods here

}