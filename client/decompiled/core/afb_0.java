/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from aFB
 */
public final class afb_0
extends and_1
implements gx_2 {
    private int dHn = -1;
    private boolean dHo = false;
    private boolean dHp = false;

    public afb_0() {
    }

    public afb_0(Reader reader) {
        super(reader);
    }

    public int read() {
        int n2 = -1;
        if (this.dHn != -1) {
            n2 = this.dHn;
            this.dHn = -1;
        } else {
            n2 = this.in.read();
            if (n2 == 34 && !this.dHp) {
                this.dHo = !this.dHo;
                this.dHp = false;
            } else if (n2 == 92) {
                this.dHp = !this.dHp;
            } else {
                this.dHp = false;
                if (!this.dHo && n2 == 47) {
                    n2 = this.in.read();
                    if (n2 == 47) {
                        while (n2 != 10 && n2 != -1 && n2 != 13) {
                            n2 = this.in.read();
                        }
                    } else if (n2 == 42) {
                        while (n2 != -1) {
                            n2 = this.in.read();
                            if (n2 != 42) continue;
                            n2 = this.in.read();
                            while (n2 == 42) {
                                n2 = this.in.read();
                            }
                            if (n2 != 47) continue;
                            n2 = this.read();
                            break;
                        }
                    } else {
                        this.dHn = n2;
                        n2 = 47;
                    }
                }
            }
        }
        return n2;
    }

    public Reader b(Reader reader) {
        afb_0 afb_02 = new afb_0(reader);
        return afb_02;
    }
}

