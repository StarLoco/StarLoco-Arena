/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ahN
 */
class ahn_1
extends uc_1 {
    public ahn_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setShortcutEnabled";
    }

    public LX[] Q() {
        return new LX[]{new LX("enabled", aos_1.elV, false), new LX("groupName", aos_1.elS, true), new LX("name", aos_1.elS, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string;
        boolean bl2 = this.ic(0);
        String string2 = n2 >= 2 ? this.hZ(1) : null;
        String string3 = string = n2 >= 3 ? this.hZ(2) : null;
        if (n2 == 1) {
            hc_2.kI().co(bl2);
        } else if (n2 == 2) {
            hc_2.kI().k(string2, bl2);
        } else if (n2 == 3) {
            hc_2.kI().b(string2, string, bl2);
        }
    }
}

