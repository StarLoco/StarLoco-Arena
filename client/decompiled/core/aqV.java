/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class aqV
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    public aqV(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "addButton";
    }

    public LX[] Q() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false), new LX("text", aos_1.elS, false), new LX("function", aos_1.elS, false), new LX("parameters", aos_1.elX, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        String string = rt_0.fN(this.hZ(1));
        String string2 = this.hZ(2);
        jJ[] jJArray = this.aX(3, n2);
        JX jX = this.agC();
        ov_1 ov_12 = (ov_1)aMi.aWT().a(jX, "interactiveBubbleDialog" + n3, string, "MOUSE_CLICKED", string2);
        if (ov_12 == null) {
            ov_12 = new iN(jX, string2, jJArray);
            aMi.aWT().a(jX, "interactiveBubbleDialog" + n3, string, "MOUSE_CLICKED", string2, ov_12);
            aod_2 aod_22 = (aod_2)rt_0.b(this.nt).get(n3);
            if (aod_22 != null) {
                aod_22.a(string, ov_12, true);
            } else {
                this.a(a, "id de bulle correspondant \u00e0 rien");
            }
        } else {
            ((iN)ov_12).a(jJArray);
        }
    }
}

