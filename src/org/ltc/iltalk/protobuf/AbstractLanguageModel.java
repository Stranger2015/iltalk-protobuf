package org.ltc.iltalk.protobuf;

import javax.lang.model.element.Name;

public class AbstractLanguageModel extends Subprocess implements ILanguageModel{

    private Name name;

    @Override
    public void load () {

    }

    @Override
    public boolean isRegistered ( IRegistry registry ) {
        return registry.isRegistered(this);
    }

    @Override
    public Integer register (IRegistry registry ) {
        return registry.register(this);
    }

    /**
     * @return
     */
    @Override
    public Name getName() {
        return name;
    }


    @Override
    public Integer getId() {
        return null;
    }
}
