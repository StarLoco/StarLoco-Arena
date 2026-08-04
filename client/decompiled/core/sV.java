/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class sV
extends uc_1 {
    private static final Logger a = Logger.getLogger(sV.class);

    public sV(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setCustomWalkStyle";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("animationName", aos_1.elS, true), new LX("cellSpeed", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null || !(mT2 instanceof abm_2)) {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
            return;
        }
        abm_2 abm_22 = (abm_2)mT2;
        if (n2 == 1) {
            abm_22.Pv();
        } else {
            abm_22.a(true, awm_0.dhJ);
            ap_2 ap_22 = (ap_2)abm_22.Pr();
            ap_22.setAnimation(this.hZ(1));
            if (n2 == 3) {
                ap_22.t(this.hW(2));
            }
        }
    }
}

