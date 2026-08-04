/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCz
 */
public abstract class acz_0
extends akn_1 {
    static final int dus = 256;
    static final int MAX_CAPACITY = 1024;
    acd_1 dut;

    public final acd_1 aOp() {
        return this.dut;
    }

    public final void b(acd_1 acd_12) {
        if (this.dut != null) {
            throw new IllegalStateException("FormattingInfo has been already set");
        }
        this.dut = acd_12;
    }

    public final void a(StringBuffer stringBuffer, Object object) {
        String string = this.h(object);
        if (this.dut == null) {
            stringBuffer.append(string);
            return;
        }
        int n2 = this.dut.getMin();
        int n3 = this.dut.getMax();
        if (string == null) {
            if (0 < n2) {
                eg_0.spacePad(stringBuffer, n2);
            }
            return;
        }
        int n4 = string.length();
        if (n4 > n3) {
            if (this.dut.aOt()) {
                stringBuffer.append(string.substring(n4 - n3));
            } else {
                stringBuffer.append(string.substring(0, n3));
            }
        } else if (n4 < n2) {
            if (this.dut.aOs()) {
                eg_0.a(stringBuffer, string, n2);
            } else {
                eg_0.b(stringBuffer, string, n2);
            }
        } else {
            stringBuffer.append(string);
        }
    }
}

