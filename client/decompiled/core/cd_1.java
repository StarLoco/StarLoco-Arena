/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Cd
 */
class cd_1
extends uc_1 {
    final /* synthetic */ apM oR;

    public cd_1(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "enableClick";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false), new LX("id", aos_1.elS, false), new LX("enable", aos_1.elV, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        aji_1 aji_12 = add_1.aOG().azj().lh(this.hZ(0));
        if (aji_12 == null) {
            return;
        }
        na_1 na_12 = aji_12.R(this.hZ(1));
        if (na_12 != null) {
            if (na_12 instanceof ahr_2) {
                ((ahr_2)na_12).setClickEnabled(this.ic(2));
            } else {
                this.a(apM.Dm(), this.hZ(0) + "." + this.hZ(1) + " n'est pas du bon type !");
            }
        } else {
            this.a(apM.Dm(), this.hZ(0) + "." + this.hZ(1) + " est introuvable !");
        }
    }
}

