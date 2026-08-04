/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Qd
 */
class qd_2
extends uc_1 {
    final /* synthetic */ agk abf;

    public qd_2(agk agk2, LuaState luaState) {
        this.abf = agk2;
        super(luaState);
    }

    public String getName() {
        return "gotoRandomAnimation";
    }

    public LX[] Q() {
        return new LX[]{new LX("animationNames", aos_1.elX, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        if (n2 <= 1) {
            return;
        }
        String[] stringArray = new String[n2];
        byte[] byArray = new byte[n2];
        if (!this.a(n2, stringArray, byArray)) {
            return;
        }
        int n3 = ej_0.am(100);
        for (int j = 0; j < n2; ++j) {
            if (byArray[j] < n3 || stringArray[j] == null) continue;
            if (stringArray[j].length() == 0) break;
            this.abf.he.aY(stringArray[j]);
            break;
        }
    }

    private boolean a(int n2, String[] stringArray, byte[] byArray) {
        int n3;
        boolean bl2 = false;
        int n4 = 0;
        int n5 = 0;
        for (n3 = 0; n3 < n2; ++n3) {
            String string = this.ib(n3);
            try {
                byte by = Byte.parseByte(string);
                if (!bl2) {
                    a.error((Object)"deux pourcentage se suivent ");
                    return false;
                }
                if (by <= 0 || by >= 100 - n2 / 2) {
                    a.error((Object)("pourcentage incorrect " + by));
                    return false;
                }
                byArray[n3 - 1] = by;
                n4 += by;
                --n5;
                bl2 = false;
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                ++n5;
                stringArray[n3] = string;
                bl2 = true;
            }
        }
        n3 = (byte)((100 - n4) / n5);
        n4 = 0;
        for (int j = 0; j < n2 - 1; ++j) {
            if (stringArray[j] == null) continue;
            if (byArray[j] == 0) {
                byArray[j] = n3;
            }
            int n6 = j;
            byArray[n6] = (byte)(byArray[n6] + n4);
            n4 = byArray[j];
        }
        byArray[n2 - 1] = 101;
        return true;
    }
}

