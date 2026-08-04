/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.Map;
import org.keplerproject.luajava.LuaState;

class pc
extends uc_1 {
    private static final String abe = "anm/";
    static final /* synthetic */ boolean bb;
    final /* synthetic */ agk abf;

    public pc(agk agk2, LuaState luaState) {
        this.abf = agk2;
        super(luaState);
    }

    public String getName() {
        return "runScript";
    }

    public LX[] Q() {
        return new LX[]{new LX("scriptId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        ano_0 ano_02 = new ano_0(1);
        ano_02.put("fightId", (Object)this.agC().Gf());
        Ky.WG().a(this.av(l2), this.abf.he.aEY(), (Map)ano_02, null, false);
    }

    private String av(long l2) {
        if (!bb && Ky.WG().getPath() == null) {
            throw new AssertionError();
        }
        return String.format("%s%d%s", abe, l2, Ky.WG().getExtension());
    }

    static {
        bb = !ahh_1.class.desiredAssertionStatus();
    }
}

