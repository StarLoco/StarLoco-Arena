/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from apb
 */
public class apb_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(apb_1.class);

    public apb_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileLookAt";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("worldX", aos_1.elT, false), new LX("worldY", aos_1.elT, false), new LX("isHeightDirections", aos_1.elV, true)};
    }

    public final LX[] R() {
        return new LX[]{new LX("directionIndex", aos_1.elT, false)};
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        int n4 = this.hW(2);
        boolean bl2 = n2 >= 4 ? this.ic(3) : true;
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            int n5 = n3 - mT2.gn();
            int n6 = n4 - mT2.go();
            qc_0 qc_02 = mT2.L();
            if (bl2 && (n5 != 0 || n6 != 0)) {
                qc_02 = aby_2.D(n5, n6);
            } else if (!bl2) {
                qc_02 = new aby_2(n5, n6, 0).e(mT2.L());
            }
            mT2.b(qc_02);
            this.id(qc_02.getIndex());
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
            this.agB();
        }
    }
}

