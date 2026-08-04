/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bq
 */
public class bq_2
extends abb_1 {
    private static bq_2 fP = new bq_2();

    public static bq_2 cF() {
        return fP;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            azs_0.aLV().g("showToolsInMenuBar", "0");
            add_1.aOG().a("fightMenuBarDialog", oh_2.bq("fightMenuBarDialog"), (short)10000);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("fightMenuBarDialog");
        }
        super.b(fh_22, bl2);
    }
}

