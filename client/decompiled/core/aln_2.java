/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aln
 */
public class aln_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(aln_2.class);
    private static final boolean DEBUG = true;

    public aln_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "attachCameraToMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("funcName", aos_1.elS, true), new LX("funcParams", aos_1.elX, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            qs_2 qs_22 = asO.aFM().YP();
            if (qs_22 != null) {
                YR yR = qs_22.vn();
                yR.c(mT2);
                if (n2 > 1) {
                    JX jX = this.agC();
                    String string = this.hZ(1);
                    jJ[] jJArray = this.aX(2, n2);
                    yR.a(new vh(this, yR, jX, string, jJArray));
                }
            } else {
                this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
            }
        } else {
            this.a(a, "mobile inconnu " + l2);
        }
    }
}

