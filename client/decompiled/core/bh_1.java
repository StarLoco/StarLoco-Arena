/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from bH
 */
public class bh_1
extends mp_0 {
    protected final Logger hd;
    final /* synthetic */ ahh_1 he;

    protected bh_1(ahh_1 ahh_12) {
        this.he = ahh_12;
        super("Sound");
        this.hd = Logger.getLogger(bh_1.class);
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new adh(this, luaState), new aa_1(this, luaState), new je_0(this, luaState), new agO(this, luaState), new OL(this, luaState), new agt_1(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }
}

