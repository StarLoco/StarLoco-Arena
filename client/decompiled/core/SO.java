/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import org.keplerproject.luajava.LuaState;

class SO
extends uc_1 {
    final /* synthetic */ apM oR;

    public SO(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "displayWideScreenBand";
    }

    public LX[] Q() {
        return new LX[]{new LX("show/hide", aos_1.elV, false), new LX("percentOfScreen", aos_1.elT, true), new LX("duration", aos_1.elT, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        xu_2 xu_22 = (xu_2)DofusArenaClientInstance.yl().YP();
        xu_22.ex(this.ic(0));
        if (n2 > 1) {
            xu_22.bp(this.hW(1));
            if (n2 > 2) {
                xu_22.br(1000.0f / (float)this.hW(2));
            }
        }
    }
}

