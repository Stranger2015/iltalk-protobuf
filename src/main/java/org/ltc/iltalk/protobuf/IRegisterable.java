package org.ltc.iltalk.protobuf;

import org.ltc.iltalk.core.INameable;
import org.ltc.iltalk.core.Identifiable;

/**
 *
 */
public interface IRegisterable extends Identifiable<Integer>, INameable {
    /**
     * @param registry
     * @param registerable
     * @return
     */
    default boolean isRegistered(IRegistry registry, IRegisterable registerable) {
        return registry.isRegistered(this);
    }

    default Integer register(IRegistry registry) {
        return registry.register(this);
    }
}