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
 * Renamed from tC
 */
class tc_1
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private tc_1(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "prepareFloating";
    }

    public LX[] Q() {
        return new LX[]{new LX("align", aos_1.elS, false), new LX("screenXoffset", aos_1.elT, false), new LX("screenYoffset", aos_1.elT, false), new LX("actAsButton", aos_1.elV, false), new LX("duration", aos_1.elT, true), new LX("closeOnClick", aos_1.elV, true), new LX("onEndFunc", aos_1.elS, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        LuaObject luaObject;
        ajn_1 ajn_12 = ajn_1.valueOf(this.hZ(0));
        int n3 = this.hW(1);
        int n4 = this.hW(2);
        boolean bl2 = this.ic(3);
        int n5 = Integer.MAX_VALUE;
        boolean bl3 = false;
        String string = null;
        if (n2 >= 5) {
            luaObject = this.getParam(6);
            if (luaObject.isNumber()) {
                n5 = (int)luaObject.getNumber();
            } else if (luaObject.isBoolean()) {
                bl3 = luaObject.getBoolean();
            } else if (luaObject.isString()) {
                string = luaObject.getString();
            }
        }
        if (n2 >= 6) {
            luaObject = this.getParam(7);
            if (luaObject.isBoolean()) {
                bl3 = luaObject.getBoolean();
            } else if (luaObject.isString()) {
                string = luaObject.getString();
            }
        }
        if (n2 >= 7 && (luaObject = this.getParam(8)).isString()) {
            string = luaObject.getString();
        }
        int n6 = wj_2.Df().Dg();
        String string2 = "interactiveBubbleDialog" + n6;
        aod_2 aod_22 = (aod_2)add_1.aOG().a(string2, oh_2.bq("interactiveBubbleDialog"), n5, 64L, (short)30001);
        if (string != null) {
            rt_0.a(this.nt).c(string2.hashCode(), new dr_0(this.nt, string, this.agC(), null));
        }
        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
        rt_0.b(this.nt).c(n6, aod_22);
        aod_22.setActAsButton(bl2);
        auW auW2 = new auW();
        auW2.b();
        auW2.setAlign(ajn_12);
        auW2.setXOffset(n3);
        auW2.setYOffset(n4);
        aod_22.a(auW2);
        aod_22.setVisible(false);
        aod_22.setCloseOnClick(bl3);
        aod_22.setForcedDisplaySpark(false);
        aod_22.setUseTargetPositionning(false);
        this.id(n6);
    }

    /* synthetic */ tc_1(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

