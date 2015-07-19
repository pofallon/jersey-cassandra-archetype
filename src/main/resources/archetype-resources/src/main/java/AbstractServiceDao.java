package ${groupId};

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.MappingManager;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractServiceDao {

    Session session;
    MappingManager mappingManager;
    private ConcurrentHashMap<String,PreparedStatement> statements = new ConcurrentHashMap<String,PreparedStatement>();

    AbstractServiceDao(Cluster cluster, String keyspace) {
        session = cluster.connect(keyspace);
        this.mappingManager = new MappingManager(session);
    }

    void addPreparedStatement(String key, String statement) {
        statements.put(key,session.prepare(statement));
    }

    PreparedStatement getPreparedStatement(String key) {
        return(statements.get(key));
    }

    MappingManager getMappingManager() {
        return mappingManager;
    }

    public void close() {
        session.close();
    }

}