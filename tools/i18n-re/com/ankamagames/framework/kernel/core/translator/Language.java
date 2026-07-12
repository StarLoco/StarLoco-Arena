/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.kernel.core.translator;

import java.util.Locale;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum Language {
    FR(new Locale("fr")),
    EN(new Locale("en")),
    DE(new Locale("de")),
    ES(new Locale("es"));

    private Locale m_locale;

    private Language(Locale locale) {
        this.m_locale = locale;
    }

    public Locale getLocale() {
        return this.m_locale;
    }

    public static Language getLanguage(String languageCode) {
        if (languageCode.equals("fr")) {
            return FR;
        }
        if (languageCode.equals("en")) {
            return EN;
        }
        if (languageCode.equals("de")) {
            return DE;
        }
        if (languageCode.equals("es")) {
            return ES;
        }
        return null;
    }
}

