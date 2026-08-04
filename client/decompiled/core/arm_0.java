/*
 * Decompiled with CFR 0.152.
 */
import java.text.MessageFormat;

/*
 * Renamed from arm
 */
public class arm_0 {
    short cPm;
    boolean ND;
    short cPn;
    boolean cPo;

    protected arm_0() {
    }

    public short aEk() {
        return this.cPn;
    }

    public boolean aEl() {
        return this.cPo;
    }

    public static arm_0 aEm() {
        return new arm_0();
    }

    public static arm_0 lQ(int n2) {
        arm_0 arm_02 = new arm_0();
        arm_02.cPm = (short)n2;
        return arm_02;
    }

    public arm_0 dS(boolean bl2) {
        this.ND = bl2;
        return this;
    }

    public arm_0 bS(short s) {
        this.cPn = s;
        return this;
    }

    public boolean aEn() {
        return this.ND;
    }

    public short aEo() {
        return this.cPm;
    }

    public arm_0 dT(boolean bl2) {
        this.cPo = bl2;
        return this;
    }

    public boolean aEp() {
        return this.cPm <= 0;
    }

    public String toString() {
        return MessageFormat.format("{0}T{1}{2}", this.cPm, this.ND ? "+" : "-", this.cPo ? "!" : "");
    }

    public void lR(int n2) {
        this.cPm = (short)n2;
    }
}

