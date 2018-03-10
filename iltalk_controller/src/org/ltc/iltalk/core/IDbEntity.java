
package org.ltc.iltalk.core;

import org.ltc.iltalk.protobuf.IRegisterable;
import org.ltc.iltalk.protobuf.IRegistry;

/**
 *
 */
public interface IDbEntity extends Identifiable<Integer>, IRegisterable {

    Integer register(IRegistry registry, IRegisterable registerable);

    boolean isRegistered(IRegistry registry, IRegisterable registerable);

}