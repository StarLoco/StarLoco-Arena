/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from awF
 */
public class awf_0
implements awp {
    private agv_0 dic = new agv_0();
    private agv_0 did = new agv_0();
    private agv_0 die = new agv_0();
    private agv_0 dif = new agv_0();
    private long dig;
    private long dih;

    public awf_0() {
        this.dih = 0L;
        this.dig = 0L;
    }

    public awf_0(awf_0 awf_02) {
        this.dic.d(awf_02.dic.getX(), awf_02.dic.getY(), awf_02.dic.id());
        this.did.d(awf_02.did.getX(), awf_02.did.getY(), awf_02.did.id());
        this.die.d(awf_02.die.getX(), awf_02.die.getY(), awf_02.die.id());
        this.dif.d(awf_02.dif.getX(), awf_02.dif.getY(), awf_02.dif.id());
        this.dig = awf_02.t();
        this.dih = awf_02.s();
    }

    public void eb(long l2) {
        this.dig = l2;
    }

    public long t() {
        return this.dig;
    }

    public void ec(long l2) {
        this.dih = l2;
    }

    public long s() {
        return this.dih;
    }

    public agv_0 u() {
        return this.dic;
    }

    public agv_0 aJA() {
        return this.did;
    }

    public agv_0 v() {
        return this.die;
    }

    public agv_0 aJB() {
        return this.dif;
    }

    public void f(agv_0 agv_02) {
        this.dic.j(agv_02);
    }

    public void g(agv_0 agv_02) {
        this.did.j(agv_02);
    }

    public void h(agv_0 agv_02) {
        this.die.j(agv_02);
    }

    public void i(agv_0 agv_02) {
        this.dif.j(agv_02);
    }

    public agv_0 a(long l2) {
        if (l2 < this.dig) {
            l2 = this.dig;
        }
        if (this.dih == this.dig || this.dif.aSy() == 0.0f && l2 >= this.dih) {
            return new agv_0(this.die);
        }
        float f = (float)(l2 - this.dig) / (float)(this.dih - this.dig);
        return new agv_0(this.dic.getX() + this.did.getX() * f, this.dic.getY() + this.did.getY() * f, this.dic.id() + this.did.id() * f);
    }

    public double ed(long l2) {
        return (double)(l2 - this.dig) / (double)(this.dih - this.dig);
    }

    public long getDuration() {
        return this.dih - this.dig;
    }

    public List mB(int n2) {
        int n3 = 0;
        int n4 = 0;
        ArrayList<int[]> arrayList = new ArrayList<int[]>();
        int[] nArray = null;
        for (long j = this.dig; j < this.dih; j += (long)n2) {
            agv_0 agv_02 = this.a(j);
            int n5 = (int)agv_02.getX();
            int n6 = (int)agv_02.getY();
            int n7 = (int)agv_02.id();
            if (j > this.dig && n5 == n3 && n6 == n4) continue;
            nArray = new int[]{n5, n6, n7};
            arrayList.add(nArray);
            n3 = n5;
            n4 = n6;
        }
        if (!(arrayList.isEmpty() || nArray == null || (float)nArray[0] == this.die.getX() && (float)nArray[1] == this.die.getY() && (float)nArray[2] == this.die.id())) {
            nArray = new int[]{(int)this.die.getX(), (int)this.die.getY(), (int)this.die.id()};
            arrayList.add(nArray);
        }
        return arrayList;
    }

    public void ee(long l2) {
        this.dig += l2;
        this.dih += l2;
    }

    public List H(double d) {
        double d2 = this.die.n(this.dic).aSz();
        int n2 = (int)Math.ceil(d2 / d);
        ArrayList<awf_0> arrayList = new ArrayList<awf_0>(n2);
        if (n2 == 0) {
            arrayList.add(this);
            return arrayList;
        }
        double d3 = d2 / (double)n2;
        long l2 = this.getDuration() / (long)n2;
        agv_0 agv_02 = this.die.n(this.dic);
        agv_02.aSB();
        agv_0 agv_03 = this.dic;
        long l3 = this.dig;
        for (int j = 0; j < n2; ++j) {
            awf_0 awf_02 = new awf_0();
            awf_02.f(agv_03);
            awf_02.eb(l3);
            if (j != n2 - 1) {
                awf_02.i(this.did);
                agv_0 agv_04 = agv_03.m(agv_02.bN((float)d3));
                awf_02.h(agv_04);
                awf_02.ec(l3 + l2);
            } else {
                awf_02.ec(this.dih);
                awf_02.i(this.dif);
                awf_02.h(this.die);
            }
            awf_02.g(awf_02.v().n(awf_02.u()));
            l3 += l2;
            agv_03 = awf_02.v();
            arrayList.add(awf_02);
        }
        return arrayList;
    }

    public double length() {
        return this.die.n(this.dic).aSz();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.getClass().getSimpleName());
        stringBuffer.append(" (duration:").append(this.dih - this.dig).append(") > from=");
        stringBuffer.append(this.dic);
        stringBuffer.append(", to=");
        stringBuffer.append(this.die);
        stringBuffer.append(", initVel=");
        stringBuffer.append(this.did);
        stringBuffer.append(", finalVel=");
        stringBuffer.append(this.dif);
        stringBuffer.append(", initialTime=");
        stringBuffer.append(this.dig);
        stringBuffer.append(", finalTime=");
        stringBuffer.append(this.dih);
        return stringBuffer.toString();
    }
}

