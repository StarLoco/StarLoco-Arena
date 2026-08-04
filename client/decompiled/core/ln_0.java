/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ln
 */
class ln_0 {
    static final int bqk = 37;
    static final int bql = 40;
    static final int bqm = 41;
    static final int MINUS = 45;
    static final int DOT = 46;
    static final int bqn = 123;
    static final int bqo = 125;
    static final int LITERAL = 1000;
    static final int bqp = 1002;
    static final int Fk = 1004;
    static final int bqq = 1006;
    static final int EOF = Integer.MAX_VALUE;
    static ln_0 bqr = new ln_0(Integer.MAX_VALUE, "EOF");
    static ln_0 bqs = new ln_0(41);
    static ln_0 bqt = new ln_0(40);
    static ln_0 bqu = new ln_0(37);
    private final int type;
    private final Object value;

    public ln_0(int n2) {
        this(n2, null);
    }

    public ln_0(int n2, Object object) {
        this.type = n2;
        this.value = object;
    }

    public int getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        String string = null;
        switch (this.type) {
            case 37: {
                string = "%";
                break;
            }
            case 1002: {
                string = "FormatModifier";
                break;
            }
            case 1000: {
                string = "LITERAL";
                break;
            }
            case 1006: {
                string = "OPTION";
                break;
            }
            case 1004: {
                string = "KEYWORD";
                break;
            }
            case 41: {
                string = "RIGHT_PARENTHESIS";
                break;
            }
            case 40: {
                string = "LEFT_PARENTHESIS";
                break;
            }
            default: {
                string = "UNKNOWN";
            }
        }
        if (this.value == null) {
            return "Token(" + string + ")";
        }
        return "Token(" + string + ", \"" + this.value + "\")";
    }

    public int hashCode() {
        int n2 = this.type;
        n2 = 29 * n2 + (this.value != null ? this.value.hashCode() : 0);
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ln_0)) {
            return false;
        }
        ln_0 ln_02 = (ln_0)object;
        if (this.type != ln_02.type) {
            return false;
        }
        return !(this.value != null ? !this.value.equals(ln_02.value) : ln_02.value != null);
    }
}

