/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aJK
 */
class ajk_2
extends uc_1 {
    final /* synthetic */ aja pd;

    public ajk_2(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "setInterval";
    }

    public final LX[] Q() {
        return new LX[]{new LX("time", aos_1.elT, false), new LX("funcName", aos_1.elS, false), new LX("funcParams", aos_1.elX, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("taskId", aos_1.elT, false)};
    }

    public final void c(int n2) {
        JX jX = this.agC();
        int n3 = this.hW(0);
        String string = this.hZ(1);
        jJ[] jJArray = this.aX(2, n2);
        int n4 = jX.a((int)n3, (int)-1, (String)string, (jJ[])jJArray).mK;
        this.id(n4);
    }
}

