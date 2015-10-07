package org.lxs.ezmq.filter;

import java.util.Arrays;

/**
 * @author akalxs@gmail.com
 */
public class FilterChain {
    private MessageFilter[] messageFilters;
    private int position;

    public void addMessageFilters(MessageFilter... filters) {
        if (messageFilters == null) {
            messageFilters = new MessageFilter[filters.length];
        } else {
            messageFilters = Arrays.copyOf(messageFilters, position + filters.length);
        }

        System.arraycopy(filters, 0, messageFilters, position, filters.length);
        position += messageFilters.length;
    }

    public MessageFilter[] getFilters() {
        return messageFilters;
    }

    public MessageFilter getFilter(Class<?> clazz) {
        for (MessageFilter mf : messageFilters) {
            if (clazz.isInstance(mf)) {
                return mf;
            }
        }
        throw new NullPointerException("no filter");
    }
}
