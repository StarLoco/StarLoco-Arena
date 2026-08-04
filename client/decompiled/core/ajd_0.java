/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ankamagames.dofusarena.common.binaryStorage.RecoverCardsListBinaryStorable
 */
import com.ankamagames.dofusarena.common.binaryStorage.RecoverCardsListBinaryStorable;
import java.nio.ByteBuffer;

/*
 * Renamed from ajD
 */
public class ajd_0
extends lJ {
    public static final RecoverCardsListBinaryStorable cAG = null;
    private static final short fn = 1;
    private long nD;
    private short UH;
    private byte UI;
    private byte cAH;

    public ajd_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVf.getId();
    }

    public byte[] cr() {
        int n2 = 12;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.nD);
        byteBuffer.putShort(this.UH);
        byteBuffer.put(this.UI);
        byteBuffer.put(this.cAH);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.nD = byteBuffer.getLong();
            this.UH = byteBuffer.getShort();
            this.UI = byteBuffer.get();
            this.cAH = byteBuffer.get();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge.");
        }
    }

    public lJ cs() {
        return new ajd_0();
    }

    public long getId() {
        return this.nD;
    }

    public short tz() {
        return this.UH;
    }

    public byte tA() {
        return this.UI;
    }

    public byte azi() {
        return this.cAH;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public void bK(short s) {
        this.UH = s;
    }

    public void aF(byte by) {
        this.UI = by;
    }

    public void aG(byte by) {
        this.cAH = by;
    }
}

