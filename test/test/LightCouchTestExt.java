package test;

import persistence.CouchGameDB;
import persistence.LightCouchGameDB;

/**
 * Tests are implemented in IGameDBTestBase, generically
 */
public class LightCouchTestExt extends IGameDBTestBase<LightCouchGameDB>{
    @Override
    protected LightCouchGameDB createSut(){
        return new LightCouchGameDB();
    }
}
