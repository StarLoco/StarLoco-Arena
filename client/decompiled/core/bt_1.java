/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from bT
 */
public class bt_1
extends uc_1 {
    public bt_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMusicMix";
    }

    public LX[] Q() {
        return new LX[]{new LX("targetGain", aos_1.elU, false), new LX("fadeOutTime", aos_1.elU, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        float f = (float)this.hX(0);
        float f2 = -1.0f;
        if (n2 > 1) {
            f2 = (float)this.hX(1);
        }
        aau_0.apB().B(f, f2);
    }
}

