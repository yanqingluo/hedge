package com.diorsunion.hedge.bo.test;

import com.diorsunion.dbtest.spring.DBTestClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author harley-dog on 2015/4/9.
 */

@RunWith(DBTestClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:springbeans-ds-embedded-test.xml",
        "classpath:springbeans-mybatis.xml",
        "classpath:springbeans-bo.xml"
})
public abstract class BOBaseTest {
}
