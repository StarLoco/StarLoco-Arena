/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class A
implements atG {
    protected static final Logger a = Logger.getLogger(A.class);
    private static A an = new A();

    public static A U() {
        return an;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 22002: {
                ls_0 ls_02 = (ls_0)pr_02;
                apN.aDK().Ln().b(ls_02.qI());
                sb_0 sb_02 = new sb_0();
                sb_02.bF((short)8);
                sb_02.f(22051);
                acu_1.ara().c(sb_02);
                add_1.aOG().a("achievementDialog", oh_2.bq("achievementDialog"), (short)10000);
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

