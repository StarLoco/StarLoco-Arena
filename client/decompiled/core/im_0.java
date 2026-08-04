/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;
import java.util.List;

/*
 * Renamed from IM
 */
public class im_0
implements ff_1 {
    private ahu_0 bic;

    public im_0(ahu_0 ahu_02) {
        this.bic = ahu_02;
    }

    public ahu_0 ON() {
        return this.OO();
    }

    public ahu_0 OO() {
        return this.bic;
    }

    public ahu_0 dS(String string) {
        return this.bic;
    }

    public List OP() {
        return Arrays.asList(this.bic.getName());
    }

    public ahu_0 dR(String string) {
        if (this.bic.getName().equals(string)) {
            return this.bic;
        }
        return null;
    }
}

