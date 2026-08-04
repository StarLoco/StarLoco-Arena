/*
 * Decompiled with CFR 0.152.
 */
public class aHX
extends qg_1 {
    private long[] dOo;

    public aHX() {
        this(10);
    }

    public aHX(int n2) {
        super(n2);
        this.dOo = new long[n2];
    }

    public boolean cM(int n2) {
        int n3 = this.adv.length;
        if (!super.cM(n2)) {
            return false;
        }
        long[] lArray = this.dOo;
        this.dOo = new long[n2];
        System.arraycopy(lArray, 0, this.dOo, 0, n3);
        return true;
    }

    public void o(long l2, long l3) {
        this.ve();
        int n2 = this.aA(l2);
        if (n2 < 0) {
            n2 = -n2 - 1;
        } else {
            ++this.m_size;
            this.adv[n2] = l2;
        }
        this.dOo[n2] = l3;
    }

    public long dv(long l2) {
        if (this.m_size == 0) {
            return 0L;
        }
        int n2 = this.az(l2);
        if (n2 < 0) {
            return 0L;
        }
        long l3 = this.dOo[n2];
        this.adv[n2] = this.adv[this.m_size - 1];
        this.dOo[n2] = this.dOo[this.m_size - 1];
        --this.m_size;
        return l3;
    }

    public long du(long l2) {
        int n2 = this.az(l2);
        if (n2 < 0) {
            return 0L;
        }
        return this.dOo[n2];
    }

    public long oJ(int n2) {
        return this.dOo[n2];
    }
}

