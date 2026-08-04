/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from Hx
 */
public abstract class hx_1
extends avg
implements R {
    private String bfe = null;

    public void eC(String string) {
        if (this.bfe == null) {
            this.bfe = string;
        }
    }

    public String getError() {
        return this.bfe;
    }

    public void dQ() {
        if (this.aId()) {
            ((hx_1)this.aIg()).dQ();
        }
    }

    public void validate() {
        if (this.getError() == null) {
            this.dQ();
        }
        if (this.getError() != null) {
            throw new eq_2(this.bfe);
        }
    }

    public abstract boolean a(File var1, String var2, File var3);
}

