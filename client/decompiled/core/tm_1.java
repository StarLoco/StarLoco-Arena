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
 * Renamed from tM
 */
public class tm_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(tm_1.class);
    private static final boolean DEBUG = true;

    public tm_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "play3DSound";
    }

    public LX[] Q() {
        return new LX[]{new LX("soundFileId", aos_1.elR, false), new LX("posX", aos_1.elT, false), new LX("posY", aos_1.elT, false), new LX("posZ", aos_1.elT, false), new LX("isLoop", aos_1.elV, true), new LX("isStereo", aos_1.elV, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        int n4 = this.hW(2);
        int n5 = this.hW(3);
        boolean bl2 = n2 >= 5 && this.ic(4);
        boolean bl3 = n2 >= 6 && this.ic(5);
        try {
            if (l2 > 0L) {
                aau_0.apB().a(l2, 1.0f, bl2 ? 0 : 1, -1L, -1L, this.agC().Gf());
            } else {
                a.warn((Object)"Pas de son sp\u00e9cifi\u00e9 (ID<=0)");
            }
        }
        catch (Exception exception) {
            this.a(a, "soundExtension or soundPath not initialized");
        }
    }
}

