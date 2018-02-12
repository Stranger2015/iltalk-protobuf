package org.ltc.iltalk.core.protobuf.pro;

import org.ltc.iltalk.protobuf.ILanguageModel;

/**
 *
 */

public class LangPair extends DbEntity{
    private final ILanguageModel lang1;
    private final ILanguageModel lang2;

    public LangPair(ILanguageModel lang1, ILanguageModel lang2){
        this.lang1 = lang1;
        this.lang2 = lang2;
    }

    public ILanguageModel getLang1() {
        return lang1;
    }

    public ILanguageModel getLang2() {
        return lang2;
    }
}

