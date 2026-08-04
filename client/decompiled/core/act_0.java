/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from aCT
 */
public class act_0
implements Comparator {
    private final aml_2 CC;

    public act_0(aml_2 aml_22) {
        this.CC = aml_22;
    }

    public int a(alp_0 alp_02, alp_0 alp_03) {
        int n2;
        int n3 = alp_02.Ou().getId();
        if (alp_02 == null) {
            return -1;
        }
        if (alp_03 == null) {
            return 1;
        }
        if (alp_02 == alp_03) {
            return 0;
        }
        int n4 = alp_02.d(Lr.bqA);
        if (n4 > (n2 = alp_03.d(Lr.bqA))) {
            return -1;
        }
        if (n4 < n2) {
            return 1;
        }
        if (alp_02.getId() < 0L) {
            if (alp_03.getId() < 0L) {
                if (alp_02.getId() < alp_03.getId()) {
                    return -1;
                }
                return 1;
            }
            return 1;
        }
        if (alp_03.getId() < 0L) {
            return -1;
        }
        long l2 = alp_02.getId() + (long)n3;
        long l3 = alp_03.getId() + (long)n3;
        long l4 = 2L;
        while (l2 % l4 == l3 % l4) {
            l4 *= 2L;
        }
        if (l2 % l4 > l3 % l4) {
            return -1;
        }
        return 1;
    }

    public int a(Long l2, Long l3) {
        alp_0 alp_02 = (alp_0)this.CC.ej(l2);
        alp_0 alp_03 = (alp_0)this.CC.ej(l3);
        return this.a(alp_02, alp_03);
    }
}

