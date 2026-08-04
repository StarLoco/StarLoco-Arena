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
 * Renamed from uj
 */
public class uj_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(uj_1.class);

    public uj_1(LuaState luaState) {
        super(luaState);
    }

    public String getDescription() {
        return "Zoom sur un point pendant un certain temps puis revient \u00e0 sa position d'origine";
    }

    public String getName() {
        return "zoomOn";
    }

    public LX[] Q() {
        return new LX[]{new LX("zoomFactor", aos_1.elU, false), new LX("time", aos_1.elT, false), new LX("x", aos_1.elU, false), new LX("y", aos_1.elU, false), new LX("z", aos_1.elU, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        double d = this.hX(0);
        int n3 = this.hW(1);
        double d2 = this.hX(2);
        double d3 = this.hX(3);
        double d4 = this.hX(4);
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            YR yR = qs_22.vn();
            Du du = yR.Fx();
            double d5 = du.getWorldX();
            double d6 = du.getWorldY();
            double d7 = du.getAltitude();
            double d8 = yR.oZ();
            yR.c(new et_0(d2, d3, d4));
            qs_22.k((float)d);
            JX jX = this.agC();
            if (du instanceof mT) {
                long l2 = ((mT)du).getId();
                jJ[] jJArray = new jJ[]{new jJ(l2)};
                jX.a(n3, 1, "attachCameraToMobile", jJArray);
                jJ[] jJArray2 = new jJ[]{new jJ(d8)};
                jX.a(n3, 1, "setZoomFactor", jJArray2);
            } else {
                jJ[] jJArray = new jJ[]{new jJ(d8), new jJ(d5), new jJ(d6), new jJ(d7)};
                jX.a(n3, 1, "SetCamera", jJArray);
            }
        } else {
            this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
        }
    }
}

