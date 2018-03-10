package org.ltc.iltalk.core;


/**
 *
 *
 */
public class ILTalkLangSide extends ILTalkProtocolHandler implements ILanguageVM {

    private final ILanguageModel lang;
    private LanguagePair pair;

    /**
     * @param  fn
     */
    public ILTalkLangSide(String fn) {
        super(fn);

        String langName = talkProps.getProperty("lang.name"); //fixme id
        lang = Languages.valueOf(langName);
    }

    /**
     * @return
     */
    public ILanguageModel getLang() {
        return lang;
    }

//    public LanguagePair createPair(IRegistry<LanguagePair>  registry, ILanguageModel lang2){
//        pair= LanguagePair.create(registry, getLang(), lang2);
//
//    }

}