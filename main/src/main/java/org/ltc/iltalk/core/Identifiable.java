package org.ltc.iltalk.core;

/**
 * @param <IDT>
 */
public interface Identifiable<IDT extends Integer> {
    /**
     * @return
     */
    IDT getId();
}
