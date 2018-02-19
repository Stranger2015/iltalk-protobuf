package org.ltc.iltalk.core;

import org.ltc.iltalk.protobuf.ILanguageModel;

import java.io.IOException;

/**lHandler {

 *
 */
public class ILTalkLangSide extends ILTalkProtocolHandler {

    private final ILanguageModel lang;
    private LanguagePair pair;
    /**
     * @param  fn
     */
    public ILTalkLangSide(String fn) throws IOException, InterruptedException {
        super(fn);

        String langName = talkProps.getProperty("lang.name"); //fixme id
        lang = Languages.valueOf(langName);
    }

    public ILanguageModel getLang() {
        return lang;
    }
}