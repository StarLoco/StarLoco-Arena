/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.core.script;

import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
import com.ankamagames.baseImpl.graphics.alea.adviser.text.flying.FlyingText;
import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
import com.ankamagames.dofusarena.client.core.action.SpellEffectAction;
import com.ankamagames.framework.script.JavaFunctionEx;
import com.ankamagames.framework.script.JavaFunctionsLibrary;
import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
import com.ankamagames.framework.script.LuaScriptParameterType;
import java.awt.Font;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

public class SpellEffectActionFunctionsLibrary
extends JavaFunctionsLibrary {
    private SpellEffectAction m_spellEffectAction;

    public SpellEffectActionFunctionsLibrary(SpellEffectAction action) {
        super("SpellEffect");
        this.m_spellEffectAction = action;
        this.registerFunctionClass(GetTarget.class);
        this.registerFunctionClass(GetParams.class);
        this.registerFunctionClass(DisplayFlyingValue.class);
    }

    private class DisplayFlyingValue
    extends JavaFunctionEx {
        private int FLYING_EFFECT_DURATION;

        public DisplayFlyingValue(LuaState luaState) {
            super(luaState);
            this.FLYING_EFFECT_DURATION = 4000;
        }

        public String getName() {
            return "displayFlyingValue";
        }

        public LuaScriptParameterDescriptor[] getParameterDescriptors() {
            return new LuaScriptParameterDescriptor[]{new LuaScriptParameterDescriptor("R", LuaScriptParameterType.NUMBER, false), new LuaScriptParameterDescriptor("G", LuaScriptParameterType.NUMBER, false), new LuaScriptParameterDescriptor("B", LuaScriptParameterType.NUMBER, false), new LuaScriptParameterDescriptor("negatesValue", LuaScriptParameterType.BOOLEAN, true)};
        }

        public void run(int paramCount) throws LuaException {
            Mobile mobile;
            float r = (float)this.getParamDouble(0);
            float g = (float)this.getParamDouble(1);
            float b = (float)this.getParamDouble(2);
            int value = SpellEffectActionFunctionsLibrary.this.m_spellEffectAction.getEffectValue();
            if (value == 0) {
                return;
            }
            if (paramCount >= 4 && this.getParamBool(3)) {
                value *= -1;
            }
            if ((mobile = MobileManager.getInstance().getMobile(SpellEffectActionFunctionsLibrary.this.m_spellEffectAction.getTargetId())) == null || !mobile.isVisible()) {
                return;
            }
            FlyingText flyingText = new FlyingText(new Font("Verdana", 1, 20), String.valueOf(value), this.FLYING_EFFECT_DURATION);
            flyingText.setColor(r, g, b, 1.0f);
            flyingText.setTarget(mobile);
            AdviserManager.getInstance().addAdviser(flyingText);
        }
    }

    private class GetParams
    extends JavaFunctionEx {
        public GetParams(LuaState luaState) {
            super(luaState);
        }

        public String getName() {
            return "getParams";
        }

        public LuaScriptParameterDescriptor[] getParameterDescriptors() {
            return null;
        }

        public void run(int paramCount) throws LuaException {
        }
    }

    private class GetTarget
    extends JavaFunctionEx {
        public GetTarget(LuaState luaState) {
            super(luaState);
        }

        public String getName() {
            return "getTarget";
        }

        public LuaScriptParameterDescriptor[] getParameterDescriptors() {
            return null;
        }

        public void run(int paramCount) throws LuaException {
            this.addReturnValue(SpellEffectActionFunctionsLibrary.this.m_spellEffectAction.getTargetId());
        }
    }
}

