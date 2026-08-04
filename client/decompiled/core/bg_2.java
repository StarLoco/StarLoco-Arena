/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from BG
 */
public class bg_2 {
    private static final Logger a = Logger.getLogger(bg_2.class);
    public final short ayK;
    public final short aJj;
    public final vP aJk = new vP();

    public bg_2(short s, vP vP2, short s2) {
        this.ayK = s;
        this.aJk.set(vP2.Cf());
        this.aJj = s2;
    }

    bg_2(ByteBuffer byteBuffer) {
        this.ayK = byteBuffer.getShort();
        this.aJk.set(byteBuffer.getInt());
        this.aJj = byteBuffer.getShort();
    }

    public void a(aij_1 aij_12) {
        aij_12.writeShort(this.ayK);
        aij_12.writeInt(this.aJk.Cf());
        aij_12.writeShort(this.aJj);
    }
}

