/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kI
 */
public class ki_1 {
    static final int LITERAL = 0;
    static final int Fk = 1;
    static final int Fl = 2;
    final int type;
    final Object value;
    ki_1 Fm;

    ki_1(int n2) {
        this(n2, null);
    }

    ki_1(int n2, Object object) {
        this.type = n2;
        this.value = object;
    }

    public int getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public ki_1 ps() {
        return this.Fm;
    }

    public void a(ki_1 ki_12) {
        this.Fm = ki_12;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ki_1)) {
            return false;
        }
        ki_1 ki_12 = (ki_1)object;
        return this.type == ki_12.type && (this.value != null ? this.value.equals(ki_12.value) : ki_12.value == null) && (this.Fm != null ? this.Fm.equals(ki_12.Fm) : ki_12.Fm == null);
    }

    String pt() {
        if (this.Fm != null) {
            return " -> " + this.Fm;
        }
        return "";
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        switch (this.type) {
            case 0: {
                stringBuffer.append("LITERAL(" + this.value + ")");
                break;
            }
            default: {
                stringBuffer.append(super.toString());
            }
        }
        stringBuffer.append(this.pt());
        return stringBuffer.toString();
    }
}

