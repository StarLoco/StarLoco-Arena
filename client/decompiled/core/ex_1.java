/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from ex
 */
public class ex_1
extends yp_2 {
    public static final String oS = "buffEffectList";
    public static final String[] ce = new String[]{"buffEffectList"};
    public static final String[] oT = new String[ce.length + yp_2.ce.length];
    private long oU;
    private ArrayList oV = new ArrayList();

    public ex_1(int n2, int n3, byte by, byte by2, byte by3, byte by4, byte by5, byte by6, boolean bl2, boolean bl3, byte by7, byte by8, int n4, int n5, boolean bl4, int n6, List list, boolean bl5, boolean bl6, long[] lArray, boolean bl7, long l2, fv fv2) {
        super(n2, n3, by, by2, by3, by4, by5, by6, bl2, bl3, by7, by8, n4, n5, bl4, n6, list, bl5, bl6, lArray, bl7, fv2);
        this.oU = l2;
    }

    public ex_1(yp_2 yp_22, long l2) {
        super(yp_22.getId(), yp_22.iQ(), yp_22.iR(), yp_22.iS(), yp_22.iT(), yp_22.iU(), yp_22.ja(), yp_22.et(), yp_22.iW(), yp_22.iN(), yp_22.iY(), yp_22.iZ(), yp_22.getValue(), yp_22.getTarget(), yp_22.iX(), yp_22.eA(), yp_22.iL(), yp_22.adW(), yp_22.eD(), yp_22.jb(), yp_22.eF(), yp_22.jd());
        this.rd = yp_22.iK();
        this.oU = l2;
        this.rt = yp_22.jc();
    }

    public ArrayList hE() {
        return this.oV;
    }

    public void b(xb_2 xb_22) {
        this.oV.add(xb_22);
    }

    public Object getFieldValue(String string) {
        if (string.equals(oS)) {
            ArrayList<vw_2> arrayList = new ArrayList<vw_2>();
            azg_0 azg_02 = apN.aDK().aDL().ass();
            short s = azg_02.JI();
            boolean bl2 = false;
            if (azg_02.bj(this.oU) < azg_02.JH()) {
                bl2 = true;
            }
            xb_2 xb_22 = (xb_2)this.oV.get(0);
            short s2 = 0;
            for (int j = 0; j < this.oV.size(); ++j) {
                xb_2 xb_23 = (xb_2)this.oV.get(j);
                if (xb_23.getId() == xb_22.getId() && xb_23.aex().aVC() == xb_22.aex().aVC()) {
                    s2 = (short)(s2 + 1);
                } else {
                    vw_2 vw_22 = new vw_2(s2, (short)(xb_22.aex().aVC() - s - 1 + (bl2 ? 0 : 1)), asf_0.c(this.ax(xb_22.ajO().ST())));
                    arrayList.add(vw_22);
                    s2 = 1;
                }
                xb_22 = xb_23;
            }
            vw_2 vw_23 = new vw_2(s2, (short)(xb_22.aex().aVC() - s - 1 + (bl2 ? 0 : 1)), asf_0.c(this.ax(xb_22.ajO().ST())));
            arrayList.add(vw_23);
            return arrayList.toArray();
        }
        return super.getFieldValue(string);
    }

    public String[] getFields() {
        return oT;
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(yp_2.ce, 0, oT, ce.length, yp_2.ce.length);
    }
}

