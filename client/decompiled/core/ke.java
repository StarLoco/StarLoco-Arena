/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class ke
implements JG,
cn_1 {
    private static Logger a = Logger.getLogger(ke.class);
    private static final acl_0 uG = new ym_0(new amk_2());
    protected acl_0 DG;
    protected boolean DH = false;
    protected qe_1 DI = null;
    protected boolean DJ = false;
    protected na_1 DK;
    protected na_1 DL;
    protected final ArrayList DM = new ArrayList();

    public void c(na_1 na_12) {
        this.DM.add(na_12);
    }

    public na_1 oB() {
        if (!this.DM.isEmpty()) {
            return (na_1)this.DM.remove(this.DM.size() - 1);
        }
        return null;
    }

    public boolean oC() {
        return !this.DM.isEmpty();
    }

    public boolean oD() {
        return this.DH;
    }

    public void W(boolean bl2) {
        this.DH = bl2;
    }

    public na_1 oE() {
        return this.DL;
    }

    public void d(na_1 na_12) {
        this.DL = na_12;
    }

    public na_1 oF() {
        return this.DK;
    }

    public na_1 oG() {
        return this.oE();
    }

    public void e(na_1 na_12) {
        this.DK = na_12;
    }

    public qe_1 aV() {
        return this.DI;
    }

    public void a(qe_1 qe_12) {
        this.DI = qe_12;
    }

    public boolean oH() {
        return this.DJ;
    }

    public void X(boolean bl2) {
        this.DJ = bl2;
    }

    public static ke oI() {
        ke ke2;
        try {
            ke2 = (ke)uG.adr();
            ke2.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            ke2 = new ke();
            ke2.b();
        }
        return ke2;
    }

    public void release() {
        if (this.DG == null) {
            this.j();
            return;
        }
        try {
            this.DG.af(this);
        }
        catch (Exception exception) {
            this.j();
        }
    }

    public void j() {
        this.DK = null;
        this.DL = null;
        this.DM.clear();
        this.DG = null;
    }

    public void b() {
        this.DJ = false;
        this.DH = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Event type=").append((Object)this.aV()).toString();
    }
}

