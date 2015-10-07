package org.lxs.ezmq.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author akalxs@gmail.com
 */
public class ObjecStreamCodecFilterTest {
    private CodecFilter cf;
    private byte[] data;
    private TestBean tb;

    @Before
    public void init() {
        cf = new ObjectStreamCodecFilter();
        tb = new TestBean();
        tb.n = "123";
        tb.b = "b456";
    }

    @Test
    public void testEncode() {
        data = cf.encode(tb);
        Assert.assertNotNull(data);
    }

    @Test
    public void testDecode() {
        if (data == null) {
            testEncode();
        }
        Object o = cf.decode(data);
        Assert.assertTrue(((TestBean) o).b.equals(tb.b));
    }
}
