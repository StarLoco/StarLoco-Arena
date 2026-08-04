/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pr
 */
public class pr_1
extends alt_0 {
    public static final String TAG = "isNotEqual";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (object == null && (this.dE == null || this.dE.equals("null"))) {
            return false;
        }
        if (object instanceof String) {
            String string = (String)object;
            return !string.equalsIgnoreCase((String)this.dE);
        }
        if (object instanceof Integer) {
            return Gr.R(object) != Gr.R(this.dE);
        }
        if (object instanceof Float) {
            return Gr.getFloat(object) != Gr.getFloat(this.dE);
        }
        if (object instanceof Double) {
            return Gr.getDouble(object) != Gr.getDouble(this.dE);
        }
        if (object instanceof Short) {
            return Gr.getShort(object) != Gr.getShort(this.dE);
        }
        if (object instanceof Long) {
            return Gr.getLong(object) != Gr.getLong(this.dE);
        }
        if (object instanceof Byte) {
            return Gr.getByte(object) != Gr.getByte(this.dE);
        }
        return object != this.dE;
    }
}

