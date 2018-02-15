package org.ltc.iltalk.core;

import org.ltc.iltalk.protobuf.ILanguageModel;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 */
public class ILTalkLangSide extends ILTalkProtocolHandler {

    protected static Logger log = getLogger(ILTalkLangSide.class);

    private final ILanguageModel lang;

    int nestingDepth;

    /**
     * @param lang
     */
    public ILTalkLangSide(ILanguageModel lang) {
        super();
        this.lang = lang;
    }


}