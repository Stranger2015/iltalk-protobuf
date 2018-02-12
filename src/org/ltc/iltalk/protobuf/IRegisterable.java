package org.ltc.iltalk.protobuf;

import org.ltc.iltalk.core.INameable;
import org.ltc.iltalk.core.protobuf.pro.Identifiable;

/**
 *
 */
public interface IRegisterable extends Identifiable<Integer>, INameable {
     default boolean isRegistered(IRegistry registry) {
        return registry.isRegistered(this);
    }
    default Integer register(IRegistry registry) {
        return registry.register(this);
    }


}