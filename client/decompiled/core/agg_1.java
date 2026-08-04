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
 * Renamed from aGG
 */
public class agg_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(aAF.class);

    public agg_1(LuaState luaState) {
        super(luaState);
    }

    public final String getName() {
        return "playRandomSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("soundId, gain", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!aau_0.apB().apx()) {
            return;
        }
        if (n2 % 2 != 0) {
            return;
        }
        int n3 = ej_0.n(0, n2 / 2);
        long l2 = this.hY(2 * n3);
        int n4 = this.hW(2 * n3 + 1);
        try {
            if (l2 != 0L) {
                aau_0.apB().a(l2, (float)n4 / 100.0f, 1, -1L, -1L, this.agC().Gf());
            } else {
                a.error((Object)"Id du son nul");
            }
        }
        catch (Exception exception) {
            this.a(a, "soundExtension or soundPath not initialized " + exception);
        }
    }
}

