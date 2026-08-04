/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.ui.protocol.message.connection;

import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
import com.ankamagames.framework.kernel.core.translator.Language;

public class UIChangeLanguageRequestMessage
extends UIMessage {
    private Language m_language;

    public int getId() {
        return 16384;
    }

    public Language getLanguage() {
        return this.m_language;
    }

    public void setLanguage(Language language) {
        this.m_language = language;
    }
}

