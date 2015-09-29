package org.lxs.ezmq.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
        try {
            data = cf.encode(tb);
            Assert.assertNotNull(data);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testDecode() {
        if (data == null) {
            testEncode();
        }
        try {
            Object o = cf.decode(data);
            Assert.assertTrue(((TestBean) o).b.equals(tb.b));
        } catch (IOException | ClassNotFoundException e) {
            Assert.fail(e.getMessage());
        }
    }
}
