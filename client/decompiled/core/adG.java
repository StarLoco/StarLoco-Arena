/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class adG
extends uc_1 {
    private static final Logger a = Logger.getLogger(adG.class);

    public adG(LuaState luaState) {
        super(luaState);
    }

    public String getDescription() {
        return "Positionne la cam\u00e9ra sur un point";
    }

    public String getName() {
        return "setCamera";
    }

    public LX[] Q() {
        return new LX[]{new LX("zoomFactor", aos_1.elU, false), new LX("x", aos_1.elU, false), new LX("y", aos_1.elU, false), new LX("z", aos_1.elU, false), new LX("funcName", aos_1.elS, true), new LX("funcParams", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        double d = this.hX(0);
        double d2 = this.hX(1);
        double d3 = this.hX(2);
        double d4 = this.hX(3);
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            YR yR = qs_22.vn();
            yR.c(new et_0(d2, d3, d4));
            yR.k((float)d);
            if (n2 > 4) {
                JX jX = this.agC();
                String string = this.hZ(4);
                jJ[] jJArray = this.aX(5, n2);
                yR.a(new aBg(this, yR, jX, string, jJArray));
            }
        } else {
            this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
        }
    }
}

