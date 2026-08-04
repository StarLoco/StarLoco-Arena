/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class adh
extends uc_1 {
    final /* synthetic */ bh_1 zq;

    public adh(bh_1 bh_12, LuaState luaState) {
        this.zq = bh_12;
        super(luaState);
    }

    public final String getName() {
        return "playSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("soundFileId", aos_1.elR, false), new LX("stopOnAnimationChange", aos_1.elV, false), new LX("gainModification", aos_1.elU, true), new LX("playCount", aos_1.elU, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!this.zq.he.dLe) {
            return;
        }
        long l2 = this.hY(0);
        if (!this.zq.he.dLk.dl(l2)) {
            return;
        }
        boolean bl2 = this.ic(1);
        float f = n2 >= 3 ? (float)this.hX(2) : 100.0f;
        int n3 = n2 >= 4 ? this.hW(3) : 1;
        try {
            if (l2 != 0L) {
                xt_0 xt_02 = aau_0.apB().a(l2, f / 100.0f, n3, -1L, -1L, this.agC().Gf());
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

