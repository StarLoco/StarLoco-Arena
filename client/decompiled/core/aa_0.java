/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaObject
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Aa
 */
class aa_0
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private aa_0(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "prepare";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("actAsButton", aos_1.elV, false), new LX("duration", aos_1.elT, true), new LX("closeOnClick", aos_1.elV, true), new LX("onEndFunc", aos_1.elS, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        LuaObject luaObject;
        mT mT2 = bd_1.Is().bb(this.hY(0));
        if (mT2 == null) {
            this.a(a, "Le mobile n'existe pas " + this.hY(0));
            this.agB();
            return;
        }
        int n3 = Integer.MAX_VALUE;
        boolean bl2 = this.ic(1);
        boolean bl3 = false;
        String string = null;
        if (n2 >= 3) {
            luaObject = this.getParam(4);
            if (luaObject.isNumber()) {
                n3 = (int)luaObject.getNumber();
            } else if (luaObject.isBoolean()) {
                bl3 = luaObject.getBoolean();
            } else if (luaObject.isString()) {
                string = luaObject.getString();
            }
        }
        if (n2 >= 4) {
            luaObject = this.getParam(5);
            if (luaObject.isBoolean()) {
                bl3 = luaObject.getBoolean();
            } else if (luaObject.isString()) {
                string = luaObject.getString();
            }
        }
        if (n2 >= 5 && (luaObject = this.getParam(6)).isString()) {
            string = luaObject.getString();
        }
        int n4 = wj_2.Df().Dg();
        String string2 = "interactiveBubbleDialog" + n4;
        aod_2 aod_22 = (aod_2)add_1.aOG().a(string2, oh_2.bq("interactiveBubbleDialog"), n3, 64L, (short)30001);
        if (string != null) {
            rt_0.a(this.nt).c(string2.hashCode(), new dr_0(this.nt, string, this.agC(), null));
        }
        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
        aod_22.setTarget(mT2);
        aod_22.setActAsButton(bl2);
        aod_22.setVisible(false);
        aod_22.setCloseOnClick(bl3);
        rt_0.b(this.nt).c(n4, aod_22);
        this.id(n4);
    }

    /* synthetic */ aa_0(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

