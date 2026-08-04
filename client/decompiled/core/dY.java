/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class dY
extends uc_1 {
    private static final Logger a = Logger.getLogger(dY.class);

    public dY(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "generateClientMobileId";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public void c(int n2) {
        this.da(uq_1.ahR());
    }
}

