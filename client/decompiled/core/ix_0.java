/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ix
 */
class ix_0
extends uc_1 {
    final /* synthetic */ akn yt;

    public ix_0(akn akn2, LuaState luaState) {
        this.yt = akn2;
        super(luaState);
    }

    public String getName() {
        return "onSpellUsed";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("checkCell", aos_1.elV, false), new LX("cellX", aos_1.elT, false), new LX("cellY", aos_1.elT, false), new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        ee_2 ee_22 = (ee_2)apN.aDK().aDL().eg(l2);
        if (ee_22 != null) {
            JX jX = this.agC();
            boolean bl2 = this.ic(1);
            int n3 = this.hW(2);
            int n4 = this.hW(3);
            String string = this.hZ(4);
            jJ[] jJArray = this.aX(5, n2);
            boolean bl3 = jX.a(string, jJArray);
            is_1 is_12 = new is_1(this, bl2, n3, n4, bl3, jX, string, jJArray);
            ee_22.a(is_12);
        } else {
            this.a(a, "le fighter " + l2 + " n'existe pas ");
        }
    }
}

