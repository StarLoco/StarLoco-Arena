/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.core.action;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
import com.ankamagames.baseImpl.graphicalClient.script.MobileFunctionsLibrary;
import com.ankamagames.baseImpl.graphicalClient.script.ParticleSystemFunctionsLibrary;
import com.ankamagames.baseImpl.graphicalClient.script.SoundFunctionsLibrary;
import com.ankamagames.baseImpl.graphicalClient.script.VisualEffectFunctionsLibrary;
import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
import com.ankamagames.dofusarena.client.core.game.fight.Fight;
import com.ankamagames.dofusarena.client.core.script.SpellEffectActionFunctionsLibrary;
import com.ankamagames.dofusarena.common.game.effect.ArenaEffectContext;
import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
import com.ankamagames.framework.script.action.ScriptedAction;

public class SpellEffectAction
extends ScriptedAction {
    private RunningEffect m_effect;
    private int m_effectValue;
    private boolean m_isTriggered;

    public SpellEffectAction(int uniqueId, int actionType, int actionId, RunningEffect effect, boolean isTriggered) {
        super(uniqueId, actionType, actionId);
        this.m_effect = effect;
        this.m_isTriggered = isTriggered;
        if (this.m_effect != null) {
            this.m_effectValue = effect.getValue();
        }
        this.addJavaFunctionsLibrary(MobileFunctionsLibrary.getInstance());
        this.addJavaFunctionsLibrary(ParticleSystemFunctionsLibrary.getInstance());
        this.addJavaFunctionsLibrary(SoundFunctionsLibrary.getInstance());
        this.addJavaFunctionsLibrary(VisualEffectFunctionsLibrary.getInstance());
        this.addJavaFunctionsLibrary(new SpellEffectActionFunctionsLibrary(this));
        this.setScriptFileId(this.getScriptIdFromEffectId(actionId));
    }

    protected void onActionFinished() {
        Fight fight = DofusArenaGameEntity.getInstance().getFight();
        if (fight != null && this.m_effect != null) {
            this.m_effect.setContext(ArenaEffectContext.checkOut(fight));
            if (this.m_isTriggered) {
                this.m_effect.execute(null, true);
            } else {
                if (this.m_effect.hasDuration() && this.m_effect.getTarget() != null && this.m_effect.getTarget().getRunningEffectManager() != null) {
                    if (this.m_effect.mustBeStacked()) {
                        this.m_effect.getTarget().getRunningEffectManager().stackEffect(this.m_effect);
                    } else {
                        this.m_effect.getTarget().getRunningEffectManager().storeEffect(this.m_effect);
                    }
                }
                if (this.m_effect.hasDuration() && !this.m_effect.isInfinite()) {
                    this.m_effect.pushRunningEffectDurationTimeEventInTimeline();
                }
                if (!this.m_effect.mustBeTriggered()) {
                    this.m_effect.execute(null, false);
                }
            }
        }
    }

    private int getScriptIdFromEffectId(int effectId) {
        RunningEffectDefinition definition = (RunningEffectDefinition)RunningEffectConstants.getInstance().getConstantDefinition(effectId);
        if (definition == null) {
            return -1;
        }
        return definition.getScriptId();
    }

    public RunningEffect getEffect() {
        return this.m_effect;
    }

    public int getEffectValue() {
        return this.m_effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.m_effectValue = effectValue;
    }
}

