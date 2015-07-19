package ${groupId};

import com.datastax.driver.core.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractServiceDao {

    Session session;
    private ConcurrentHashMap<String,PreparedStatement> statements = new ConcurrentHashMap<String,PreparedStatement>();

    void addPreparedStatement(String key, String statement) {
        statements.put(key,session.prepare(statement));
    }

    PreparedStatement getPreparedStatement(String key) {
        return(statements.get(key));
    }

    AbstractServiceDao(Cluster cluster, String keyspace) {
        this.session = cluster.connect(keyspace);
    }

    public void close() {
        session.close();
    }

}