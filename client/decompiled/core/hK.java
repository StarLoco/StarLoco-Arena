/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

public class hK
implements Serializable {
    private static final long serialVersionUID = 5028223666108713696L;
    final cr_0 wm;
    final String name;

    public hK(String string, ahu_0 ahu_02) {
        this.name = string;
        assert (ahu_02.aUl() != null);
        this.wm = ahu_02.aUl();
    }

    public cr_0 kQ() {
        return this.wm;
    }

    public String getName() {
        return this.name;
    }
}

