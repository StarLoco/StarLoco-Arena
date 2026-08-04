/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from pJ
 */
public class pj_1
implements Serializable {
    private static final long serialVersionUID = -4832130320500439038L;
    final StackTraceElement acp;
    private transient String acq;
    private abl_0 acr;

    pj_1(StackTraceElement stackTraceElement) {
        if (stackTraceElement == null) {
            throw new IllegalArgumentException("ste cannot be null");
        }
        this.acp = stackTraceElement;
    }

    public String uo() {
        if (this.acq == null) {
            this.acq = "\tat " + this.acp.toString();
        }
        return this.acq;
    }

    public StackTraceElement getStackTraceElement() {
        return this.acp;
    }

    void a(abl_0 abl_02) {
        this.acr = abl_02;
    }

    public abl_0 up() {
        return this.acr;
    }

    public int hashCode() {
        return this.acp.hashCode();
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
        pj_1 pj_12 = (pj_1)object;
        if (!this.acp.equals(pj_12.acp)) {
            return false;
        }
        return !(this.acr == null ? pj_12.acr != null : !this.acr.equals(pj_12.acr));
    }

    public String toString() {
        return this.uo();
    }
}

