/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import org.apache.log4j.Logger;

public abstract class IP
extends GregorianCalendar
implements Runnable {
    private static final Logger a = Logger.getLogger(IP.class);
    private static final boolean DEBUG = false;
    protected final aby_0 bie = new aby_0();
    private final List bif = new ArrayList();
    private final List big = new ArrayList();
    private final List bih = new ArrayList();
    private final int bii;
    private final int bij;
    private rd_1 OV;
    private afc_1 bik;
    private long bil;
    private long bim;
    protected boolean bin = false;

    protected IP(int n2, int n3) {
        super(TimeZone.getDefault());
        this.bii = n2;
        this.bij = n3;
    }

    public void run() {
        if (this.bin) {
            long l2 = this.UR();
            this.setTimeInMillis(l2);
            this.OV.set(l2);
            this.UW();
            this.bif.removeAll(this.big);
            this.big.clear();
            int n2 = this.bif.size();
            for (int j = 0; j < n2; ++j) {
                ((ee_1)this.bif.get(j)).a(QC.bGM, this);
            }
        }
    }

    public void a(ee_1 ee_12) {
        if (!this.big.remove(ee_12) || !this.bif.contains(ee_12)) {
            this.bif.add(ee_12);
        }
    }

    public void a(atq_0 atq_02) {
        if (!this.bih.contains(atq_02)) {
            this.bih.add(atq_02);
        }
    }

    public sv_1 Me() {
        return this.bie.Me();
    }

    public nF UQ() {
        return this.bie.UQ();
    }

    public long UR() {
        return (this.US() + this.bil) * (long)this.bij;
    }

    long US() {
        return (System.nanoTime() - this.bim) / 1000000L;
    }

    public byte UT() {
        int n2 = this.bie.Me().size();
        for (int j = 0; j < n2; ++j) {
            nF nF2 = (nF)this.bie.Me().get(j);
            if (!(nF2 instanceof anw_1)) continue;
            return ((anw_1)nF2).UY().aiK();
        }
        return -1;
    }

    public long UU() {
        return this.US() + this.bil;
    }

    public void a(nF nF2) {
        this.bie.a(nF2);
        int n2 = this.bif.size();
        for (int j = 0; j < n2; ++j) {
            ((ee_1)this.bif.get(j)).a(QC.bGP, this);
        }
    }

    public void b(ee_1 ee_12) {
        if (!this.big.contains(ee_12)) {
            this.big.add(ee_12);
        }
    }

    public void bP(long l2) {
        this.setTimeInMillis(l2);
        this.bim = System.nanoTime();
        this.bil = l2;
        int n2 = this.bii + (this.get(1) - 1970);
        this.OV = new rd_1(this.get(13), this.get(12), this.get(11), this.get(5), this.get(2) + 1, n2);
        this.bin = true;
        int n3 = this.bif.size();
        for (int j = 0; j < n3; ++j) {
            ((ee_1)this.bif.get(j)).a(QC.bGN, this);
        }
    }

    public void start(long l2) {
        this.UV();
        ip_2.Un().b(this);
        ip_2.Un().a(this, l2);
    }

    protected void UV() {
        this.UW();
    }

    public void b(nF nF2) {
        this.bie.b(nF2);
        int n2 = this.bif.size();
        for (int j = 0; j < n2; ++j) {
            ((ee_1)this.bif.get(j)).a(QC.bGO, this);
        }
    }

    protected void UW() {
        if (!this.bin) {
            return;
        }
        nF nF2 = this.bie.UQ();
        while (nF2 != null && nF2.sz().f(this.OV) < 0) {
            this.c(nF2);
            this.d(nF2);
            this.e(nF2);
            this.UX();
            nF2 = this.bie.UQ();
        }
    }

    private void c(nF nF2) {
        try {
            nF2.a(this);
        }
        catch (Exception exception) {
            a.error((Object)"Exception levee lors de l'execution d'un evenement", (Throwable)exception);
        }
    }

    private void d(nF nF2) {
        int n2 = this.bih.size();
        for (int j = 0; j < n2; ++j) {
            try {
                ((atq_0)this.bih.get(j)).f(nF2);
                continue;
            }
            catch (Exception exception) {
                a.error((Object)"Exception levee lors de la notification d'un evenement aux observers", (Throwable)exception);
            }
        }
    }

    private void e(nF nF2) {
        aju aju2;
        this.bie.a(nF2);
        if (nF2 instanceof aju && (aju2 = (aju)nF2).aze() != null && (aju2.UE().xg() || aju2.UE().f(this.OV) > 0)) {
            this.bie.b(nF2.a(((aju)nF2).aze()));
        }
    }

    private void UX() {
        int n2 = this.bif.size();
        for (int j = 0; j < n2; ++j) {
            try {
                ((ee_1)this.bif.get(j)).a(QC.bGQ, this);
                continue;
            }
            catch (Exception exception) {
                a.error((Object)"Exception levee lors de la notification d'un evenement aux observers", (Throwable)exception);
            }
        }
    }

    public acx_1 sz() {
        return this.OV;
    }

    public boolean i(acx_1 acx_12) {
        return this.OV.equals(acx_12);
    }

    public boolean j(acx_1 acx_12) {
        return this.OV.b(acx_12);
    }

    public boolean k(acx_1 acx_12) {
        return this.OV.c(acx_12);
    }

    public boolean l(acx_1 acx_12) {
        return this.OV.d(acx_12);
    }

    public boolean m(acx_1 acx_12) {
        return this.OV.e(acx_12);
    }

    public afc_1 UY() {
        return this.bik;
    }

    public void a(afc_1 afc_12) {
        this.bik = afc_12;
    }

    public boolean isSynchronized() {
        return this.bin;
    }

    public boolean UZ() {
        return true;
    }
}

