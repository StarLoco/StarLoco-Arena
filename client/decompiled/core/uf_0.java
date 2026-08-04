/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.nio.ByteBuffer;

/*
 * Renamed from uF
 */
public class uf_0
extends ael_2 {
    private PlayerStatisticsReport aqJ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getShort() & 0xFFFF;
        byte[] byArray2 = new byte[n2];
        if (n2 > 0) {
            byteBuffer.get(byArray2);
        }
        this.aqJ = (PlayerStatisticsReport)arq_0.aEv().aa(byArray2);
        return true;
    }

    public int getId() {
        return 2401;
    }

    public PlayerStatisticsReport AK() {
        return this.aqJ;
    }
}

