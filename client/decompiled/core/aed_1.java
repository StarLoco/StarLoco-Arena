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
 * Renamed from aeD
 */
public class aed_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(aed_1.class);
    private static final boolean DEBUG = true;

    public aed_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "onCellTransition";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("x", aos_1.elT, false), new LX("y", aos_1.elT, false), new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
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
                int n3 = this.hW(1);
                int n4 = this.hW(2);
                String string = this.hZ(3);
                jJ[] jJArray = this.aX(4, n2);
                boolean bl2 = jX.a(string, jJArray);
                cq cq2 = new cq(this, n3, n4, mT2, bl2, jX, string, jJArray);
                abm_22.a(cq2);
            }
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

