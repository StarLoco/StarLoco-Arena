/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.core.contentInitializer;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
import com.ankamagames.dofusarena.client.core.contentInitializer.EffectContentDocumentLoader;
import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import com.ankamagames.dofusarena.client.core.game.spell.SpellManager;
import com.ankamagames.dofusarena.common.game.ai.CriteriaCompiler;
import com.ankamagames.framework.ai.criteria.Criterion;
import com.ankamagames.framework.fileFormat.document.DocumentContainer;
import java.util.List;

public class SpellLoader
extends EffectContentDocumentLoader {
    private static final SpellLoader m_instance = new SpellLoader();

    public static SpellLoader getInstance() {
        return m_instance;
    }

    private SpellLoader() {
        this.setContentDocumentExtension(".dat");
    }

    public String getName() {
        return DofusArenaTranslator.getInstance().getString("contentLoader.spell", new Object[0]);
    }

    public void init(AbstractGameClientInstance clientInstance) throws Exception {
        this.open(DofusArenaConfiguration.getInstance().getString("contentSpellFile"));
        clientInstance.fireContentInitializerDone(this);
    }

    public void read(DocumentContainer container) {
        if (container == null) {
            return;
        }
        try {
            int spellCount = this.readInteger();
            int i = 0;
            while (i < spellCount) {
                int spellId = this.readInteger();
                byte spellActionPoints = this.readByte();
                byte spellCastFrequencyMaxPerPlayer = this.readByte();
                byte spellCastFrequencyMaxPerTurn = this.readByte();
                byte spellCastFrequencyMinInterval = this.readByte();
                boolean spellCastTestLos = this.readBoolean();
                boolean spellCastOnlyLine = this.readBoolean();
                byte spellCastRangeMin = this.readByte();
                byte spellCastRangeMax = this.readByte();
                int spellValue = this.readInteger();
                int spellAiTargetId = this.readInteger();
                boolean testFreeCell = this.readBoolean();
                int spellScriptId = this.readInteger();
                int breedId = this.readInteger();
                String criterionString = this.readString();
                List<Criterion> criterion = CriteriaCompiler.compile(null, criterionString);
                boolean useAutomaticDescription = this.readBoolean();
                Spell spell = new Spell(spellId, breedId, spellActionPoints, spellCastFrequencyMaxPerPlayer, spellCastFrequencyMaxPerTurn, spellCastFrequencyMinInterval, spellCastTestLos, spellCastOnlyLine, spellCastRangeMin, spellCastRangeMax, spellValue, spellAiTargetId, testFreeCell, spellScriptId, criterion, useAutomaticDescription);
                SpellManager.getInstance().addSpell(spell);
                ++i;
            }
            int effectCount = this.readInteger();
            int i2 = 0;
            while (i2 < effectCount) {
                this.readAndLoadEffect();
                ++i2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        container.notifyOnLoadComplete();
    }

    public void onEffectLoaded(Effect effect, String parentType, int parentId) {
        Spell spell = (Spell)SpellManager.getInstance().getSpell(parentId);
        if (spell != null) {
            spell.addEffect(effect);
        }
    }

    public void notifyOnLoadComplete() {
        m_logger.info((Object)"Spells loaded successfully");
    }
}

