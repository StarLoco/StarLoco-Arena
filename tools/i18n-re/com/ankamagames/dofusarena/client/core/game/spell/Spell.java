/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.core.game.spell;

import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
import com.ankamagames.dofusarena.client.core.contentInitializer.CastableDescriptionGenerator;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
import com.ankamagames.framework.ai.criteria.Criterion;
import com.ankamagames.xulor.property.FieldProvider;
import java.net.URL;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Spell
extends AbstractSpell
implements FieldProvider,
Comparable {
    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String ICON_URL_FIELD = "iconUrl";
    public static final String ILLUSTRATION_URL_FIELD = "illustrationUrl";
    public static final String CARD_TYPE_FIELD = "cardType";
    public static final String VALUE_FIELD = "value";
    public static final String ACTION_POINTS_FIELD = "actionPoints";
    public static final String[] FIELDS = new String[]{"id", "name", "description", "iconUrl", "illustrationUrl", "cardType", "value", "actionPoints"};
    private final int m_scriptId;
    private String m_description = null;
    private final boolean m_useAutomaticDescription;

    public Spell(int id, int breedId, byte actionPoints, byte castMaxPerTarget, byte castMaxPerTurn, byte castInterval, boolean lineOfSight, boolean castOnLine, byte rangeMin, byte rangeMax, int value, int target, boolean testFreeCell, int scriptId, List<Criterion> castCriterion, boolean useAutomaticDescription) {
        super(id, breedId, actionPoints, castMaxPerTarget, castMaxPerTurn, castInterval, lineOfSight, castOnLine, rangeMin, rangeMax, value, target, testFreeCell, castCriterion);
        this.m_scriptId = scriptId;
        this.m_useAutomaticDescription = useAutomaticDescription;
    }

    public String getName() {
        return DofusArenaTranslator.getInstance().getString(3, this.getId());
    }

    public String getDescription() {
        if (this.m_description == null) {
            this.m_description = CastableDescriptionGenerator.generateDescription(this.getId(), this.isUseAutomaticDescription(), this.getEffects(), null, this.getRangeMin(), this.getRangeMax(), false, this.hasToTestFreeCell(), this.getCastInterval(), this.getCastMaxPerTarget(), this.getCastMaxPerTurn(), 20, 4);
        }
        return this.m_description;
    }

    public int getScriptId() {
        return this.m_scriptId;
    }

    public boolean isUseAutomaticDescription() {
        return this.m_useAutomaticDescription;
    }

    public URL getIconUrl() {
        try {
            return new URL(String.format(DofusArenaConfiguration.getInstance().getString("spellsIconsPath"), this.getId()));
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void appendFieldValue(String fieldName, Object value) {
    }

    @Override
    public Object getFieldValue(String fieldName) {
        if (fieldName.equals(ID_FIELD)) {
            return this.getId();
        }
        if (fieldName.equals(NAME_FIELD)) {
            return this.getName();
        }
        if (fieldName.equals(DESCRIPTION_FIELD)) {
            return this.getDescription();
        }
        if (fieldName.equals(ICON_URL_FIELD)) {
            try {
                return String.format(DofusArenaConfiguration.getInstance().getString("spellsIconsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (fieldName.equals(ILLUSTRATION_URL_FIELD)) {
            try {
                return String.format(DofusArenaConfiguration.getInstance().getString("spellsIllustrationsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (fieldName.equals(CARD_TYPE_FIELD)) {
            return "spell";
        }
        if (fieldName.equals(ACTION_POINTS_FIELD)) {
            return this.getActionPoints();
        }
        if (fieldName.equals(VALUE_FIELD)) {
            return this.getValue();
        }
        return null;
    }

    @Override
    public String[] getFields() {
        return FIELDS;
    }

    @Override
    public boolean isFieldSynchronisable(String fieldName) {
        return false;
    }

    @Override
    public void prependFieldValue(String fieldName, Object value) {
    }

    @Override
    public void setFieldValue(String fieldName, Object value) {
    }

    public int compareTo(Object o) {
        if (o instanceof Spell) {
            return this.getName().compareTo(((Spell)o).getName());
        }
        throw new RuntimeException("attempting to compare a " + o.getClass().getName() + " to a " + this.getClass().getName());
    }
}

