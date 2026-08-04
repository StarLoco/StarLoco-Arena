/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Comparator;
import org.apache.log4j.Logger;

public abstract class nF {
    protected static final Logger a = Logger.getLogger(nF.class);
    public static Comparator OU = new adc_2();
    protected rd_1 OV;

    protected nF(acx_1 acx_12) {
        this.OV = new rd_1(acx_12);
    }

    public acx_1 sz() {
        return this.OV;
    }

    public abstract void a(IP var1);

    public nF l(int n2, int n3, int n4, int n5) {
        this.OV.m(n2, n3, n4, n5);
        return this;
    }

    public nF a(jx_0 jx_02) {
        this.OV.a(jx_02.getSeconds(), jx_02.getMinutes(), jx_02.getHours(), jx_02.getDays(), jx_02.getMonths(), jx_02.getYears());
        return this;
    }
}

