/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;

import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;

public class UIFighterSpellMessage
extends UIFighterMessage {
    private Spell m_spell;

    public Spell getSpell() {
        return this.m_spell;
    }

    public void setSpell(Spell spell) {
        this.m_spell = spell;
    }
}

