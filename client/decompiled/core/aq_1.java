/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from AQ
 */
class aq_1
extends uc_1 {
    final /* synthetic */ aja pd;

    private aq_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "invoke";
    }

    public final LX[] Q() {
        return new LX[]{new LX("time", aos_1.elT, false), new LX("loopCount", aos_1.elT, false), new LX("funcName", aos_1.elS, false), new LX("funcParams", aos_1.elX, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("taskId", aos_1.elT, false)};
    }

    public final void c(int n2) {
        JX jX = this.agC();
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        String string = this.hZ(2);
        jJ[] jJArray = this.aX(3, n2);
        dN dN2 = jX.a(n3, n4, string, jJArray);
        this.id(dN2.getId());
    }

    /* synthetic */ aq_1(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

