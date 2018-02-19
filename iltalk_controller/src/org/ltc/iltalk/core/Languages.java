package org.ltc.iltalk.core;

import org.ltc.iltalk.protobuf.ILanguageModel;

import javax.lang.model.element.Name;

public enum Languages implements ILanguageModel {

    LOGTALK,
    PROLOG,
    JAVA;

    /**
     * new language
     */
    @Override
    public void loadNewLang() {

    }

    /**
     * @return
     */
    @Override
    public Name getName() {
        return null;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Name setName(Name name) {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
