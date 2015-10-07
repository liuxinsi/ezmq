package org.lxs.ezmq.filter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author akalxs@gmail.com
 */
public class FilterChainTest {

    @Test
    public void testAddMessageFilters() {
        FilterChain fc = new FilterChain();
        ObjectStreamCodecFilter<String> f1 = new ObjectStreamCodecFilter<>();
        ObjectStreamCodecFilter f2 = new ObjectStreamCodecFilter();
        fc.addMessageFilters(f1, f2);

        ObjectStreamCodecFilter f3 = new ObjectStreamCodecFilter();
        fc.addMessageFilters(f3);

        Assert.assertTrue(f1 == fc.getFilters()[0]);
        Assert.assertTrue(f2 == fc.getFilters()[1]);
        Assert.assertTrue(f3 == fc.getFilters()[2]);
    }
}
