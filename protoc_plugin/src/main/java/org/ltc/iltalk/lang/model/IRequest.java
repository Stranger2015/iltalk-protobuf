package org.ltc.iltalk.lang.model;

import org.ltc.iltalk.lang.model.type.TypeModel;

public interface IRequest {
    void  send(TypeModel value);
}
