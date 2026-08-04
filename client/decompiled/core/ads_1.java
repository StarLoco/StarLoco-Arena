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
 * Renamed from aDS
 */
public class ads_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(aAF.class);

    public ads_1(LuaState luaState) {
        super(luaState);
    }

    public final String getName() {
        return "loopSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("soundFileId", aos_1.elR, false), new LX("isStereo", aos_1.elV, true), new LX("gainMod", aos_1.elU, true), new LX("loopingTime", aos_1.elU, true), new LX("fadeOut time", aos_1.elU, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        long l2 = this.hY(0);
        int n3 = 0;
        boolean bl2 = n2 >= 2 && this.ic(1);
        float f = n2 >= 3 ? (float)this.hX(2) : 100.0f;
        if (n2 >= 4) {
            n3 = this.hW(3);
        }
        long l3 = n2 >= 5 ? (long)this.hW(4) : 0L;
        try {
            if (l2 != 0L) {
                boolean bl3 = n3 > 0;
                long l4 = bl3 ? System.currentTimeMillis() + (long)n3 : -1L;
                aau_0.apB().a(l2, f / 100.0f, bl3 ? 0 : 1, l4, l3, this.agC().Gf());
            } else {
                a.error((Object)"Id du son nul");
            }
        }
        catch (Exception exception) {
            this.a(a, "soundExtension or soundPath not initialized");
        }
    }
}

