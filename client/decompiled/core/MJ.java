/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import java.util.ArrayList;
import java.util.List;

public class MJ {
    private static final short bxD = 2;

    public static arh_0 a(awp awp2) {
        List list = awp2.mB(200);
        if (list.isEmpty()) {
            return new arh_0();
        }
        arh_0 arh_02 = new arh_0(list.size());
        for (int j = 0; j < list.size(); ++j) {
            arh_02.c(j, (int[])list.get(j));
        }
        return arh_02;
    }

    public static arh_0 a(qs_2 qs_22, abm_2 abm_22, int n2, int n3, aen_0 aen_02, aja_1 aja_12) {
        qe_0 qe_02 = qe_0.adj();
        arh_0 arh_02 = MJ.a(qs_22, abm_22, n2, n3, aen_02, aja_12, qe_02);
        qe_02.release();
        return arh_02;
    }

    public static arh_0 a(qs_2 qs_22, abm_2 abm_22, int n2, int n3, aen_0 aen_02, aja_1 aja_12, qe_0 qe_02) {
        float f = 0.0f;
        float f2 = 0.0f;
        ArrayList arrayList = qs_22.a(n2, n3, (float)abm_22.getAltitude(), ma_0.buh, 0.0f, 0.0f);
        int n4 = arrayList.size();
        if (n4 == 0) {
            return null;
        }
        int n5 = abm_22.aNU();
        int n6 = abm_22.aNV();
        short s = abm_22.aNW();
        arh_0 arh_02 = null;
        qe_02.adk();
        qe_02.p(n5, n6, s);
        qe_02.a(aen_02);
        qe_02.a((int)abm_22.ge(), abm_22.ox(), abm_22.BP());
        auU.a(abm_22.ge(), abm_22.ox(), abm_22.BP());
        boolean bl2 = false;
        if (aja_12 == null) {
            aja_12 = new aja_1();
            bl2 = true;
        }
        qe_02.a(aja_12);
        se_2 se_22 = new se_2(50);
        for (int j = 0; j < n4; ++j) {
            short s2;
            int n7;
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(j);
            ScreenElement screenElement = displayedScreenElement.atV();
            if (!se_22.add(displayedScreenElement.aua())) continue;
            int n8 = screenElement.avV();
            short s3 = auU.H(n8, n7 = screenElement.avW(), s2 = screenElement.avU());
            if (Math.abs(s3 - s2) <= 2) {
                s2 = s3;
            }
            if (bl2) {
                auU.a(n5, n6, n8, n7, 27, aja_12);
            }
            qe_02.q(n8, n7, s2);
            int n9 = qe_02.ado();
            if (n9 == -1) continue;
            long[] lArray = qe_02.adm();
            int[][] nArray = new int[n9][3];
            for (int i2 = 0; i2 < n9; ++i2) {
                int n10 = n9 - 1 - i2;
                nArray[i2][0] = qe_0.cy(lArray[n10]);
                nArray[i2][1] = qe_0.cz(lArray[n10]);
                nArray[i2][2] = qe_0.cA(lArray[n10]);
            }
            arh_02 = new arh_0(nArray);
            break;
        }
        return arh_02;
    }

    public static ry a(qs_2 qs_22, int n2, int n3, boolean bl2) {
        return MJ.a(qs_22, n2, n3, bl2, bl2);
    }

    public static ry a(qs_2 qs_22, int n2, int n3, boolean bl2, boolean bl3) {
        aog_2 aog_22;
        ArrayList arrayList;
        if ((bl2 || bl3) && (arrayList = qs_22.c(n2, (double)n3)).size() != 0 && (aog_22 = (mT)arrayList.get(0)) != null && (bl2 && ((ahh_1)aog_22).ox() == 0 || bl3 && ((ahh_1)aog_22).ox() > 0)) {
            return ((ahh_1)aog_22).aTI();
        }
        arrayList = qs_22.a(n2, n3, 0.0f, ma_0.buh);
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        aog_22 = (DisplayedScreenElement)arrayList.get(0);
        ScreenElement screenElement = ((DisplayedScreenElement)aog_22).atV();
        return new ry(screenElement.avV(), screenElement.avW(), screenElement.avU());
    }

    public static ArrayList b(qs_2 qs_22, int n2, int n3, boolean bl2) {
        return MJ.b(qs_22, n2, n3, bl2, bl2);
    }

    public static ArrayList b(qs_2 qs_22, int n2, int n3, boolean bl2, boolean bl3) {
        Object object;
        ArrayList arrayList;
        if ((bl2 || bl3) && (arrayList = qs_22.c(n2, (double)n3)).size() != 0 && (object = (mT)arrayList.get(0)) != null && (bl2 && ((ahh_1)object).ox() == 0 || bl3 && ((ahh_1)object).ox() > 0)) {
            ArrayList<ry> arrayList2 = new ArrayList<ry>();
            arrayList2.add(((ahh_1)object).aTI());
            return arrayList2;
        }
        arrayList = qs_22.a(n2, n3, 0.0f, ma_0.buh);
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        object = new ArrayList();
        for (int j = 0; j < arrayList.size(); ++j) {
            ScreenElement screenElement = ((DisplayedScreenElement)arrayList.get(j)).atV();
            ((ArrayList)object).add(new ry(screenElement.avV(), screenElement.avW(), screenElement.avU()));
        }
        return object;
    }
}

