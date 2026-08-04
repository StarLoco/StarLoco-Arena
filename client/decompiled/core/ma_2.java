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
 * Renamed from mA
 */
public class ma_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(ma_2.class);

    public ma_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setWorldTarget";
    }

    public LX[] Q() {
        return new LX[]{new LX("coordX", aos_1.elU, false), new LX("coordY", aos_1.elU, false), new LX("altitude", aos_1.elU, false), new LX("funcName", aos_1.elS, true), new LX("funcParams", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        double d = this.hX(0);
        double d2 = this.hX(1);
        double d3 = this.hX(2);
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            YR yR = qs_22.vn();
            yR.c(new et_0(d, d2, d3));
            if (n2 > 3) {
                JX jX = this.agC();
                String string = this.hZ(3);
                jJ[] jJArray = this.aX(4, n2);
                yR.a(new aJr(this, yR, jX, string, jJArray));
            }
        } else {
            this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
        }
    }
}

