/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public class l
implements adh_1 {
    protected static final float[][] s = new float[][]{{2.0f, -3.0f, 0.0f, 1.0f}, {2.0f, 3.0f, 0.0f, 0.0f}, {1.0f, -2.0f, 1.0f, 0.0f}, {1.0f, -1.0f, 0.0f, 0.0f}};
    protected final ArrayList t;
    protected float u;
    protected long v;
    protected long w;
    private static final agv_0 z = new agv_0();
    private static final agv_0 A = new agv_0();

    public l(List list, long l2) {
        Object object;
        this.w = l2;
        this.u = 0.0f;
        this.v = 0L;
        this.t = new ArrayList();
        if (list.size() == 1) {
            object = (awf_0)list.get(0);
            List list2 = ((awf_0)object).H(((awf_0)object).length() / 2.0);
            list.clear();
            list.addAll(list2);
        }
        object = null;
        for (int j = 0; j < list.size(); ++j) {
            awp awp2 = (awp)list.get(j);
            alm_2 alm_22 = new alm_2(this);
            alm_22.c(awp2.u());
            if (j != 0) {
                ((alm_2)object).bX(((alm_2)object).qG().n(alm_22.qG()).aSz());
                this.u += ((alm_2)object).aWm();
            }
            this.v += awp2.getDuration();
            this.t.add(alm_22);
            object = alm_22;
            if (j != list.size() - 1) continue;
            alm_2 alm_23 = new alm_2(this);
            alm_23.c(awp2.v());
            ((alm_2)object).bX(((alm_2)object).qG().n(alm_23.qG()).aSz());
            this.u += ((alm_2)object).aWm();
            this.t.add(alm_23);
        }
        this.o();
    }

    protected void o() {
        agv_0 agv_02;
        Object object;
        alm_2 alm_22;
        Object object2;
        alm_2 alm_23;
        for (int j = 1; j < this.t.size() - 1; ++j) {
            alm_23 = (alm_2)this.t.get(j - 1);
            object2 = (alm_2)this.t.get(j);
            alm_22 = (alm_2)this.t.get(j + 1);
            object = alm_22.qG().n(((alm_2)object2).qG());
            ((agv_0)object).aSB();
            agv_02 = alm_23.qG().n(((alm_2)object2).qG());
            agv_02.aSB();
            agv_0 agv_03 = ((agv_0)object).n(agv_02);
            agv_03.aSB();
            ((alm_2)object2).r(agv_03);
        }
        alm_2 alm_24 = (alm_2)this.t.get(0);
        alm_23 = (alm_2)this.t.get(1);
        object2 = alm_23.qG().n(alm_24.qG());
        ((agv_0)object2).O(1.0f / alm_24.aWm());
        ((agv_0)object2).O(3.0);
        ((agv_0)object2).l(alm_23.aWl());
        ((agv_0)object2).O(0.5);
        alm_24.r((agv_0)object2);
        alm_22 = (alm_2)this.t.get(this.t.size() - 1);
        object = (alm_2)this.t.get(this.t.size() - 2);
        agv_02 = alm_22.qG().n(((alm_2)object).qG());
        agv_02.O(1.0f / ((alm_2)object).aWm());
        agv_02.O(3.0);
        agv_02.l(((alm_2)object).aWl());
        agv_02.O(0.5);
        alm_22.r(agv_02);
    }

    public agv_0 a(long l2) {
        int n2;
        if (l2 >= this.w + this.v) {
            return ((alm_2)this.t.get(this.t.size() - 1)).qG();
        }
        if (l2 < this.w) {
            return ((alm_2)this.t.get(0)).qG();
        }
        long l3 = l2 - this.w;
        float f = (float)l3 / (float)this.v;
        float f2 = f * this.u;
        float f3 = 0.0f;
        for (n2 = 0; n2 < this.t.size() - 1 && f3 + ((alm_2)this.t.get(n2)).aWm() < f2; ++n2) {
            f3 += ((alm_2)this.t.get(n2)).aWm();
        }
        float f4 = f2 - f3;
        alm_2 alm_22 = (alm_2)this.t.get(n2);
        alm_2 alm_23 = (alm_2)this.t.get(n2 + 1);
        z.j(alm_22.aWl());
        z.O(alm_22.aWm());
        A.j(alm_23.aWl());
        A.O(alm_22.aWm());
        return this.a(alm_22.qG(), z, alm_23.qG(), A, f4 /= alm_22.aWm());
    }

    protected agv_0 a(agv_0 agv_02, agv_0 agv_03, agv_0 agv_04, agv_0 agv_05, float f) {
        float f2 = 2.0f * agv_02.getX() - 2.0f * agv_04.getX() + agv_03.getX() + agv_05.getX();
        float f3 = 2.0f * agv_02.getY() - 2.0f * agv_04.getY() + agv_03.getY() + agv_05.getY();
        float f4 = 2.0f * agv_02.id() - 2.0f * agv_04.id() + agv_03.id() + agv_05.id();
        float f5 = -3.0f * agv_02.getX() + 3.0f * agv_04.getX() - 2.0f * agv_03.getX() - agv_05.getX();
        float f6 = -3.0f * agv_02.getY() + 3.0f * agv_04.getY() - 2.0f * agv_03.getY() - agv_05.getY();
        float f7 = -3.0f * agv_02.id() + 3.0f * agv_04.id() - 2.0f * agv_03.id() - agv_05.id();
        float f8 = agv_03.getX();
        float f9 = agv_03.getY();
        float f10 = agv_03.id();
        float f11 = agv_02.getX();
        float f12 = agv_02.getY();
        float f13 = agv_02.id();
        float f14 = f;
        float f15 = f * f;
        float f16 = f15 * f;
        return new agv_0(f2 * f16 + f5 * f15 + f8 * f14 + f11, f3 * f16 + f6 * f15 + f9 * f14 + f12, agv_02.id());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("TimeUniformSpline={");
        for (alm_2 alm_22 : this.t) {
            stringBuffer.append("( position=").append(alm_22.qG()).append(" velocity=").append(alm_22.aWl()).append(", length=").append(alm_22.aWm()).append(" ), ");
        }
        return stringBuffer.append("}").toString();
    }

    public ArrayList p() {
        return this.t;
    }

    public long q() {
        return this.v;
    }

    public double r() {
        return this.u;
    }

    public agv_0 b(long l2) {
        long l3 = 75L;
        agv_0 agv_02 = this.a(l2);
        agv_0 agv_03 = new agv_0((int)agv_02.getX(), (int)agv_02.getY(), (int)agv_02.id());
        agv_0 agv_04 = new agv_0(agv_03);
        agv_0 agv_05 = agv_03;
        while (l2 < this.w + this.v && agv_04.equals(agv_03)) {
            agv_05 = this.a(l2 + 75L);
            agv_04.d((int)agv_05.getX(), (int)agv_05.getY(), (int)agv_05.id());
            l2 += 75L;
        }
        return agv_05;
    }

    public long s() {
        return this.w + this.v;
    }

    public long t() {
        return this.w;
    }

    public agv_0 u() {
        return ((alm_2)this.t.get(0)).qG();
    }

    public agv_0 v() {
        return ((alm_2)this.t.get(this.t.size() - 1)).qG();
    }
}

