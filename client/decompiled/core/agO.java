/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class agO
extends uc_1 {
    final /* synthetic */ bh_1 zq;

    public agO(bh_1 bh_12, LuaState luaState) {
        this.zq = bh_12;
        super(luaState);
    }

    public final String getName() {
        return "playLocalRandomSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("rollOffPresetId", aos_1.elT, false), new LX("stopOnAnimationChange", aos_1.elV, false), new LX("soundId, gain", aos_1.elX, true)};
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
        int n3 = this.hW(0);
        boolean bl2 = this.ic(1);
        if (n2 % 2 != 0) {
            return;
        }
        int n4 = ej_0.n(0, (n2 - 2) / 2);
        long l2 = this.hY(2 * n4 + 2);
        int n5 = this.hW(2 * n4 + 3);
        if (!this.zq.he.dLk.dl(l2)) {
            return;
        }
        try {
            if (l2 != 0L) {
                xt_0 xt_02 = aau_0.apB().a(l2, (float)n5 / 100.0f, 1, -1L, -1L, this.agC().Gf(), this.zq.he, n3);
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

