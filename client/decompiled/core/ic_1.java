/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Ic
 */
class ic_1
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private ic_1(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "attachToMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false), new LX("mobileId", aos_1.elR, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        aod_2 aod_22 = (aod_2)rt_0.b(this.nt).get(n3);
        if (aod_22 == null) {
            this.a(a, "pas de bulle de texte " + n3);
            return;
        }
        long l2 = this.hY(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            aod_22.setTarget(mT2);
            aod_22.a(mT2, mT2.getScreenX(), mT2.getScreenY(), mT2.hB());
        } else {
            this.a(a, "mobile inconnu " + l2);
        }
        a.info((Object)("attachtoMobile " + aod_22 + " " + l2));
    }

    /* synthetic */ ic_1(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

