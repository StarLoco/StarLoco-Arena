/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from avC
 */
class avc_0
extends uc_1 {
    final /* synthetic */ us_2 bLZ;

    public avc_0(us_2 us_22, LuaState luaState) {
        this.bLZ = us_22;
        super(luaState);
    }

    public String getName() {
        return "isCritical";
    }

    public LX[] Q() {
        return null;
    }

    public void c(int n2) {
        this.cp(us_2.a(this.bLZ).wi());
    }

    public LX[] R() {
        return new LX[]{new LX("criticalHit", aos_1.elV, false)};
    }
}

