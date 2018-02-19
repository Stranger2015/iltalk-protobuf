package org.ltc.iltalk.protobuf;

import javax.lang.model.element.Name;

public class AbstractLanguageModel extends Subprocess implements ILanguageModel {

    private Name name;

    @Override
    public void loadNewLang() {

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

    /**
     * @param name
     * @return
     */
    @Override
    public Name setName(Name name) {
        return this.name = name;
    }


    @Override
    public Integer etId() {
        return id;
    }
}
