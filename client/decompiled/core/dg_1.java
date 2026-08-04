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
 * Renamed from dG
 */
public class dg_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(dg_1.class);

    public dg_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setZoomFactor";
    }

    public LX[] Q() {
        return new LX[]{new LX("zoomValue", aos_1.elU, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        double d = this.hX(0);
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            qs_22.k((float)d);
        } else {
            this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
        }
    }
}

