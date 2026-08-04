/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class Fu
extends uc_1 {
    private static final Logger a = Logger.getLogger(Fu.class);
    private static final aen_0 oM = new aen_0();
    private static final aja_1 oN;

    public Fu(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "moveMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("worldX", aos_1.elT, false), new LX("worldY", aos_1.elT, false), new LX("altitude", aos_1.elT, false), new LX("func", aos_1.elS, true), new LX("params", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        int n4 = this.hW(2);
        int n5 = this.hW(3);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            if (mT2 instanceof abm_2) {
                abm_2 abm_22 = (abm_2)mT2;
                auU.a(mT2.gn(), mT2.go(), n3, n4, 9, oN);
                qe_0 qe_02 = qe_0.adj();
                Fu.oM.cpH = abm_22.aNX() != 8;
                qe_02.a(oM);
                qe_02.a((int)abm_22.ge(), abm_22.ox(), abm_22.BP());
                qe_02.a(oN);
                qe_02.p(mT2.gn(), mT2.go(), (short)mT2.getAltitude());
                qe_02.q(n3, n4, (short)n5);
                qe_02.ado();
                arh_0 arh_02 = qe_02.FJ();
                oN.reset();
                if (arh_02.aEG()) {
                    abm_22.a(arh_02, true, true);
                }
                qe_02.release();
                if (n2 > 4) {
                    JX jX = this.agC();
                    String string = this.hZ(4);
                    jJ[] jJArray = this.aX(5, n2);
                    abm_22.a(new VJ(this, jX, string, jJArray));
                }
            } else {
                this.a(a, "le mobile " + l2 + " n'est pas un PAthMobile ");
            }
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }

    static {
        Fu.oM.cpH = true;
        Fu.oM.cpI = 400;
        Fu.oM.cpM = false;
        oN = new aja_1();
    }
}

