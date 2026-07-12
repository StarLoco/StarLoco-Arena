/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.ui.protocol.frame;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
import com.ankamagames.dofusarena.client.alea.highlightingCells.SpellDisplayZone;
import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.SpellCastRequestMessage;
import com.ankamagames.dofusarena.client.ui.protocol.frame.UIAbstractFightCastWorldSceneInteractionFrame;

public class UIFightSpellCastWorldSceneInteractionFrame
extends UIAbstractFightCastWorldSceneInteractionFrame {
    private static UIFightSpellCastWorldSceneInteractionFrame m_instance = new UIFightSpellCastWorldSceneInteractionFrame();
    private Spell m_selectedSpell = null;

    private UIFightSpellCastWorldSceneInteractionFrame() {
        this.m_rangeDisplayer = new SpellDisplayZone();
    }

    public static UIFightSpellCastWorldSceneInteractionFrame getInstance() {
        return m_instance;
    }

    public void setSelectedSpell(Spell selectedSpell) {
        this.m_selectedSpell = selectedSpell;
    }

    public Spell getSelectedSpell() {
        return this.m_selectedSpell;
    }

    protected EffectContainer getEffectContainer() {
        return this.m_selectedSpell;
    }

    protected void sendCastMessage(int castPositionX, int castPositionY, short castPositionZ) {
        SpellCastRequestMessage netMessage = new SpellCastRequestMessage();
        netMessage.setFighterId(this.m_fighter.getId());
        netMessage.setSpellId(this.m_selectedSpell.getId());
        netMessage.setCastPosition(castPositionX, castPositionY, castPositionZ);
        DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
    }

    protected String getCastMouseIcon() {
        if (this.m_selectedSpell != null) {
            return (String)this.m_selectedSpell.getFieldValue("iconUrl");
        }
        return null;
    }

    public long getId() {
        return 0L;
    }

    public void setId(long id) {
    }

    public void selectRange() {
        DofusArenaWorldScene worldScene;
        super.selectRange();
        if (this.m_selectedSpell != null && this.m_fighter != null && (worldScene = (DofusArenaWorldScene)DofusArenaClientInstance.getInstance().getWorldScene()) != null) {
            ((SpellDisplayZone)this.m_rangeDisplayer).selectSpellRange(this.m_selectedSpell, this.m_fighter, worldScene);
        }
    }
}

