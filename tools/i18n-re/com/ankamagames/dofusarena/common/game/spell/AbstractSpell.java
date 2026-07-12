/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.common.game.spell;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
import com.ankamagames.baseImpl.common.clientAndServer.game.spell.BasicSpell;
import com.ankamagames.framework.ai.criteria.Criterion;
import com.ankamagames.framework.kernel.core.common.collections.GrowingArray;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractSpell
extends BasicSpell
implements EffectContainer,
InventoryContent {
    protected GrowingArray<Effect> m_effects = new GrowingArray();
    private final byte m_actionPoints;
    private final int m_breedId;
    private final byte m_castMaxPerTarget;
    private final byte m_castMaxPerTurn;
    private final byte m_castInterval;
    private final boolean m_testLineOfSight;
    private final boolean m_castOnlyInLine;
    private final boolean m_testFreeCell;
    private final byte m_rangeMin;
    private final byte m_rangeMax;
    private final int m_value;
    private final int m_target;
    private final List<Criterion> m_castCriterions;
    private boolean m_canBeCritical = false;

    public AbstractSpell(int id, int breedId, byte actionPoints, byte castMaxPerPlayer, byte castMaxPerTurn, byte castInterval, boolean lineOfSight, boolean castOnlyInLine, byte rangeMin, byte rangeMax, int goldValue, int target, boolean testFreeCell, List<Criterion> criterion) {
        super(id);
        this.m_breedId = breedId;
        this.m_actionPoints = actionPoints;
        this.m_castMaxPerTarget = castMaxPerPlayer;
        this.m_castMaxPerTurn = castMaxPerTurn;
        this.m_castInterval = castInterval;
        this.m_testLineOfSight = lineOfSight;
        this.m_castOnlyInLine = castOnlyInLine;
        this.m_rangeMin = (byte)Math.max(0, Math.min(rangeMin, rangeMax));
        this.m_rangeMax = (byte)Math.max(0, Math.max(rangeMin, rangeMax));
        this.m_value = goldValue;
        this.m_target = target;
        this.m_testFreeCell = testFreeCell;
        this.m_castCriterions = criterion;
    }

    @Override
    public void addEffect(Effect effect) {
        this.m_effects.add(effect);
        if (effect.checkFlags(1L)) {
            this.m_canBeCritical = true;
        }
    }

    @Override
    public void addEffects(Effect[] effects) {
        Effect[] effectArray = effects;
        int n = effects.length;
        int n2 = 0;
        while (n2 < n) {
            Effect effect = effectArray[n2];
            this.addEffect(effect);
            ++n2;
        }
    }

    public void setEffects(Effect[] effects) {
        this.m_canBeCritical = false;
        this.m_effects.clear();
        this.addEffects(effects);
    }

    public Effect getEffectById(int effectId) {
        for (Effect eff : this.m_effects) {
            if (eff.getEffectId() != effectId) continue;
            return eff;
        }
        return null;
    }

    public GrowingArray<Effect> getEffects() {
        return this.m_effects;
    }

    public List<Criterion> getCastCriterions() {
        return this.m_castCriterions;
    }

    public boolean canCanBeCritical() {
        return this.m_canBeCritical;
    }

    public boolean castOnlyInLine() {
        return this.m_castOnlyInLine;
    }

    @Override
    public long getEffectContainerId() {
        return this.getId();
    }

    @Override
    public int getContainerType() {
        return 13;
    }

    @Override
    public Iterator<Effect> iterator() {
        return this.m_effects.iterator();
    }

    public int getBreedId() {
        return this.m_breedId;
    }

    public byte getActionPoints() {
        return this.m_actionPoints;
    }

    public byte getCastMaxPerTarget() {
        return this.m_castMaxPerTarget;
    }

    public byte getCastMaxPerTurn() {
        return this.m_castMaxPerTurn;
    }

    public byte getMinCastInterval() {
        return this.m_castInterval;
    }

    public boolean hasToTestLineOfSight() {
        return this.m_testLineOfSight;
    }

    public boolean hasToTestFreeCell() {
        return this.m_testFreeCell;
    }

    public byte getRangeMin() {
        return this.m_rangeMin;
    }

    public byte getRangeMax() {
        return this.m_rangeMax;
    }

    public int getValue() {
        return this.m_value;
    }

    public byte getCastInterval() {
        return this.m_castInterval;
    }

    public int getTarget() {
        return this.m_target;
    }

    @Override
    public void release() {
    }

    @Override
    public long getUniqueId() {
        return this.getId();
    }

    @Override
    public int getReferenceId() {
        return this.getId();
    }

    @Override
    public byte[] serialize() {
        byte[] b = new byte[4];
        ByteBuffer.wrap(b).putInt(this.getId());
        return b;
    }

    @Override
    public boolean unserialize(ByteBuffer buf) {
        throw new UnsupportedOperationException("AbstractSpell can't be unserialized. Need to be get from AbstractReferenceCoachCardManager");
    }

    @Override
    public short getQuantity() {
        return 1;
    }

    @Override
    public void setQuantity(short quantity) {
        throw new UnsupportedOperationException("Spell can't be stacked");
    }

    @Override
    public void updateQuantity(short quantityUpdate) {
        throw new UnsupportedOperationException("Spell can't be stacked");
    }

    @Override
    public short getStackMaximumHeight() {
        return 1;
    }

    @Override
    public InventoryContent getCopy() {
        try {
            return (AbstractSpell)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Unable to copy AbstractSpell", e);
        }
    }

    @Override
    public InventoryContent getClone() {
        try {
            return (AbstractSpell)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Unable to clone AbstractSpell", e);
        }
    }
}

