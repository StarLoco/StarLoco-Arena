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
 * Renamed from aMD
 */
public class amd_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(amd_2.class);
    private static final boolean DEBUG = true;

    public amd_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "onPathEnded";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            if (mT2 instanceof abm_2) {
                abm_2 abm_22 = (abm_2)mT2;
                JX jX = this.agC();
                String string = this.hZ(1);
                jJ[] jJArray = this.aX(2, n2);
                boolean bl2 = jX.a(string, jJArray);
                em_2 em_22 = new em_2(this, bl2, jX, string, jJArray);
                abm_22.a(em_22);
            }
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

