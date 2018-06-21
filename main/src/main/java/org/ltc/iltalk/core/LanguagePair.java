

package org.ltc.iltalk.core;


import org.ltc.iltalk.lang.model.ILanguageModel;

/**
 *
 */
public class LanguagePair extends DbEntity implements IRegisterable {

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

    }

    /**
     * REgis
     *
     * @param registry
     * @param lang1
     * @param lang2
     * @return
     */
    public static LanguagePair create( IRegistry registry, ILanguageModel lang1, ILanguageModel lang2 ) {

        if(registry.isRegistered( LanguagePair.class )){

        }
        LanguagePair pair = new LanguagePair(lang1, lang2);

        registry.register(pair);

        return pair;
    }

    /**
     * @param registerable
     * @return
     */

    @Override
    public boolean isRegistered(Class<? extends IRegisterable> registerable ) {
        return false;//todo
    }

    @Override
    public Integer register( DbEntity registerable ) {
        return null;//todo
    }
}
