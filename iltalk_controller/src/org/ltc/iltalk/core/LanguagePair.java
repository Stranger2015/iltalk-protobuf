

package org.ltc.iltalk.core;

import org.ltc.iltalk.protobuf.ILanguageModel;
import org.ltc.iltalk.protobuf.IRegisterable;
import org.ltc.iltalk.protobuf.IRegistry;

/**
 *
 */
public class LanguagePair extends DbEntity implements IRegistry {

    protected ILanguageModel lang1;
    protected ILanguageModel lang2;

    Runnable r = () -> {
        throw new Error();
    };

    Runnable r2 = () -> {
    };

    /**
     * @param lang1
     * @param lang2
     */
    public LanguagePair(ILanguageModel lang1, ILanguageModel lang2) {
        if (lang1.equals(lang2)) {
            r.run();
        }
        this.lang1 = lang1;
        this.lang2 = lang2;
        if (this.isRegistered(this, this)))
    }

    /**
     * REgis
     *
     * @param registry
     * @param lang1
     * @param lang2
     * @return
     */
    public static LanguagePair create(IRegistry registry, ILanguageModel lang1, ILanguageModel lang2) {

        return new LanguagePair(lang1, lang2);
    }

    /**
     * @param registerable
     * @return
     */
    @Override
    public boolean isRegistered(IRegistry registry, IRegisterable registerable) {
        return false;
    }

    @Override
    public Integer register(IRegistry registry, IRegisterable registerable) {
        return null;
    }

}
