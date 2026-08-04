/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class rb
extends uc_1 {
    private static final Logger a = Logger.getLogger(rb.class);

    public rb(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getDistanceBetweenMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileIdA", aos_1.elR, false), new LX("mobileIdB", aos_1.elR, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("distance", aos_1.elT, false)};
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        long l3 = this.hY(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null) {
            this.a(a, "le mobile " + l2 + "n'existe pas");
            this.agB();
            return;
        }
        mT mT3 = bd_1.Is().bb(l3);
        if (mT3 == null) {
            this.a(a, "le mobile " + l3 + "n'existe pas");
            this.agB();
            return;
        }
        this.id(amd.c((aGf)mT2, mT3));
    }
}

