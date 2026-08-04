/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from AA
 */
public class aa_1
extends uc_1 {
    final /* synthetic */ bh_1 zq;

    public aa_1(bh_1 bh_12, LuaState luaState) {
        this.zq = bh_12;
        super(luaState);
    }

    public final String getName() {
        return "playRandomSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("stopOnAnimationChange", aos_1.elV, false), new LX("soundId, gain", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!this.zq.he.dLe) {
            return;
        }
        if (!aau_0.apB().apx()) {
            return;
        }
        if (n2 % 2 != 1) {
            return;
        }
        boolean bl2 = this.ic(0);
        int n3 = ej_0.n(0, (n2 - 1) / 2);
        long l2 = this.hY(2 * n3 + 1);
        int n4 = this.hW(2 * n3 + 2);
        if (!this.zq.he.dLk.dl(l2)) {
            return;
        }
        try {
            if (l2 != 0L) {
                xt_0 xt_02 = aau_0.apB().a(l2, (float)n4 / 100.0f, 1, -1L, -1L, this.agC().Gf());
                if (bl2 && xt_02 != null) {
                    this.zq.he.dLj.o(l2, xt_02.alE());
                }
            } else {
                a.debug((Object)"Id du son nul");
            }
        }
        catch (Exception exception) {
            a.debug((Object)("soundExtension or soundPath not initialized " + exception));
        }
    }
}

