/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Is
 */
class is_2
extends uc_1 {
    final /* synthetic */ apM oR;

    public is_2(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "addColorTween";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false), new LX("widgetId", aos_1.elS, false), new LX("duration", aos_1.elU, false), new LX("repeat", aos_1.elU, false), new LX("r1", aos_1.elU, true), new LX("g1", aos_1.elU, true), new LX("b1", aos_1.elU, true), new LX("a1", aos_1.elU, true), new LX("r2", aos_1.elU, true), new LX("g2", aos_1.elU, true), new LX("b2", aos_1.elU, true), new LX("a2", aos_1.elU, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        vP vP2;
        vP vP3;
        String string = this.hZ(0);
        aji_1 aji_12 = add_1.aOG().azj().lh(string);
        if (aji_12 == null) {
            this.a(a, "Dialogue inconnu " + string);
            return;
        }
        String string2 = this.hZ(1);
        na_1 na_12 = aji_12.R(string2);
        if (na_12 == null) {
            this.a(a, "EventDispatcher inconnu " + string2 + " dans le dialog " + string);
            return;
        }
        if (!(na_12 instanceof adg_2)) {
            this.a(a, "l'EventDispatcher n'est pas du type Widget");
            return;
        }
        int n3 = this.hW(2);
        int n4 = this.hW(3);
        if (n2 == 12) {
            vP3 = new vP((float)this.hX(4), (float)this.hX(5), (float)this.hX(6), (float)this.hX(7));
            vP2 = new vP((float)this.hX(8), (float)this.hX(9), (float)this.hX(10), (float)this.hX(11));
        } else {
            vP3 = vP.atL;
            vP2 = vP.atT;
        }
        Zb zb = ((adg_2)na_12).getAppearance();
        zb.a(new kD(vP3, vP2, zb, 0, n3, n4, ys.aCq));
    }
}

