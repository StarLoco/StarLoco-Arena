/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from arA
 */
public final class ara_0
extends uc_1 {
    public ara_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileStatus";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("statusId", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        byte by = (byte)this.hW(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.setStatus(by);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

