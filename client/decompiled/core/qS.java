/*
 * Decompiled with CFR 0.152.
 */
public class qS
extends anz_1 {
    ki_1 afz;

    qS() {
        super(2);
    }

    public ki_1 we() {
        return this.afz;
    }

    public void b(ki_1 ki_12) {
        this.afz = ki_12;
    }

    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        if (!(object instanceof qS)) {
            return false;
        }
        qS qS2 = (qS)object;
        return this.afz != null ? this.afz.equals(qS2.afz) : qS2.afz == null;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.afz != null) {
            stringBuffer.append("CompositeNode(" + this.afz + ")");
        } else {
            stringBuffer.append("CompositeNode(no child)");
        }
        stringBuffer.append(this.pt());
        return stringBuffer.toString();
    }
}

