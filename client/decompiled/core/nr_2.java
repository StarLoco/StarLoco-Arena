/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nr
 */
public class nr_2
extends pt_2 {
    public static final String TAG = "BitwiseOperation";
    public static final String Or = "and";
    public static final String Os = "or";
    public static final String Ot = "not";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        Number number = null;
        if (this.fZ != null) {
            if (this.fZ.equalsIgnoreCase(Or)) {
                if (object instanceof Integer) {
                    number = Gr.R(object) & Gr.R(this.dE);
                } else if (object instanceof Short) {
                    number = Gr.getShort(object) & Gr.getShort(this.dE);
                } else if (object instanceof Long) {
                    number = Gr.getLong(object) & Gr.getLong(this.dE);
                }
            }
            if (this.fZ.equalsIgnoreCase(Os)) {
                if (object instanceof Integer) {
                    number = Gr.R(object) | Gr.R(this.dE);
                } else if (object instanceof Short) {
                    number = Gr.getShort(object) | Gr.getShort(this.dE);
                } else if (object instanceof Long) {
                    number = Gr.getLong(object) | Gr.getLong(this.dE);
                }
            }
        }
        return this.dD.isValid(number);
    }
}

