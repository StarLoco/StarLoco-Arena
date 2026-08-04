/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from lD
 */
class ld_0
extends uc_1 {
    final /* synthetic */ vp_2 am;

    public ld_0(vp_2 vp_22, LuaState luaState) {
        this.am = vp_22;
        super(luaState);
    }

    public String getName() {
        return "addPointLight";
    }

    public LX[] Q() {
        return new LX[]{new LX("posX", aos_1.elU, false), new LX("posY", aos_1.elU, false), new LX("posZ", aos_1.elU, false), new LX("red", aos_1.elU, false), new LX("green", aos_1.elU, false), new LX("blue", aos_1.elU, false), new LX("radius", aos_1.elU, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("lightId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        lP lP2 = (lP)do_0.aNC.P();
        lP2.a(new et_0(this.hX(0), this.hX(1), this.hX(2)));
        lP2.s((float)this.hX(3), (float)this.hX(4), (float)this.hX(5));
        if (n2 == 7) {
            lP2.u((float)this.hX(6));
        }
        ahn_0.dNL.a(lP2);
        int n3 = vp_2.BH();
        vp_2.a(this.am).c(n3, lP2);
        this.id(n3);
    }
}

