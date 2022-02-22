package org.ltc.iltalk.lang.model;


import javax.lang.model.element.Name;

public class LanguageModel /*extends SubprocessEngine*/ implements ILanguageModel {

    protected int id;
    public String name;

    public LanguageModel(String name) {

    }


    @Override
    public boolean isRegistered(org.ltc.iltalk.core.IRegistry registry) {
        return registry.isRegistered(this);
    }

    @Override
    public Integer register(org.ltc.iltalk.core.IRegistry registry) {
        return registry.register(this);
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public String setName(String name) {
        return this.name = name;
    }


    public Integer setId(int id) {
        return this.id = id;
    }

    /**
     * @return
     */
    @Override
    public Integer getId() {
               return id;
    }

    @Override
    public Integer register(org.ltc.iltalk.protobuf.IRegisterable registerable) {
        return null;
    }
}
