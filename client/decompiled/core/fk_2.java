/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

/*
 * Renamed from Fk
 */
public class fk_2
extends ni_2 {
    private Vector aUy = new Vector();

    public fk_2(String string, Vector vector) {
        super(string);
        if (vector == null) {
            throw new IllegalArgumentException("choices must not be null");
        }
        this.aUy = vector;
    }

    public Vector OQ() {
        return this.aUy;
    }

    public boolean OR() {
        return this.aUy.contains(this.getInput()) || "".equals(this.getInput()) && this.getDefaultValue() != null;
    }
}

