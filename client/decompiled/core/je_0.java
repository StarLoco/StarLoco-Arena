/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from je
 */
public class je_0
extends uc_1 {
    final /* synthetic */ bh_1 zq;

    public je_0(bh_1 bh_12, LuaState luaState) {
        this.zq = bh_12;
        super(luaState);
    }

    public final String getName() {
        return "playLocalSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("rollOffPresetId", aos_1.elT, false), new LX("stopOnAnimationChange", aos_1.elV, false), new LX("soundFileId", aos_1.elR, false), new LX("gainModification", aos_1.elU, true), new LX("playCount", aos_1.elU, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!this.zq.he.dLe) {
            return;
        }
        int n3 = this.hW(0);
        boolean bl2 = this.ic(1);
        long l2 = this.hY(2);
        if (!this.zq.he.dLk.dl(l2)) {
            return;
        }
        float f = n2 >= 4 ? (float)this.hX(3) : 100.0f;
        int n4 = n2 >= 5 ? this.hW(4) : 1;
        try {
            if (l2 != 0L) {
                xt_0 xt_02 = aau_0.apB().a(l2, f / 100.0f, n4, -1L, -1L, this.agC().Gf(), this.zq.he, n3);
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

