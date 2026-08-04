/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aeq
 */
public class aeq_0
extends alm_0 {
    protected static final Logger a = Logger.getLogger(aeq_0.class);
    private Lr cos;
    private ph_2 cot = null;
    public static final byte cou = 12;
    protected int cov;
    protected int cow;
    protected int cox;
    protected int brp;
    protected int bro;

    public aeq_0(Lr lr, int n2, int n3) {
        this.cos = lr;
        this.bk(n2, n3);
    }

    public Lr atQ() {
        return this.cos;
    }

    public int atR() {
        return this.cov;
    }

    public int value() {
        if (this.cov > this.brp) {
            return this.brp;
        }
        if (this.cov < this.bro) {
            return this.bro;
        }
        if (this.cov > this.cow) {
            return this.max();
        }
        if (this.cov < this.cox) {
            return this.min();
        }
        return this.cov;
    }

    public int max() {
        return Math.min(Math.max(this.cow, this.cox), this.brp);
    }

    public int min() {
        return Math.max(this.cox, this.bro);
    }

    public void set(int n2) {
        this.cov = n2;
        this.aAI();
    }

    public int jZ(int n2) {
        this.set(this.cov + n2);
        return this.cov;
    }

    public int ka(int n2) {
        this.set(this.cov - n2);
        return this.cov;
    }

    public void at(int n2) {
        this.cow = n2;
        this.aAI();
    }

    public void as(int n2) {
        this.cox = Math.min(this.brp, Math.max(this.bro, n2));
        this.cox = Math.min(this.cow, this.cox);
        this.aAI();
    }

    public int kb(int n2) {
        if (this.cow == Integer.MAX_VALUE) {
            return this.cow;
        }
        this.at(this.cow + n2);
        return this.cow;
    }

    public int kc(int n2) {
        if (this.cox == Integer.MIN_VALUE) {
            return this.cox;
        }
        this.as(this.cox + n2);
        return this.cox;
    }

    private void bk(int n2, int n3) {
        this.bro = Math.min(n2, n3);
        this.brp = Math.max(n2, n3);
        if (this.cow > this.brp) {
            this.at(this.brp);
        }
        if (this.cox < this.bro) {
            this.as(this.bro);
        }
    }

    public void atS() {
        ph_2 ph_22 = this.cot;
        this.cot = null;
        this.as(this.cos.XB());
        this.at(this.cos.XC());
        this.set(this.cos.getDefaultValue());
        this.cot = ph_22;
        this.aAI();
    }
}

