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
 * Renamed from acU
 */
public class acu_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(acu_2.class);

    public acu_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getWorldTarget";
    }

    public LX[] Q() {
        return null;
    }

    public final LX[] R() {
        return new LX[]{new LX("posX", aos_1.elU, false), new LX("posY", aos_1.elU, false), new LX("posZ", aos_1.elU, false)};
    }

    public void c(int n2) {
        qs_2 qs_22 = asO.aFM().YP();
        if (qs_22 != null) {
            YR yR = qs_22.vn();
            double d = yR.oV();
            double d2 = yR.oW();
            double d3 = yR.getAltitude();
            this.t(d);
            this.t(d2);
            this.t(d3);
        } else {
            this.a(a, "Pas de scene associ\u00e9 \u00e0 CameraFunctionsLibrary");
            this.agB();
            this.agB();
            this.agB();
        }
    }
}

