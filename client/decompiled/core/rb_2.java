/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from RB
 */
public class rb_2
implements atG {
    private static rb_2 bJt = new rb_2();

    public static rb_2 aem() {
        return bJt;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 5202: {
                yz_2 yz_22 = (yz_2)pr_02;
                mT mT2 = bd_1.Is().bb(yz_22.mb());
                if (mT2 instanceof aez_0) {
                    aez_0 aez_02 = (aez_0)mT2;
                    aez_02.aQn().k(yz_22.ane());
                }
                return false;
            }
            case 5200: {
                air_2 air_22 = (air_2)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null) {
                    ky_2 ky_22 = sj_12.aQn();
                    ky_2 ky_23 = sj_12.yD();
                    mm_0 mm_02 = air_22.aUV();
                    int n2 = mm_02.size();
                    for (int j = 0; j < n2; ++j) {
                        short s = mm_02.get(j);
                        ky_22.A(s);
                        ky_23.A(s);
                    }
                    List list = air_22.aUU();
                    for (pf_0 pf_02 : list) {
                        short s = (Short)pf_02.getFirst();
                        wy_2 wy_22 = (wy_2)pf_02.acl();
                        if (ky_22.z(s) == null) {
                            ky_22.A(s);
                            ky_23.A(s);
                        }
                        try {
                            ky_22.a(wy_22, s);
                            ky_23.a(wy_22, s);
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    List list2 = air_22.aUW();
                    Object object = list2.iterator();
                    while (object.hasNext()) {
                        wy_2 wy_23 = (wy_2)object.next();
                        try {
                            ky_22.d(ky_22.bW(wy_23.jf()));
                            ky_23.d(ky_23.bW(wy_23.jf()));
                            ky_22.f(wy_23);
                            ky_23.f(wy_23);
                        }
                        catch (Exception exception) {}
                    }
                    object = air_22.aUX();
                    int n3 = ((jg_0)object).size();
                    for (int j = 0; j < n3; ++j) {
                        int n4 = ((jg_0)object).get(j);
                        ky_22.d(ky_22.bW(n4));
                        ky_23.d(ky_23.bW(n4));
                    }
                    sj_12.av(true);
                    if (add_1.aOG().kR("graveyardDialog") || add_1.aOG().kR("evolutionDialog")) {
                        aij_0.aUF().aUI();
                        aij_0.aUF().aUH();
                    }
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

