/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

/*
 * Renamed from akH
 */
public class akh_2 {
    private HashMap cDI = new HashMap();

    public HashMap azU() {
        return this.cDI;
    }

    public void b(pL pL2) {
        this.cDI.put(pL2.getName(), pL2);
    }

    public void b(Iterable iterable) {
        for (pL pL2 : iterable) {
            this.b(pL2);
        }
    }

    public boolean c(pL pL2) {
        return this.cDI.remove(pL2.getName()) != null;
    }

    public boolean iw(String string) {
        if (this.cDI.containsKey(string)) {
            this.cDI.remove(string);
            return true;
        }
        return false;
    }

    public pL ix(String string) {
        return (pL)this.cDI.get(string);
    }

    public String azV() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (pL pL2 : this.azU().values()) {
            stringBuilder.append(" +").append(pL2.getName()).append(" (");
            if (pL2.uq()) {
                stringBuilder.append("onLine");
            } else {
                stringBuilder.append("offLine");
            }
            stringBuilder.append(")\n");
        }
        return stringBuilder.toString();
    }

    public boolean contains(String string) {
        return this.cDI.containsKey(string);
    }
}

