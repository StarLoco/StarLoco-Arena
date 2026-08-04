/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aaC
 */
class aac_0
extends uc_1 {
    final /* synthetic */ akn yt;

    private aac_0(akn akn2, LuaState luaState) {
        this.yt = akn2;
        super(luaState);
    }

    public String getName() {
        return "createActor";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("spriteName", aos_1.elS, false), new LX("worldX", aos_1.elT, false), new LX("worldY", aos_1.elT, false), new LX("altitude", aos_1.elT, false), new LX("availableDirection", aos_1.elT, true), new LX("setId", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        String string = this.hZ(1);
        int n3 = this.hW(2);
        int n4 = this.hW(3);
        int n5 = this.hW(4);
        byte by = (byte)(n2 >= 6 ? this.hW(5) : 8);
        amt_1 amt_12 = new amt_1(this, l2);
        amt_12.iG(string);
        amt_12.b(qc_0.bEK);
        amt_12.aY("AnimStatique");
        amt_12.a(n3, (double)n4, (double)n5);
        if (by != 4 && by != 8) {
            this.a(a, "nombre de direction (" + by + ") inconnu, forc\u00e9e \u00e0 8 ");
            amt_12.be((byte)8);
        } else {
            amt_12.be(by);
        }
        bd_1.Is().g(amt_12);
    }

    /* synthetic */ aac_0(akn akn2, LuaState luaState, KX kX) {
        this(akn2, luaState);
    }
}

