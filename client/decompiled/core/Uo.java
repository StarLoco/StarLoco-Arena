/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class Uo
extends uc_1 {
    final /* synthetic */ us_2 bLZ;

    public Uo(us_2 us_22, LuaState luaState) {
        this.bLZ = us_22;
        super(luaState);
    }

    public String getName() {
        return "getPosition";
    }

    public LX[] Q() {
        return null;
    }

    public void c(int n2) {
        this.id(us_2.a(this.bLZ).getX());
        this.id(us_2.a(this.bLZ).getY());
        this.id(us_2.a(this.bLZ).wk());
    }

    public LX[] R() {
        return new LX[]{new LX("x", aos_1.elU, false), new LX("y", aos_1.elU, false), new LX("z", aos_1.elU, false)};
    }
}

