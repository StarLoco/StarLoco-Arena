/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aAk
 */
public class aak_0
extends ahg_1 {
    private static aak_0 doZ = new aak_0();
    private short atZ;

    public static aak_0 aME() {
        return doZ;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            add_1.aOG().a("demonLadderInformationDialog", oh_2.bq("demonLadderInformationDialog"), (short)10000);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            add_1.aOG().kO("demonLadderInformationDialog");
        }
    }

    public short WU() {
        return this.atZ;
    }

    public void bu(short s) {
        this.atZ = s;
    }
}

