/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from PF
 */
public class pf_0 {
    private Object bEf;
    private Object bEg;

    public pf_0() {
    }

    public pf_0(Object object, Object object2) {
        this.bEf = object;
        this.bEg = object2;
    }

    public Object getFirst() {
        return this.bEf;
    }

    public void ac(Object object) {
        this.bEf = object;
    }

    public Object acl() {
        return this.bEg;
    }

    public void ad(Object object) {
        this.bEg = object;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof pf_0)) {
            return false;
        }
        pf_0 pf_02 = (pf_0)object;
        if (this.bEf != null ? !this.bEf.equals(pf_02.bEf) : pf_02.bEf != null) {
            return false;
        }
        return !(this.bEg != null ? !this.bEg.equals(pf_02.bEg) : pf_02.bEg != null);
    }

    public int hashCode() {
        int n2 = this.bEf != null ? this.bEf.hashCode() : 0;
        n2 = 31 * n2 + (this.bEg != null ? this.bEg.hashCode() : 0);
        return n2;
    }

    public String toString() {
        return "ObjectPair{m_first=" + this.bEf + ", m_second=" + this.bEg + '}';
    }
}

