/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aJX
 */
class ajx_2
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private ajx_2(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "showFloatingMonologue";
    }

    public LX[] Q() {
        return new LX[]{new LX("align", aos_1.elS, false), new LX("screenXoffset", aos_1.elT, false), new LX("screenYoffset", aos_1.elT, false), new LX("texts", aos_1.elW, false), new LX("funcOnTerminate", aos_1.elS, true), new LX("funcParams", aos_1.elX, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        ajn_1 ajn_12 = ajn_1.valueOf(this.hZ(0));
        int n3 = this.hW(1);
        int n4 = this.hW(2);
        int n5 = Integer.MAX_VALUE;
        String string = null;
        jJ[] jJArray = this.ia(3);
        if (jJArray.length == 0) {
            this.a(a, "La table de texte est vide");
        }
        String[] stringArray = new String[jJArray.length];
        for (int j = 0; j < jJArray.length; ++j) {
            stringArray[j] = rt_0.fN((String)jJArray[j].getValue());
        }
        String string2 = n2 > 4 ? this.hZ(4) : null;
        jJ[] jJArray2 = this.aX(5, n2);
        JX jX = this.agC();
        int n6 = wj_2.Df().Dg();
        String string3 = "interactiveBubbleDialog" + n6;
        aod_2 aod_22 = (aod_2)add_1.aOG().a(string3, oh_2.bq("interactiveBubbleDialog"), n5, 64L, (short)30001);
        if (string != null) {
            rt_0.a(this.nt).c(string3.hashCode(), new dr_0(this.nt, string, this.agC(), null));
        }
        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
        rt_0.b(this.nt).c(n6, aod_22);
        int[] nArray = new int[]{0};
        aod_22.setBubbleText(stringArray[0]);
        aod_22.setActAsButton(true);
        auW auW2 = new auW();
        auW2.b();
        auW2.setAlign(ajn_12);
        auW2.setXOffset(n3);
        auW2.setYOffset(n4);
        aod_22.a(auW2);
        aod_22.a(rt_0.fN("dialog.next"), (ov_1)new ir_2(this, nArray, aod_22, stringArray, string2, jX, jJArray2, n6), true);
        aod_22.setCloseOnClick(false);
        aod_22.setForcedDisplaySpark(false);
        aod_22.setUseTargetPositionning(false);
        aod_22.show();
        this.id(n6);
    }

    /* synthetic */ ajx_2(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

