/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from SU
 */
class su_0
extends uc_1 {
    final /* synthetic */ us_2 bLZ;

    public su_0(us_2 us_22, LuaState luaState) {
        this.bLZ = us_22;
        super(luaState);
    }

    public String getName() {
        return "getCaster";
    }

    public LX[] Q() {
        return null;
    }

    public void c(int n2) {
        this.da(us_2.a(this.bLZ).Nl());
    }

    public LX[] R() {
        return new LX[]{new LX("instigatorId", aos_1.elR, false)};
    }
}

