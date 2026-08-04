/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from abx
 */
class abx_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public abx_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "removeParticle";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleId", aos_1.elT, false), new LX("time", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        ob_1 ob_12 = (ob_1)apM.a(this.oR).remove(n3);
        a.info((Object)("Suppression de la particule d'id : " + n3));
        if (ob_12 != null) {
            try {
                ob_12.setTimeToLive(n2 == 2 ? this.hW(1) : 100);
            }
            catch (Exception exception) {
                a.error((Object)("Exception lev\u00e9e dans le removeParticle(" + n3 + ")"), (Throwable)exception);
            }
        } else {
            a.warn((Object)("Impossible de trouver la particle " + n3 + " pour la supprimer"));
        }
    }
}

