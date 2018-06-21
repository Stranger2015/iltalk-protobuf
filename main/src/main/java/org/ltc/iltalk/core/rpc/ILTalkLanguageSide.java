package org.ltc.iltalk.core.rpc;


import org.ltc.iltalk.core.LanguagePair;
import org.ltc.iltalk.lang.model.ILanguageModel;
import org.ltc.iltalk.lang.model.LanguageModel;

import java.io.IOException;
import java.util.Properties;

/**
 *
 *
 */
public class ILTalkLanguageSide extends ILTalkProtocolHandler {
    private int  nestingDepth = 0;

    private final ILanguageModel language;
    private LanguagePair pair;

    /**
     * @param  talkProps
     */
    public ILTalkLanguageSide(State state, Properties talkProps) throws IOException, InterruptedException {
        super(state, talkProps);

        String langName = talkProps.getProperty("lang.name"); //fixme id
        language = createLanguageModel(langName);
    }

    private ILanguageModel createLanguageModel(String langName) {
        return  new LanguageModel(langName);
    }

    /**
     * @return
     */
    public ILanguageModel getLanguage() {
        return language;
    }


}


