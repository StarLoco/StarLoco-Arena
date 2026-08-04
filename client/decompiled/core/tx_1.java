/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tx
 */
public class tx_1
implements aho_0 {
    public static final String amZ = "showAllEvent";
    public static final String ana = "tournamentEventFilter";
    public static final String anb = "broadcastEventFilter";
    public static final String anc = "maintenanceEventFilter";
    public static final String[] ce = new String[]{"showAllEvent", "tournamentEventFilter", "broadcastEventFilter", "maintenanceEventFilter"};
    private final jg_0 and = new jg_0();

    public String[] getFields() {
        return ce;
    }

    public tx_1() {
        this.zF();
    }

    public void zF() {
        this.and.clear();
        this.and.add(2);
        this.and.add(3);
        this.and.add(1);
    }

    public void dG(int n2) {
        this.and.add(n2);
    }

    public void zG() {
        this.and.clear();
    }

    public void dH(int n2) {
        for (int j = 0; j < this.and.size(); ++j) {
            if (this.and.get(j) != n2) continue;
            this.and.bv(j);
            return;
        }
    }

    public boolean contains(int n2) {
        return this.and.contains(n2);
    }

    public boolean isFull() {
        return this.and.contains(2) && this.and.contains(3) && this.and.contains(1);
    }

    public Object getFieldValue(String string) {
        if (string.equals(amZ)) {
            return this.isFull();
        }
        if (string.equals(ana)) {
            return this.and.contains(2);
        }
        if (string.equals(anc)) {
            return this.and.contains(3);
        }
        if (string.equals(anb)) {
            return this.and.contains(1);
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

