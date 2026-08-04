/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

/*
 * Renamed from LE
 */
public final class le_2
implements bv_2 {
    private int rv;
    private String bsc;
    private String bsd;
    private long bse;

    public le_2() {
    }

    public le_2(int n2, String string, String string2, long l2) {
        this.rv = n2;
        this.bsc = string != null ? string.intern() : null;
        this.bsd = string2 != null ? string2.intern() : null;
        this.bse = l2;
    }

    public int getType() {
        return this.rv;
    }

    public void setType(int n2) {
        this.rv = n2;
    }

    public String getIndexName() {
        return this.bsc;
    }

    public void fe(String string) {
        this.bsc = string;
    }

    public String XW() {
        return this.bsd;
    }

    public void ff(String string) {
        this.bsd = string;
    }

    public long getPosition() {
        return this.bse;
    }

    public void bZ(long l2) {
        this.bse = l2;
    }

    public void write(DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.rv);
        dataOutputStream.writeUTF(this.bsc);
        dataOutputStream.writeUTF(this.bsd);
        dataOutputStream.writeLong(this.bse);
    }

    public void read(DataInputStream dataInputStream) {
        this.rv = dataInputStream.readInt();
        this.bsc = dataInputStream.readUTF().intern();
        this.bsd = dataInputStream.readUTF().intern();
        this.bse = dataInputStream.readLong();
    }
}

