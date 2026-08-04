/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from alx
 */
class alx_1
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private alx_1(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "question";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elT, false), new LX("text1", aos_1.elS, false), new LX("texts", aos_1.elX, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        mT mT2 = bd_1.Is().bb(this.hW(0));
        if (mT2 == null) {
            a.error((Object)("Le mobile n'existe pas " + this.hW(0)));
            this.agB();
            return;
        }
        int n3 = wj_2.Df().Dg();
        aod_2 aod_22 = (aod_2)add_1.aOG().a("interactiveBubbleDialog" + n3, oh_2.bq("interactiveBubbleDialog"), 64L, (short)30001);
        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
        aod_22.setVisible(false);
        aod_22.setTarget(mT2);
        rt_0.b(this.nt).c(n3, aod_22);
        JX jX = this.agC();
        String[] stringArray = new String[n2 - 1];
        for (int j = 0; j < n2 - 1; ++j) {
            stringArray[j] = rt_0.fN(this.hZ(j + 1));
        }
        int[] nArray = new int[]{0};
        aod_22.setBubbleText(stringArray[0]);
        aod_22.a(rt_0.fN("dialog.next"), (ov_1)new adi_1(this, nArray, aod_22, stringArray, n3), true);
        aod_22.show();
        this.id(n3);
    }
}

