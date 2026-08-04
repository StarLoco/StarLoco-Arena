/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xZ
 */
public class xz_2
extends acz_0 {
    StringBuffer buf = new StringBuffer();
    akn_1 aAc;

    public String h(Object object) {
        if (this.buf.capacity() > 1024) {
            this.buf = new StringBuffer(256);
        } else {
            this.buf.setLength(0);
        }
        akn_1 akn_12 = this.aAc;
        while (akn_12 != null) {
            akn_12.a(this.buf, object);
            akn_12 = akn_12.cDW;
        }
        return this.buf.toString();
    }

    public void c(akn_1 akn_12) {
        this.aAc = akn_12;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CompositeConverter<");
        if (this.dut != null) {
            stringBuffer.append(this.dut);
        }
        if (this.aAc != null) {
            stringBuffer.append(", children: " + this.aAc);
        }
        stringBuffer.append(">");
        return stringBuffer.toString();
    }
}

