package test;

import persistence.HibernateGameDB;

/**
 * Tests are implemented in IGameDBTestBase, generically
 */
public class HibernateTestExt extends IGameDBTestBase<HibernateGameDB>{
    @Override
    protected HibernateGameDB createSut(){
        return new HibernateGameDB();
    }
}
