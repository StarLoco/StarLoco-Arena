/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class ahI
extends uc_1 {
    private static final Logger a = Logger.getLogger(ahI.class);

    public ahI(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setZoomSpeedFactor";
    }

    public LX[] Q() {
        return new LX[]{new LX("speed", aos_1.elU, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            qs_22.vn().aa((float)this.hX(0));
        } else {
            this.a(a, "pas de camera associ\u00e9e \u00e0 CameraFunctionsLibrary");
        }
    }
}

