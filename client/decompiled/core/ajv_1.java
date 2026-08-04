/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajV
 */
public class ajv_1
extends auQ {
    private static final acl_0 uG = new ym_0(new aei_1());
    private short uM;
    private long uN;
    private byte[] uO;

    public static ajv_1 azz() {
        ajv_1 ajv_12;
        try {
            ajv_12 = (ajv_1)uG.adr();
            ajv_12.a(uG);
        }
        catch (Exception exception) {
            ajv_12 = new ajv_1();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type StatisticsReportRequestMessage : " + exception.getMessage()));
        }
        return ajv_12;
    }

    public void b() {
        super.b();
        this.uO = null;
    }

    public void j() {
        super.j();
        this.uO = null;
    }

    byte[] azA() {
        return this.uO;
    }

    void f(byte[] byArray) {
        this.uO = byArray;
    }

    public short wC() {
        return this.uM;
    }

    public void y(short s) {
        this.uM = s;
    }

    public long wD() {
        return this.uN;
    }

    public void R(long l2) {
        this.uN = l2;
    }

    public int getId() {
        return 1;
    }
}

