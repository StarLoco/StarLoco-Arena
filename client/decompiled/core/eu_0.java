/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;
import java.util.Collections;

/*
 * Renamed from eu
 */
public class eu_0
implements alq_2 {
    private static final aen_0 oM = new aen_0();
    private static final aja_1 oN;
    private final amg_1 oO;
    private final amg_1 oP;

    public eu_0(amg_1 amg_12, amg_1 amg_13) {
        this.oO = amg_12;
        this.oP = amg_13;
    }

    public void a(abm_2 abm_22, arh_0 arh_02) {
        if (arh_02 == null) {
            return;
        }
        int[] nArray = arh_02.aEI();
        if (nArray == null) {
            return;
        }
        int n2 = this.oO.gn();
        int n3 = this.oO.go();
        int n4 = n2 - nArray[0];
        int n5 = n3 - nArray[1];
        int[] nArray2 = this.e(nArray[0], nArray[1], (short)nArray[2]);
        if (n4 * n4 + n5 * n5 > 300) {
            this.oO.a(nArray2[0], (double)nArray2[1], (double)((short)nArray2[2]));
            return;
        }
        oN.reset();
        qe_0 qe_02 = qe_0.adj();
        eu_0.oM.cpH = false;
        qe_02.a(oM);
        qe_02.a((int)this.oO.ge(), this.oO.ox(), this.oO.BP());
        qe_02.a(oN);
        qe_02.p(this.oO.gn(), this.oO.go(), this.oO.gp());
        auU.a(n2, n3, nArray2[0], nArray2[1], 9, oN);
        qe_02.q(nArray2[0], nArray2[1], (short)nArray2[2]);
        qe_02.ado();
        arh_0 arh_03 = qe_02.FJ();
        qe_02.release();
        ip_2.Un().a(new avO(this, arh_03), this.oO.Pr().a(this.oO) / 2, 1);
    }

    public int[] e(int n2, int n3, short s) {
        qc_0[] qc_0Array = qc_0.acQ();
        Collections.shuffle(Arrays.asList(qc_0Array));
        int n4 = n2;
        int n5 = n3;
        short s2 = s;
        boolean bl2 = false;
        for (int j = 0; j < qc_0Array.length; ++j) {
            qc_0 qc_02 = qc_0Array[j];
            n4 = n2 + qc_02.acJ()[0];
            if (auU.bW(n4, n5 = n3 + qc_02.acJ()[1]) == null || (s2 = auU.H(n4, n5, s)) == Short.MIN_VALUE) continue;
            bl2 = true;
            break;
        }
        if (!bl2) {
            n4 = n2;
            n5 = n3;
            s2 = s;
        }
        return new int[]{n4, n5, s2};
    }

    public void reset() {
        int[] nArray = this.e(this.oP.gn(), this.oP.go(), this.oP.gp());
        this.oO.a(nArray[0], (double)nArray[1], (double)((short)nArray[2]));
    }

    static /* synthetic */ amg_1 a(eu_0 eu_02) {
        return eu_02.oO;
    }

    static {
        eu_0.oM.cpH = true;
        eu_0.oM.cpJ = 25;
        eu_0.oM.cpI = 400;
        eu_0.oM.cpM = false;
        eu_0.oM.cpO = true;
        oN = new aja_1();
    }
}

