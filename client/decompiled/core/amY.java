/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class amY {
    private static amY cIc = new amY();
    public static short cId = 1;
    public static short cIe = (short)10000;
    public static short cIf = (short)20000;
    public static short cIg = (short)30000;
    private ArrayList cIh = new ArrayList();
    private short cIi = 0;
    private static Comparator cIj = new GD();
    private aht_1 cIk;

    private amY() {
    }

    public static amY aBW() {
        return cIc;
    }

    public void p(na_1 na_12) {
        if (na_12.getModalLevel() > this.cIi) {
            this.cIi = na_12.getModalLevel();
            lb_2.XL().XN();
        }
        this.cIh.add(na_12);
        Collections.sort(this.cIh, cIj);
    }

    public void q(na_1 na_12) {
        this.cIi = this.cIi + 1 < cIf ? cIf : (short)(this.cIi + 1);
        na_12.setModalLevel(this.cIi);
        lb_2.XL().XN();
        this.cIh.add(na_12);
        Collections.sort(this.cIh, cIj);
        ago_2 ago_22 = ago_2.getInstance();
        eq_0 eq_02 = ago_22.getLayeredContainer();
        int n2 = eq_02.getWidgetPositionInLayer((adg_2)na_12);
        if (this.cIh.size() == 1) {
            this.cIk = new aht_1();
            this.cIk.b();
            auW auW2 = new auW();
            auW2.b();
            auW2.setAlign(ajn_1.dSu);
            auW2.setSize(ago_22.getSize());
            this.cIk.setLayoutData(auW2);
            ph_0 ph_02 = new ph_0();
            ph_02.b();
            ph_02.setColor(add_1.aOG().aOJ());
            this.cIk.getAppearance().j(ph_02);
            this.cIk.setSize(ago_22.getSize());
            eq_02.a(this.cIk, 26000, n2);
        } else {
            eq_02.setWidgetPositionInLayer(this.cIk, n2 - 1);
        }
    }

    public void o(na_1 na_12) {
        boolean bl2;
        if (na_12.getModalLevel() == this.cIi) {
            this.cIi = 0;
            for (na_1 na_13 : this.cIh) {
                if (na_13.getModalLevel() <= this.cIi) continue;
                this.cIi = na_13.getModalLevel();
            }
        }
        if (bl2 = this.cIh.remove(na_12)) {
            if (this.cIh.isEmpty()) {
                this.cIk.aab();
            } else {
                na_1 na_13;
                na_13 = ago_2.getInstance();
                eq_0 eq_02 = ((ex_2)na_13).getLayeredContainer();
                na_1 na_14 = (na_1)this.cIh.get(this.cIh.size() - 1);
                int n2 = eq_02.getWidgetPositionInLayer((adg_2)na_14);
                int n3 = eq_02.getWidgetPositionInLayer(this.cIk);
                eq_02.setWidgetPositionInLayer(this.cIk, n2 - (n3 < n2 ? 1 : 0));
            }
        }
    }

    public void removeAllElements() {
        this.cIh.clear();
    }

    public short aBX() {
        return this.cIi;
    }

    public boolean by(int n2, int n3) {
        if (!this.cIh.isEmpty()) {
            adg_2 adg_22;
            for (adg_22 = ago_2.getInstance().getWidget(n2, n3); adg_22 != null && adg_22.getModalLevel() == -1 && adg_22 != ago_2.getInstance(); adg_22 = adg_22.getParentWidget()) {
            }
            if (adg_22 != null) {
                return adg_22.getModalLevel() >= ((na_1)this.cIh.get(0)).getModalLevel();
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return this.cIh.isEmpty();
    }
}

