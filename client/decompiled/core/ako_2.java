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
 * Renamed from aKO
 */
public class ako_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(ako_2.class);
    private static final boolean DEBUG = true;

    public ako_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "playLocalSound";
    }

    public LX[] Q() {
        return new LX[]{new LX("rollOff", aos_1.elT, false), new LX("soundFileId", aos_1.elR, false), new LX("gain", aos_1.elR, false), new LX("posX", aos_1.elT, false), new LX("posY", aos_1.elT, false), new LX("posZ", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        long l2 = this.hY(1);
        int n4 = this.hW(2);
        int n5 = this.hW(3);
        int n6 = this.hW(4);
        int n7 = this.hW(5);
        try {
            if (l2 > 0L) {
                aau_0.apB().a(l2, (float)n4 / 100.0f, 1, -1L, -1L, -1, new ty_0(n5 - n6, -(n5 + n6), n7, true, 0), n3);
            } else {
                a.warn((Object)"Pas de son sp\u00e9cifi\u00e9 (ID<=0)");
            }
        }
        catch (Exception exception) {
            this.a(a, "soundExtension or soundPath not initialized");
        }
    }
}

