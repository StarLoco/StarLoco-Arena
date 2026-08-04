/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class aAZ
extends mp_0 {
    private aor dqT;

    public aAZ(aor aor2) {
        super("EffectArea");
        this.dqT = aor2;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new abD(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    static /* synthetic */ aor a(aAZ aAZ2) {
        return aAZ2.dqT;
    }
}

