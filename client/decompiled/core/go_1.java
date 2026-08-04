/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;

/*
 * Renamed from GO
 */
public class go_1
extends sb_0 {
    private static final acl_0 uG = new ym_0(new ht_1());
    private NM bcs;
    private String GS;
    private String GT;
    private boolean bct;

    private go_1() {
    }

    public static go_1 RY() {
        go_1 go_12;
        try {
            go_12 = (go_1)uG.adr();
            go_12.a(uG);
        }
        catch (Exception exception) {
            go_12 = new go_1();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UIChatOperationMessage : " + exception.getMessage()));
        }
        return go_12;
    }

    public int getId() {
        return 16385;
    }

    public void aQ(String string) {
        this.GS = string;
    }

    public void setPassword(String string) {
        try {
            this.GT = new String(string.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.GT = string;
        }
    }

    public void a(NM nM) {
        this.bcs = nM;
    }

    public void a(Boolean bl2) {
        this.bct = bl2;
    }

    public NM RZ() {
        return this.bcs;
    }

    public String qc() {
        return this.GS;
    }

    public String getPassword() {
        return this.GT;
    }

    public Boolean Sa() {
        return this.bct;
    }

    /* synthetic */ go_1(ht_1 ht_12) {
        this();
    }
}

