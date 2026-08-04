/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.common.game.effect.runningEffect;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
import com.ankamagames.dofusarena.common.game.effect.runningEffect.ArenaRunningEffect;
import com.ankamagames.framework.kernel.core.common.MonitoredPool;
import com.ankamagames.framework.kernel.core.common.ObjectFactory;
import org.apache.commons.pool.ObjectPool;

public class SpellRebound
extends ArenaRunningEffect {
    private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory<SpellRebound>(){

        @Override
        public SpellRebound makeObject() {
            return new SpellRebound();
        }
    });
    private float m_executionRate;

    public float getExecutionRate() {
        return this.m_executionRate;
    }

    public SpellRebound newInstance() {
        SpellRebound re;
        try {
            re = (SpellRebound)m_staticPool.borrowObject();
            re.m_pool = m_staticPool;
        }
        catch (Exception e) {
            re = new SpellRebound();
            re.m_pool = null;
            m_logger.error((Object)("Erreur lors d'un checkOut sur un SpellRebound : " + e.getMessage()));
        }
        re.cloneParameters(this);
        re.m_executionRate = this.m_genericEffect.getParams() != null && this.m_genericEffect.getParams().length == 1 ? Math.min(99.0f, this.m_genericEffect.getParam(0)) : 99.0f;
        return re;
    }

    public void execute(RunningEffect linkedRE, boolean trigger) {
        if (!(linkedRE == null || linkedRE.getEffectContainer() == null || linkedRE.getEffectContainer().getContainerType() != 13 || this.m_value <= 0 || linkedRE.useTarget() && linkedRE.getCaster() == null)) {
            linkedRE.setTarget(linkedRE.getCaster());
        }
        super.execute(linkedRE, trigger);
    }

    public void computeValue(RunningEffect triggerRE) {
        this.m_value = (float)DiceRoll.roll(100) <= this.m_executionRate ? 1 : 0;
    }

    public boolean useCaster() {
        return true;
    }

    public boolean useTarget() {
        return true;
    }

    public boolean useTargetCell() {
        return false;
    }

    public boolean mustBeStacked() {
        return true;
    }

    public boolean canBeStackedWith(RunningEffect reToStack) {
        return reToStack.getId() == this.getId() && reToStack != this;
    }

    public void stackWith(RunningEffect reToStack) {
        super.stackWith(reToStack);
        SpellRebound re = (SpellRebound)reToStack;
        this.m_executionRate = Math.min(99.0f, this.m_executionRate + re.getExecutionRate());
    }
}

