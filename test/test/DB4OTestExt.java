package test;

import persistence.DB4OGameDB;

/**
 * Tests are implemented in IGameDBTestBase, generically
 */
public class DB4OTestExt extends IGameDBTestBase<DB4OGameDB>{
    @Override
    protected DB4OGameDB createSut(){
        return new DB4OGameDB();
    }
}
