/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.nio.ByteBuffer;

/*
 * Renamed from Kq
 */
public class kq_2
extends ael_2 {
    private PlayerStatisticsReport bnz;
    private String aiK;
    private long sB;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 1, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.sB = byteBuffer.getLong();
        byte[] byArray2 = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray2);
        this.aiK = new String(byArray2);
        byte[] byArray3 = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray3);
        this.bnz = (PlayerStatisticsReport)arq_0.aEv().aa(byArray3);
        return true;
    }

    public int getId() {
        return 2601;
    }

    public PlayerStatisticsReport WC() {
        return this.bnz;
    }

    public String xW() {
        return this.aiK;
    }

    public long mb() {
        return this.sB;
    }
}

