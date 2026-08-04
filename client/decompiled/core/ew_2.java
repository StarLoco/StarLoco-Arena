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
 * Renamed from eW
 */
public class ew_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(ew_2.class);

    public ew_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileAnimation";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("animationName", aos_1.elS, false), new LX("func", aos_1.elS, true), new LX("params", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        String string = this.hZ(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.aY(string);
            mT2.aTt();
            if (n2 > 2) {
                JX jX = this.agC();
                String string2 = this.hZ(2);
                jJ[] jJArray = this.aX(3, n2);
                boolean bl2 = jX.a(string2, jJArray);
                mT2.a(new vw_1(this, mT2, bl2, jX, string2, jJArray));
            }
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

