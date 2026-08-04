/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Wg
 */
class wg_1
extends uc_1 {
    private wg_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "changeGamePreference";
    }

    public LX[] Q() {
        return new LX[]{new LX("option", aos_1.elS, false), new LX("value", aos_1.elV, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        boolean bl2 = this.ic(1);
        adc_0 adc_02 = null;
        for (adc_0 adc_03 : adc_0.values()) {
            if (!adc_03.getKey().equals(string)) continue;
            adc_02 = adc_03;
            break;
        }
        DofusArenaClientInstance.yl().aod().a(adc_02, bl2);
    }

    /* synthetic */ wg_1(LuaState luaState, apd_1 apd_12) {
        this(luaState);
    }
}

