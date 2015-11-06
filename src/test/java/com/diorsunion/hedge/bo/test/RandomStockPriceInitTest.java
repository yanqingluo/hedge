package com.diorsunion.hedge.bo.test;

import com.diorsunion.hedge.bo.stockdatainit.RandomStockPriceInit;
import org.junit.Test;

/**
 * @author harley-dog
 * @since 2015-10-26
 */
public class RandomStockPriceInitTest extends BOBaseTest {

    @Test
    public void testgetRandomRate() {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            double d = RandomStockPriceInit.getRandomRate();
            if (d > 0)
                num++;
        }
        assert (num > 0);

    }

    @Test
    public void testgetRandomUD() {
        int num = 0;
        for (int i = 0; i < 10; i++) {
            boolean d = RandomStockPriceInit.getRandomUD();
            if (d)
                num++;
        }
        assert (num > 0);

    }
}
