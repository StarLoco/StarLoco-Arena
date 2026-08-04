/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;

/*
 * Renamed from aNP
 */
public class anp_2 {
    private final long nD;
    private final int bMT;
    private final PlayerStatisticsReport dAZ;
    final /* synthetic */ YP dZT;

    public anp_2(YP yP, long l2, int n2, PlayerStatisticsReport playerStatisticsReport) {
        this.dZT = yP;
        this.nD = l2;
        this.bMT = n2;
        this.dAZ = playerStatisticsReport;
    }

    public long getId() {
        return this.nD;
    }

    public int getStrength() {
        return this.bMT;
    }

    public PlayerStatisticsReport aQs() {
        return this.dAZ;
    }
}

