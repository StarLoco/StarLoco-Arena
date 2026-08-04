/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class aDl
extends uc_1 {
    final /* synthetic */ apM oR;

    public aDl(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "addParticle";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false), new LX("widgetId", aos_1.elS, false), new LX("particleFileName", aos_1.elS, false), new LX("posX", aos_1.elU, false), new LX("posY", aos_1.elU, false), new LX("followBorder", aos_1.elV, false), new LX("alignment", aos_1.elS, true), new LX("level", aos_1.elU, true)};
    }

    public final LX[] R() {
        return new LX[]{new LX("particleId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        aji_1 aji_12 = add_1.aOG().azj().lh(string);
        if (aji_12 == null) {
            this.a(a, "Dialogue inconnu " + string);
            this.agB();
            return;
        }
        String string2 = this.hZ(1);
        na_1 na_12 = aji_12.R(string2);
        if (na_12 == null) {
            this.a(a, "ElementDispatcher inconnu " + string2 + " dans le dialog " + string);
            this.agB();
            return;
        }
        if (!(na_12 instanceof adg_2)) {
            this.a(a, "le widget n'est pas du type Widget");
            this.agB();
            return;
        }
        String string3 = this.hZ(2);
        ob_1 ob_12 = new ob_1();
        int n3 = apM.aDJ();
        ob_12.b();
        ob_12.setFile(string3);
        ob_12.setX(this.hW(3));
        ob_12.setY(this.hW(4));
        ob_12.setFollowBorders(this.ic(5));
        BT bT = BT.aJX;
        int n4 = fh_1.rK;
        if (n2 > 6) {
            if (this.getParam(8).isString()) {
                bT = BT.valueOf(this.hZ(6));
            } else if (this.getParam(8).isNumber()) {
                n4 = this.hW(6);
            }
        }
        if (n2 > 7 && this.getParam(9).isNumber()) {
            n4 = this.hW(7);
        }
        if (n4 != fh_1.rK) {
            ob_12.setLevel(n4);
        }
        ob_12.setAlignment(bT);
        a.info((Object)("Ajout de la particule d'id : " + n3));
        ((adg_2)na_12).getAppearance().a(ob_12);
        apM.a(this.oR).c(n3, ob_12);
        this.id(n3);
    }
}

