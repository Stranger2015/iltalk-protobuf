package org.ltc.iltalk.core;

public interface IRegistry<T extends IRegisterable> {

    boolean isRegistered(Class<? extends IRegisterable> registerable);

    Integer register(T registerable);
}
