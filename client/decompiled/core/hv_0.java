/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from hv
 */
class hv_0
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    public hv_0(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "showText";
    }

    public LX[] Q() {
        return new LX[]{new LX("characterId", aos_1.elR, false), new LX("text", aos_1.elS, false), new LX("offsetX", aos_1.elT, false), new LX("offsetY", aos_1.elT, false), new LX("infiniteDuration", aos_1.elV, true), new LX("funcOnEnd", aos_1.elS, true)};
    }

    public final LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false), new LX("displayTime", aos_1.elT, false)};
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            String string = rt_0.fN(this.hZ(1));
            atn_0 atn_02 = new atn_0(string);
            atn_02.c(mT2);
            atn_02.setXOffset(this.hW(2));
            atn_02.setYOffset(this.hW(3));
            boolean bl2 = false;
            String string2 = null;
            if (n2 >= 5) {
                if (this.getParam(6).isBoolean()) {
                    bl2 = this.ic(4);
                } else if (this.getParam(6).isString()) {
                    string2 = this.hZ(4);
                }
            }
            if (n2 >= 6 && this.getParam(7).isString()) {
                string2 = this.hZ(5);
            }
            if (bl2) {
                atn_02.setDuration(-1);
            }
            if (string2 != null) {
                dr_0 dr_02 = new dr_0(this.nt, string2, this.agC(), null);
            }
            wj_2.Df().a(atn_02);
            this.id(atn_02.getId());
            this.id(atn_02.getDuration());
        } else {
            this.a(a, "mobile " + l2 + " inexistant");
            this.agB();
            this.agB();
        }
    }
}

