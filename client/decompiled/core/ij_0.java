/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from Ij
 */
public final class ij_0
extends and_1
implements gx_2 {
    private String OA = null;

    public ij_0() {
    }

    public ij_0(Reader reader) {
        super(reader);
    }

    public int read() {
        int n2 = -1;
        if (this.OA != null && this.OA.length() == 0) {
            this.OA = null;
        }
        if (this.OA != null) {
            n2 = this.OA.charAt(0);
            this.OA = this.OA.substring(1);
            if (this.OA.length() == 0) {
                this.OA = null;
            }
        } else {
            this.OA = this.aCh();
            if (this.OA == null || this.OA.length() == 0) {
                n2 = -1;
            } else {
                UI uI = this.TP();
                this.OA = uI.fZ(this.OA);
                return this.read();
            }
        }
        return n2;
    }

    public Reader b(Reader reader) {
        ij_0 ij_02 = new ij_0(reader);
        ij_02.l(this.TP());
        return ij_02;
    }
}

