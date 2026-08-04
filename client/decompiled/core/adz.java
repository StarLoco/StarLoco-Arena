/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.util.List;

public class adz {
    private final short aEp;
    private final short aao;
    private final short bBZ;
    private final List attributes;
    private final nw_2 bnq;

    public adz(nw_2 nw_22, short s, short s2, short s3, List list) {
        this.bnq = nw_22;
        this.aEp = s;
        this.aao = s2;
        this.bBZ = s3;
        this.attributes = list;
    }

    public nw_2 asW() {
        return this.bnq;
    }

    public short asX() {
        return this.aEp;
    }

    public short asY() {
        return this.aao;
    }

    public short abx() {
        return this.bBZ;
    }

    public ov_2[] asZ() {
        return this.attributes.toArray(new ov_2[this.attributes.size()]);
    }

    public void b(ov_2 ov_22) {
        this.attributes.add(ov_22);
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.aEp);
        dataOutputStream.writeShort(this.aao);
        dataOutputStream.writeShort(this.bBZ);
        nw_2.e(dataOutputStream, this.attributes);
    }
}

