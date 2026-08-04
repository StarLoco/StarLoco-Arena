/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class fp
extends mp_0 {
    private static final Logger a = Logger.getLogger(fp.class);
    private static final fp qW = new fp();

    private fp() {
        super("InteractiveElement");
    }

    public static fp iF() {
        return qW;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new yg_2(this, luaState, null), new hf_1(this, luaState), new ue_2(this, luaState), new aaq_1(this, luaState), new ik_1(this, luaState), new aMj(this, luaState), new aoo_2(this, luaState), new anu_2(this, luaState), new aps_0(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }
}

