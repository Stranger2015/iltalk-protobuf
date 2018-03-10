package org.ltc.iltalk.protobuf;

import org.ltc.iltalk.com.declarativa.interprolog.SubprocessEngine;
import org.ltc.iltalk.core.ILanguageVM;

import javax.lang.model.element.Name;

public class AbstractLanguageModel extends SubprocessEngine implements ILanguageModel {

    protected Name name;
    protected ILanguageVM vm;

    /**
     *
     */
    @Override
    public void loadNewLanguage() {

    }

    /**
     * @param lang
     * @return
     */
    @Override
    public ILanguageVM getVirtualMachine(ILanguageModel lang) {
        return null;
    }

    @Override
    public boolean isRegistered(IRegistry registry) {
        return registry.isRegistered(this);
    }

    @Override
    public Integer register(IRegistry registry) {
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
    public Integer register(IRegisterable registerable) {
        return null;
    }
}
