/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import java.util.Map;

/*
 * Renamed from CR
 */
public class cr_0
implements Serializable {
    private static final long serialVersionUID = 5488023392483144387L;
    final String name;
    final Map hY;

    public cr_0(ahu_0 ahu_02) {
        this.name = ahu_02.getName();
        this.hY = ahu_02.eb();
    }

    public String getName() {
        return this.name;
    }

    public Map Lm() {
        return this.hY;
    }
}

