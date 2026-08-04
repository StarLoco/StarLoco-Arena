/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class azy
extends uc_1 {
    private static final Logger a = Logger.getLogger(azy.class);

    public azy(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setPartVisible";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("visible", aos_1.elV, false), new LX("partNames", aos_1.elX, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null) {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
            return;
        }
        boolean bl2 = this.ic(1);
        String[] stringArray = new String[n2 - 2];
        for (int j = 2; j < n2; ++j) {
            stringArray[j - 2] = this.hZ(j);
        }
        mT2.a(stringArray, bl2);
    }
}

