package org.lxs.ezmq.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author akalxs@gmail.com
 */
public class ProtobufCodecFilterTest {
    private CodecFilter cf;
    private byte[] data;
    private AddressBookProtos.AddressBook tb;

    @Before
    public void init() {
        cf = new ProtobufCodecFilter(AddressBookProtos.AddressBook.getDefaultInstance());
        tb = AddressBookProtos.AddressBook.newBuilder().addPeople(0,
                AddressBookProtos.Person.newBuilder().setName("abc").build())
                .build();
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
        AddressBookProtos.AddressBook ab = (AddressBookProtos.AddressBook) cf.decode(data);
        Assert.assertTrue(tb.getPeople(0).getName().equals(ab.getPeople(0).getName()));
    }
}
