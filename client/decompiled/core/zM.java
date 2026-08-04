/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;

public class zM {
    private String m_name;
    private boolean OD = false;
    private final ArrayList aGe = new ArrayList();

    public zM(String string) {
        this.m_name = string;
    }

    public aex dm(String string) {
        for (int j = this.aGe.size() - 1; j >= 0; --j) {
            String string2;
            aex aex2 = (aex)this.aGe.get(j);
            if (aex2 == null || (string2 = aex2.getId()) == null || !string2.equalsIgnoreCase(string)) continue;
            return (aex)this.aGe.get(j);
        }
        return null;
    }

    public void a(aex aex2) {
        if (aex2 != null) {
            aex2.C(this.getName());
            this.aGe.add(aex2);
        }
    }

    public void b(aex aex2) {
        this.aGe.remove(aex2);
    }

    public Collection GM() {
        return this.aGe;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public boolean isEnabled() {
        return this.OD;
    }

    public void setEnabled(boolean bl2) {
        this.OD = bl2;
    }
}

