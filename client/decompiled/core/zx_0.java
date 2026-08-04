/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from ZX
 */
public class zx_0
implements aho_0 {
    public static final String ces = "friend";
    public static final String cet = "ignore";
    public static final String ceu = "party";
    public static final String cev = "guild";
    public static final String[] ce = new String[]{"friend", "ignore", "party", "guild"};
    private ArrayList cew = new ArrayList();

    zx_0() {
        this.cew.add(ces);
        this.cew.add(cet);
        this.cew.add(ceu);
        this.cew.add(cev);
        azs_0.aLV().g("contact.list.filter", this);
    }

    public boolean hb(String string) {
        return this.cew.contains(string);
    }

    public void hc(String string) {
        if (!this.hb(string)) {
            this.cew.add(string);
        }
    }

    public void hd(String string) {
        if (this.hb(string)) {
            this.cew.remove(string);
        }
    }

    public boolean b(axa_0 axa_02) {
        if (this.cew.contains(ces) && axa_02.ci(axa_0.diQ)) {
            return true;
        }
        if (this.cew.contains(cet) && axa_02.ci(axa_0.diR)) {
            return true;
        }
        if (this.cew.contains(ceu) && axa_02.ci(axa_0.diS)) {
            return true;
        }
        return this.cew.contains(cev) && axa_02.ci(axa_0.czR);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        return this.cew.contains(string);
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

