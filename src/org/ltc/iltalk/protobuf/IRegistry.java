package org.ltc.iltalk.protobuf;

/**
 *
 */
public interface IRegistry {

    /**
     * @return
     */
    Integer register ( IRegisterable registerable );

    /**
     * @return
     */
    boolean isRegistered ( IRegisterable registerable );


}
