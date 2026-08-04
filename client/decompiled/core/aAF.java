/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class aAF
extends uc_1 {
    private static final Logger a = Logger.getLogger(aAF.class);

    public aAF(LuaState luaState) {
        super(luaState);
    }

    public final String getName() {
        return "playSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("soundFileId", aos_1.elR, false), new LX("isStereo", aos_1.elV, true), new LX("gainModification", aos_1.elU, true), new LX("playCount", aos_1.elU, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!aau_0.apB().apx()) {
            return;
        }
        long l2 = this.hY(0);
        boolean bl2 = n2 >= 2 && this.ic(1);
        float f = n2 >= 3 ? (float)this.hX(2) : 100.0f;
        int n3 = n2 >= 4 ? this.hW(3) : 1;
        try {
            if (l2 != 0L) {
                aau_0.apB().a(l2, f / 100.0f, n3, -1L, -1L, this.agC().Gf());
            } else {
                a.error((Object)"Id du son nul");
            }
        }
        catch (Exception exception) {
            this.a(a, "soundExtension or soundPath not initialized " + exception);
        }
    }
}

