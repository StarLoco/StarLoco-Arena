/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aiu
 */
public class aiu_0
extends gq_2
implements MN {
    protected static final Logger a = Logger.getLogger(aiu_0.class);
    public static final short cyD = 6;
    private boolean vd = false;
    protected boolean cyE = false;
    protected ArrayList cyF = new ArrayList();

    public aiu_0() {
    }

    public aiu_0(long l2) {
        super(l2);
    }

    public aiu_0(long l2, double d, double d2) {
        super(l2, d, d2);
    }

    public aiu_0(long l2, double d, double d2, double d3) {
        super(l2, d, d2, d3);
    }

    public aiu_0(long l2, double d, double d2, double d3, boolean bl2) {
        super(l2, d, d2, d3, bl2);
    }

    public void bJ(short s) {
        this.dLR = s;
    }

    public boolean isSelected() {
        return this.vd;
    }

    public void setSelected(boolean bl2) {
        if (bl2 != this.vd) {
            this.vd = bl2;
            this.cyE = true;
        }
    }

    public boolean ayk() {
        return true;
    }

    public void a(aji_0 aji_02) {
        if (!this.cyF.contains(aji_02)) {
            this.cyF.add(aji_02);
        }
    }

    public void b(aji_0 aji_02) {
        this.cyF.remove(aji_02);
    }

    public void ayl() {
        this.cyF.clear();
    }

    public boolean b(aba_2 aba_22, int n2) {
        boolean bl2 = super.b(aba_22, n2);
        if (this.cyE) {
            this.aym();
        }
        return bl2;
    }

    public void a(qs_2 qs_22, int n2) {
        super.a(qs_22, n2);
        if (this.cyE) {
            this.aym();
        }
    }

    private void aym() {
        for (aji_0 aji_02 : this.cyF) {
            aji_02.a(this, this.isSelected());
        }
        this.cyE = false;
    }

    public void ayn() {
    }
}

