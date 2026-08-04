/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class OL
extends uc_1 {
    final /* synthetic */ bh_1 zq;

    public OL(bh_1 bh_12, LuaState luaState) {
        this.zq = bh_12;
        super(luaState);
    }

    public final String getName() {
        return "playBark";
    }

    public final LX[] Q() {
        return new LX[]{new LX("barkId", aos_1.elT, false), new LX("gain", aos_1.elT, true), new LX("breedId", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!this.zq.he.dLe) {
            return;
        }
        int n3 = this.hW(0);
        int n4 = 100;
        int n5 = -1;
        if (n2 > 1) {
            n4 = this.hW(1);
        }
        if (n2 > 2) {
            n5 = this.hW(2);
        }
        try {
            if (n3 != 0) {
                ns_0 ns_02 = aau_0.apB().a(n3, this.zq.he, n5);
                if (ns_02 == null) {
                    a.debug((Object)"Impossible de trouver de BarkData ad\u00e9quat");
                    return;
                }
                if (!this.zq.he.dLk.dl(ns_02.sq())) {
                    return;
                }
                aau_0.apB().a(ns_02.sq(), ns_02.getGain() * (float)n4 / 100.0f, 1, -1L, -1L, this.agC().Gf(), this.zq.he, ns_02.sr());
            } else {
                a.debug((Object)"Id du son nul");
            }
        }
        catch (Exception exception) {
            a.debug((Object)("soundExtension or soundPath not initialized " + exception));
        }
    }
}

