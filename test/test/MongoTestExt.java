package test;

import persistence.CouchGameDB;
import persistence.MongoGameDB;

/**
 * Tests are implemented in IGameDBTestBase, generically
 */
public class MongoTestExt extends IGameDBTestBase<MongoGameDB>{
    @Override
    protected MongoGameDB createSut(){
        return new MongoGameDB();
    }
}
