package com.diorsunion.hedge.dal.repository.test;

import com.diorsunion.dbtest.spring.DBTestClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author harley-dog on 2015/6/4.
 */

@RunWith(DBTestClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:springbeans-ds-embedded-test.xml",
        "classpath:springbeans-mybatis.xml"
})
public class RepositoryBaseTest {
    @Test
    public void test() {

    }
}
