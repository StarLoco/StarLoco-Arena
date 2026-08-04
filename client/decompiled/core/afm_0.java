/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from afm
 */
class afm_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public afm_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "clickButton";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false), new LX("id", aos_1.elS, false), new LX("button", aos_1.elU, true), new LX("clickCount", aos_1.elU, true)};
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
        if (na_12 != null && na_12 instanceof aqq_0) {
            if (n2 > 2) {
                int n3;
                switch (this.hW(2)) {
                    case 2: {
                        n3 = 2;
                        break;
                    }
                    case 3: {
                        n3 = 3;
                        break;
                    }
                    default: {
                        n3 = 1;
                    }
                }
                if (n2 == 4) {
                    ((aqq_0)na_12).L(n3, this.hW(3), 0);
                } else {
                    ((aqq_0)na_12).L(n3, 1, 0);
                }
            } else {
                ((aqq_0)na_12).aDY();
            }
        }
    }
}

