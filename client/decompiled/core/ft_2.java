/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Ft
 */
class ft_2
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private ft_2(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "showMonologue";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("texts", aos_1.elW, false), new LX("xOffset", aos_1.elT, true), new LX("yOffset", aos_1.elT, true), new LX("funcOnTerminate", aos_1.elS, true), new LX("funcParams", aos_1.elX, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        mT mT2 = bd_1.Is().bb(this.hY(0));
        if (mT2 == null) {
            this.a(a, "Le mobile n'existe pas " + this.hY(0));
            this.id(0);
            return;
        }
        jJ[] jJArray = this.ia(1);
        if (jJArray.length == 0) {
            this.a(a, "La table de texte est vide");
        }
        String[] stringArray = new String[jJArray.length];
        for (int j = 0; j < jJArray.length; ++j) {
            stringArray[j] = rt_0.fN((String)jJArray[j].getValue());
        }
        JX jX = this.agC();
        int n3 = wj_2.Df().Dg();
        aod_2 aod_22 = (aod_2)add_1.aOG().a("interactiveBubbleDialog" + n3, oh_2.bq("interactiveBubbleDialog"), 64L, (short)30001);
        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
        aod_22.setVisible(false);
        aod_22.setTarget(mT2);
        Integer n4 = n2 > 2 ? this.hW(2) : 0;
        Integer n5 = n2 > 3 ? this.hW(3) : 0;
        aod_22.setOffset(n4, n5);
        rt_0.b(this.nt).c(n3, aod_22);
        int[] nArray = new int[]{0};
        aod_22.setBubbleText(stringArray[0]);
        aod_22.setActAsButton(true);
        String string = n2 > 4 ? this.hZ(4) : null;
        jJ[] jJArray2 = this.aX(5, n2);
        aod_22.a(rt_0.fN("dialog.next"), (ov_1)new ti_2(this, nArray, aod_22, stringArray, string, jX, jJArray2, n3), true);
        aod_22.setCloseOnClick(false);
        aod_22.show();
        this.id(n3);
    }

    /* synthetic */ ft_2(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

