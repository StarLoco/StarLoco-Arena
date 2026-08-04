/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from YB
 */
public final class yb_1 {
    private lb_0 cbi;
    private int m_size;

    public yb_1() {
        this.cbi = new lb_0(4);
        this.m_size = 0;
    }

    public yb_1(yb_1 yb_12) {
        this.cbi = new lb_0(yb_12.cbi.size());
        ll_0 ll_02 = yb_12.cbi.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            this.cbi.c(ll_02.kR(), ll_02.value());
        }
        this.m_size = yb_12.m_size;
    }

    public final void a(aij_1 aij_12) {
        aij_12.writeInt(this.m_size);
        int n2 = this.getNumComponents();
        aij_12.writeInt(n2);
        ll_0 ll_02 = this.cbi.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((agt_2)ll_02.value()).a(aij_12);
        }
    }

    public final void b(acf acf2) {
        this.m_size = acf2.readInt();
        int n2 = acf2.readInt();
        this.cbi = new lb_0(n2);
        for (int j = 0; j < n2; ++j) {
            agt_2 agt_22 = new agt_2(acf2);
            this.cbi.c(agt_22.aws(), agt_22);
        }
    }

    public final int getSize() {
        return this.m_size;
    }

    public final void a(agt_2 agt_22) {
        assert (this.W(agt_22.aws()) == null);
        agt_22.NB = this.m_size;
        this.cbi.c(agt_22.aws(), agt_22);
        this.m_size += agt_22.getSize();
    }

    public final void jg(int n2) {
        ll_0 ll_02 = this.cbi.pK();
        ll_0 ll_03 = this.cbi.pK();
        agt_2 agt_22 = null;
        while (ll_02.hasNext()) {
            ll_02.fK();
            if (ll_02.kR() != n2) continue;
            ll_03.fK();
            agt_22 = (agt_2)ll_03.value();
            break;
        }
        if (agt_22 == null) {
            return;
        }
        int n3 = agt_22.getSize();
        this.m_size -= agt_22.getSize();
        while (ll_03.hasNext()) {
            ll_03.fK();
            ((agt_2)ll_03.value()).NB -= n3;
        }
        this.cbi.remove(n2);
    }

    public final agt_2 W(int n2) {
        return (agt_2)this.cbi.get(n2);
    }

    public final int getNumComponents() {
        return this.cbi.size();
    }
}

