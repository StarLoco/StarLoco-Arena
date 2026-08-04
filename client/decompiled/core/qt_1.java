/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from qt
 */
class qt_1
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private qt_1(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "prepareQuestion";
    }

    public LX[] Q() {
        return new LX[]{new LX("text", aos_1.elS, false), new LX("align", aos_1.elS, false), new LX("screenXoffset", aos_1.elT, false), new LX("screenYoffset", aos_1.elT, false), new LX("firstChoiceFunc", aos_1.elS, false), new LX("secondChoiceFunc", aos_1.elS, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        String string = rt_0.fN(this.hZ(0));
        ajn_1 ajn_12 = ajn_1.valueOf(this.hZ(1));
        int n3 = this.hW(2);
        int n4 = this.hW(3);
        String string2 = this.hZ(4);
        String string3 = this.hZ(5);
        int n5 = wj_2.Df().Dg();
        String string4 = "interactiveBubbleDialog" + n5;
        JX jX = this.agC();
        aod_2 aod_22 = (aod_2)add_1.aOG().a(string4, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
        ov_1 ov_12 = (ov_1)aMi.aWT().a(jX, "interactiveBubbleDialog" + n5, aon_0.aYc().getString(string2), "MOUSE_CLICKED", string2);
        if (ov_12 == null) {
            ov_12 = new iN(jX, string2);
            aMi.aWT().a(jX, "interactiveBubbleDialog" + n5, aon_0.aYc().getString(string2), "MOUSE_CLICKED", string2, ov_12);
            aod_22.a(aon_0.aYc().getString(string2), ov_12, true);
        } else {
            ((iN)ov_12).a((jJ[])null);
        }
        ov_1 ov_13 = (ov_1)aMi.aWT().a(jX, "interactiveBubbleDialog" + n5, aon_0.aYc().getString(string3), "MOUSE_CLICKED", string3);
        if (ov_13 == null) {
            ov_13 = new iN(jX, string3);
            aMi.aWT().a(jX, "interactiveBubbleDialog" + n5, aon_0.aYc().getString(string3), "MOUSE_CLICKED", string3, ov_13);
            aod_22.a(aon_0.aYc().getString(string3), ov_13, true);
        } else {
            ((iN)ov_13).a((jJ[])null);
        }
        aod_22.setForcedDisplaySpark(true);
        aod_22.setUseTargetPositionning(false);
        aod_22.setText(string);
        aod_22.setActAsButton(true);
        auW auW2 = new auW();
        auW2.b();
        auW2.setAlign(ajn_12);
        auW2.setXOffset(n3);
        auW2.setYOffset(n4);
        aod_22.a(auW2);
        aod_22.setVisible(true);
        aod_22.setCloseOnClick(false);
        rt_0.b(this.nt).c(n5, aod_22);
        this.id(n5);
    }

    /* synthetic */ qt_1(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

