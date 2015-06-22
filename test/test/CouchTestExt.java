package test;

import persistence.CouchGameDB;

/**
 * Tests are implemented in IGameDBTestBase, generically
 */
public class CouchTestExt extends IGameDBTestBase<CouchGameDB>{
    @Override
    protected CouchGameDB createSut(){
        return new CouchGameDB();
    }
}
