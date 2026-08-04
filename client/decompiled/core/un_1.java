/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from uN
 */
public class un_1
implements Serializable {
    private static final long serialVersionUID = -2891376879381358469L;
    String aqS;
    pj_1 aqT;
    final ama_0 aqU;

    public un_1(String string) {
        this.aqS = string;
        this.aqU = ama_0.dXm;
    }

    public un_1(StackTraceElement stackTraceElement) {
        this.aqT = new pj_1(stackTraceElement);
        this.aqU = ama_0.dXn;
    }

    public ama_0 AS() {
        return this.aqU;
    }

    public pj_1 AT() {
        return this.aqT;
    }

    public String toString() {
        switch (this.aqU) {
            case dXm: {
                return this.aqS;
            }
            case dXn: {
                return this.aqT.uo();
            }
        }
        throw new IllegalStateException("Unreachable code");
    }

    public int hashCode() {
        switch (this.aqU) {
            case dXm: {
                return this.aqS.hashCode();
            }
            case dXn: {
                return this.aqT.hashCode();
            }
        }
        throw new IllegalStateException("Unreachable code");
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        un_1 un_12 = (un_1)object;
        switch (this.aqU) {
            case dXm: {
                if (this.aqS == null) {
                    return un_12.aqS == null;
                }
                return this.aqS.equals(un_12.aqS);
            }
            case dXn: {
                return this.aqT.equals(un_12.aqT);
            }
        }
        throw new IllegalStateException("Unreachable code");
    }
}

