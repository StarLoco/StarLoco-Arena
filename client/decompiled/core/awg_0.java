/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from awg
 */
public class awg_0
extends anz_1 {
    List cjt;

    awg_0(Object object) {
        super(1, object);
    }

    public List aJo() {
        return this.cjt;
    }

    public void r(List list) {
        this.cjt = list;
    }

    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        if (!(object instanceof awg_0)) {
            return false;
        }
        awg_0 awg_02 = (awg_0)object;
        return this.cjt != null ? ((Object)this.cjt).equals(awg_02.cjt) : awg_02.cjt == null;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.cjt == null) {
            stringBuffer.append("KeyWord(" + this.value + "," + this.cJi + ")");
        } else {
            stringBuffer.append("KeyWord(" + this.value + ", " + this.cJi + "," + this.cjt + ")");
        }
        stringBuffer.append(this.pt());
        return stringBuffer.toString();
    }
}

