/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class Uj {
    protected static final Logger a = Logger.getLogger(Uj.class);
    private static final ArrayList bPF = new ArrayList(64);

    public static ArrayList a(int n2, int n3, qc_0 qc_02, su_1 su_12, rz_1 rz_12) {
        su_12.reset();
        int n4 = rz_12.aed();
        int n5 = rz_12.aee();
        bPF.clear();
        switch (qc_02) {
            case bEK: {
                int n6 = n2 - rz_12.aea();
                int n7 = n3 - rz_12.aeb();
                for (int j = 0; j < n4; ++j) {
                    int n8 = n6 + j;
                    for (int i2 = 0; i2 < n5; ++i2) {
                        if (!rz_12.aS(j, i2)) continue;
                        Uj.aZ(n8, n7 + i2);
                    }
                }
                break;
            }
            case bEM: {
                int n9 = n2 - rz_12.aeb() + rz_12.aee() - 1;
                int n10 = n3 - rz_12.aea();
                for (int j = 0; j < n4; ++j) {
                    int n11 = n10 + j;
                    for (int i3 = 0; i3 < n5; ++i3) {
                        if (!rz_12.aS(j, i3)) continue;
                        Uj.aZ(n9 - i3, n11);
                    }
                }
                break;
            }
            case bEO: {
                int n12 = n2 - rz_12.aea() + rz_12.aed() - 1;
                int n13 = n3 - rz_12.aeb() + rz_12.aee() - 1;
                for (int j = 0; j < n4; ++j) {
                    int n14 = n12 - j;
                    for (int i4 = 0; i4 < n5; ++i4) {
                        if (!rz_12.aS(j, i4)) continue;
                        Uj.aZ(n14, n13 - i4);
                    }
                }
                break;
            }
            case bEQ: {
                int n15 = n2 - rz_12.aeb();
                int n16 = n3 + rz_12.aea();
                for (int j = 0; j < n4; ++j) {
                    int n17 = n16 - j;
                    for (int i5 = 0; i5 < n5; ++i5) {
                        if (!rz_12.aS(j, i5)) continue;
                        Uj.aZ(n15 + i5, n17);
                    }
                }
                break;
            }
        }
        su_12.k(bPF);
        return su_12.afk();
    }

    private static void aZ(int n2, int n3) {
        aga_0.aSG().a(n2, n3, bPF, pq_2.abX);
    }

    public static ArrayList a(int n2, int n3, qc_0 qc_02, su_1 su_12, List list) {
        su_12.reset();
        int n4 = n2;
        int n5 = n3;
        bPF.clear();
        switch (qc_02) {
            case bEK: 
            case bET: {
                for (int[] nArray : list) {
                    Uj.aZ(n4 + nArray[0], n5 + nArray[1]);
                }
                break;
            }
            case bEM: {
                for (int[] nArray : list) {
                    Uj.aZ(n4 - nArray[1], n5 + nArray[0]);
                }
                break;
            }
            case bEO: {
                for (int[] nArray : list) {
                    Uj.aZ(n4 - nArray[0], n5 - nArray[1]);
                }
                break;
            }
            case bEQ: {
                for (int[] nArray : list) {
                    Uj.aZ(n4 + nArray[1], n5 - nArray[0]);
                }
                break;
            }
            default: {
                a.error((Object)("Impossible de selectionner des cellules dans cette direction :" + qc_02));
            }
        }
        su_12.k(bPF);
        return su_12.afk();
    }
}

