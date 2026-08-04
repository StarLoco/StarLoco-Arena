/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aoH
 */
public class aoh_2
extends ael_2 {
    private sj_1 aMT = null;

    public boolean a(byte[] byArray) {
        sj_1 sj_12 = new sj_1();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        if (sj_12.b(byteBuffer, 4014)) {
            this.aMT = sj_12;
            azs_0.aLV().g("adminRight", aet_0.nD(this.aMT.aQc()));
        }
        return true;
    }

    public int getId() {
        return 2052;
    }

    public sj_1 Ln() {
        return this.aMT;
    }
}

