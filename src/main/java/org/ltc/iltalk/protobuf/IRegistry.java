package org.ltc.iltalk.protobuf;

/=

public interface IRegistry {

    /**
     * @return
     */
    Integer register(IRegistry registry, IRegisterable registerable);


    /**
     * @return
     */
    boolean isRegistered(IRegistry registry, IRegisterable registerable);
}