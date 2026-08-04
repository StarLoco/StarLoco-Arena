/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Eu
 */
public class eu_1
extends afB {
    private ArrayList Hy;

    public eu_1(int n2) {
        this.Hy = new ArrayList(n2);
    }

    public eu_1(eu_1 eu_12) {
        this.Hy = new ArrayList(eu_12.Hy.size());
        this.Hy.addAll(eu_12.Hy);
    }

    public final int NM() {
        return this.Hy.size();
    }

    public final vP fx(int n2) {
        return (vP)this.Hy.get(n2);
    }

    public final void a(int n2, vP vP2) {
        try {
            this.Hy.set(n2, vP2);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            this.Hy.add(n2, vP2);
        }
    }

    public final void b(vP vP2) {
        this.Hy.add(vP2);
    }

    protected void delete() {
        this.Hy.clear();
        this.Hy = null;
    }
}

