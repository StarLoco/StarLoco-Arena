/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

class Lb
extends ov_2 {
    private final avo_0 bpF;
    private final short bpN;
    private final short bpO;
    private final zh_2 rW;

    Lb(zh_2 zh_22, short s, avo_0 avo_02, short s2, short s3) {
        super(s);
        this.rW = zh_22;
        this.bpF = avo_02;
        this.bpN = s2;
        this.bpO = s3;
    }

    protected void b(DataOutputStream dataOutputStream) {
        this.bpF.a(dataOutputStream, this.bpN, this.bpO);
    }
}

