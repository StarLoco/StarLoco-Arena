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
 * Renamed from KV
 */
public class kv_0
extends uc_1 {
    private static final Logger a = Logger.getLogger(kv_0.class);

    public kv_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getZoomFactor";
    }

    public LX[] Q() {
        return null;
    }

    public final LX[] R() {
        return new LX[]{new LX("zoomValue", aos_1.elU, false)};
    }

    public void c(int n2) {
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            double d = qs_22.Ft();
            this.t(d);
        } else {
            this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
            this.agB();
        }
    }
}

