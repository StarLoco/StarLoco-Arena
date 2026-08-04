/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.apache.log4j.Logger;

/*
 * Renamed from Wo
 */
public final class wo_0
implements JG,
bv_2 {
    private static final Logger a = Logger.getLogger(wo_0.class);
    public int Gc;
    public long bTX;
    public long bTY;
    public String value;
    private static final acl_0 uG = new ym_0(new Ej());

    private wo_0() {
    }

    public wo_0(String string, int n2, long l2, long l3) {
        this.value = string;
        this.Gc = n2;
        this.bTX = l2;
        this.bTY = l3;
    }

    public void write(DataOutputStream dataOutputStream) {
        dataOutputStream.writeUTF(this.value);
        dataOutputStream.writeInt(this.Gc);
        dataOutputStream.writeLong(this.bTX);
        dataOutputStream.writeLong(this.bTY);
    }

    public void read(DataInputStream dataInputStream) {
        this.value = dataInputStream.readUTF();
        this.Gc = dataInputStream.readInt();
        this.bTX = dataInputStream.readLong();
        this.bTY = dataInputStream.readLong();
    }

    public static wo_0 a(String string, int n2, long l2, long l3) {
        wo_0 wo_02;
        try {
            wo_02 = (wo_0)uG.adr();
        }
        catch (Exception exception) {
            wo_02 = new wo_0();
            a.error((Object)"Erreur lors d'un checkout d'un IndexEntry", (Throwable)exception);
        }
        wo_02.value = string;
        wo_02.Gc = n2;
        wo_02.bTX = l2;
        wo_02.bTY = l3;
        return wo_02;
    }

    public static wo_0 ajd() {
        wo_0 wo_02;
        try {
            wo_02 = (wo_0)uG.adr();
        }
        catch (Exception exception) {
            wo_02 = new wo_0();
            a.error((Object)"Erreur lors d'un checkout d'un IndexEntry", (Throwable)exception);
        }
        return wo_02;
    }

    public void release() {
        try {
            uG.af(this);
        }
        catch (Exception exception) {
            a.error((Object)"Erreur lors d'un release d'un IndexEntry", (Throwable)exception);
        }
    }

    public void b() {
    }

    public void j() {
        this.value = null;
    }

    /* synthetic */ wo_0(Ej ej) {
        this();
    }
}

