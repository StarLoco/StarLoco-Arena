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
 * Renamed from afd
 */
public class afd_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(afd_1.class);

    public afd_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setUserZoomLocked";
    }

    public LX[] Q() {
        return new LX[]{new LX("zoomLocked", aos_1.elV, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 == null) {
            this.a(a, "pas de scene associ\u00e9 \u00e0 CameraFunctionsLibrary");
            return;
        }
        YR yR = qs_22.vn();
        if (yR == null) {
            this.a(a, "pas de camera associ\u00e9 \u00e0 la scene");
            return;
        }
        yR.aW(this.ic(0));
    }
}

