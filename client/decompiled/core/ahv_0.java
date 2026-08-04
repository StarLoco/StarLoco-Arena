/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from aHV
 */
public class ahv_0
implements aho_0 {
    public static final String dOl = "chat.viewList";
    public static final String[] ce = new String[]{"chat.viewList"};
    private static final ahv_0 dOm = new ahv_0();
    private int dOn = 0;
    private final List cMS = new ArrayList();

    public static ahv_0 aUv() {
        return dOm;
    }

    public ahv_0() {
        azs_0.aLV().g("chatViewManager", this);
    }

    public abl_2 aUw() {
        abl_2 abl_22 = new abl_2(this.dOn);
        ++this.dOn;
        this.cMS.add(abl_22);
        return abl_22;
    }

    public abl_2 aUx() {
        return (abl_2)this.cMS.get(0);
    }

    public abl_2 oI(int n2) {
        return (abl_2)this.cMS.get(n2);
    }

    public List aUy() {
        return this.cMS;
    }

    public void aUz() {
        for (abl_2 abl_22 : this.cMS) {
            abl_22.clean();
        }
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(dOl)) {
            return this.cMS.toArray();
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

