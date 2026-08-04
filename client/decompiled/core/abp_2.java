/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from abP
 */
public class abp_2
extends uc_1 {
    public abp_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "throwCarriedMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("destX", aos_1.elT, false), new LX("destY", aos_1.elT, false), new LX("destZ", aos_1.elT, false), new LX("duration", aos_1.elT, false), new LX("startVectorZ", aos_1.elU, false), new LX("endVectorZ", aos_1.elU, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        int n4 = this.hW(2);
        int n5 = this.hW(3);
        int n6 = this.hW(4);
        float f = (float)this.hX(5);
        float f2 = (float)this.hX(6);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null) {
            this.a(a, "Pas de carrier trouv\u00e9 avec l'id " + l2);
            return;
        }
        mT mT3 = mT2.rB();
        if (mT3 == null) {
            this.a(a, "Pas de carrier port\u00e9 trouv\u00e9 avec l'id " + l2);
            return;
        }
        mT2.a(false, null);
        aln_0 aln_02 = new aln_0(mT3);
        aln_02.h(new agv_0(n3, n4, n5));
        aln_02.f(new agv_0(mT3.gn(), mT3.go(), mT3.gp() + mT2.ge()));
        aln_02.dN(n6);
        aln_02.g(new agv_0(0.0f, 0.0f, f));
        aln_02.i(new agv_0(0.0f, 0.0f, f2));
        ahq_0.awW().b(aln_02);
    }
}

