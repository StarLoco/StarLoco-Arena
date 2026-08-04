/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.core.game.spell;

import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import com.ankamagames.dofusarena.common.game.fight.SpellCastValidity;

public class UsableSpell
extends Spell {
    public static final String USABLE_FIELD = "usable";
    public static final String SMALL_DESCRIPTION_FIELD = "smallDescription";
    public static final String[] FIELDS = new String[]{"usable", "smallDescription"};
    public static final String[] ALL_FIELDS = new String[FIELDS.length + Spell.FIELDS.length];
    private Fighter m_fighter;

    static {
        System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
        System.arraycopy(Spell.FIELDS, 0, ALL_FIELDS, FIELDS.length, Spell.FIELDS.length);
    }

    public UsableSpell(Spell spell) {
        super(spell.getId(), spell.getBreedId(), spell.getActionPoints(), spell.getCastMaxPerTarget(), spell.getCastMaxPerTurn(), spell.getCastInterval(), spell.hasToTestLineOfSight(), spell.castOnlyInLine(), spell.getRangeMin(), spell.getRangeMax(), spell.getValue(), spell.getTarget(), spell.hasToTestFreeCell(), spell.getScriptId(), spell.getCastCriterions(), spell.isUseAutomaticDescription());
        this.m_effects = spell.getEffects();
    }

    public Fighter getFighter() {
        return this.m_fighter;
    }

    public void setFighter(Fighter fighter) {
        this.m_fighter = fighter;
    }

    public SpellCastValidity getCastValidity() {
        if (this.m_fighter != null) {
            return DofusArenaGameEntity.getInstance().getFight().getSpellCastValidity(this.m_fighter, this, null);
        }
        return SpellCastValidity.OK;
    }

    public String[] getFields() {
        return ALL_FIELDS;
    }

    public Object getFieldValue(String fieldName) {
        if (fieldName.equals(USABLE_FIELD)) {
            if (this.getCastValidity() == SpellCastValidity.OK) {
                return true;
            }
            return false;
        }
        if (fieldName.equals(SMALL_DESCRIPTION_FIELD)) {
            StringBuilder smallDescriptionBuilder = new StringBuilder(this.getName());
            smallDescriptionBuilder.append(" (").append(this.getActionPoints()).append(' ').append(DofusArenaTranslator.getInstance().getString("AP", new Object[0])).append(")");
            SpellCastValidity castValidity = this.getCastValidity();
            if (castValidity != SpellCastValidity.OK) {
                smallDescriptionBuilder.append('\n').append(DofusArenaTranslator.getInstance().getString(castValidity.toString(), new Object[0]));
            }
            return smallDescriptionBuilder.toString();
        }
        return super.getFieldValue(fieldName);
    }
}

