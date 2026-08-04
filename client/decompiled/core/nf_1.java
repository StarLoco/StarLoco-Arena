/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Nf
 */
public class nf_1
extends uc_1 {
    public nf_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addCubicSplineTweenToMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("destX", aos_1.elT, false), new LX("destY", aos_1.elT, false), new LX("destZ", aos_1.elT, false), new LX("duration", aos_1.elT, false), new LX("startVectorX", aos_1.elU, false), new LX("startVectorY", aos_1.elU, false), new LX("startVectorZ", aos_1.elU, false), new LX("endVectorX", aos_1.elU, false), new LX("endVectorY", aos_1.elU, false), new LX("endVectorZ", aos_1.elU, false)};
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
        float f3 = (float)this.hX(7);
        float f4 = (float)this.hX(8);
        float f5 = (float)this.hX(9);
        float f6 = (float)this.hX(10);
        mT mT2 = bd_1.Is().bb(l2);
        aln_0 aln_02 = new aln_0(mT2);
        aln_02.h(new agv_0(n3, n4, n5));
        aln_02.f(new agv_0(mT2.gn(), mT2.go(), mT2.gp()));
        aln_02.dN(n6);
        aln_02.g(new agv_0(f, f2, f3));
        aln_02.i(new agv_0(f4, f5, f6));
        ahq_0.awW().b(aln_02);
    }
}

