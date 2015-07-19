package ${groupId};

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("/endpoint")
public class NewService {

    @Context ServiceDao dao;

    // Add Jersey endpoint methods here

}

