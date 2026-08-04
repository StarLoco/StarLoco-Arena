/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/*
 * Renamed from adP
 */
class adp_2
extends mt_0 {
    private final Deflater cnx;
    private final Inflater cny;
    final /* synthetic */ ah_0 cnz;

    adp_2(ah_0 ah_02) {
        this.cnz = ah_02;
        super(null);
        this.cnx = new Deflater(1);
        this.cny = new Inflater();
    }

    public DataOutputStream a(FileOutputStream fileOutputStream) {
        this.cnx.reset();
        this.btU.c(new DeflaterOutputStream((OutputStream)fileOutputStream, this.cnx));
        return this.btU;
    }

    public DataInputStream a(FileInputStream fileInputStream) {
        this.cny.reset();
        this.btV.b(new InflaterInputStream(fileInputStream, this.cny));
        return this.btV;
    }

    public void b() {
    }

    public void j() {
    }
}

