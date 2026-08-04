/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class jm
extends uc_1 {
    private static final Logger a = Logger.getLogger(jm.class);

    public jm(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getAnimationDuration";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("animationName", aos_1.elS, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("time", aos_1.elT, false)};
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null) {
            this.a(a, "le mobile " + l2 + "n'existe pas");
            this.agB();
            return;
        }
        String string = this.hZ(1);
        int n3 = mT2.an(string);
        if (n3 == -1) {
            a.warn((Object)("animation (" + string + ") qui boucle"));
            n3 = 0;
        }
        this.id(n3);
    }
}

