/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class YP
extends ue_0 {
    private boolean cbE = false;
    private final aLO bA = new aLO();
    private aLO bB = new aLO();
    private final ArrayList bw = new ArrayList();
    private final ArrayList bx = new ArrayList();
    private final ArrayList by = new ArrayList();
    private final ArrayList bz = new ArrayList();
    private final cp_2 cbF = new cp_2();
    private int cbG;
    private byte cbH;
    private byte cbI;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 9, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        boolean bl2 = this.cbE = byteBuffer.get() == 1;
        if (this.cbE) {
            short s = byteBuffer.getShort();
            if (s > 0) {
                byte[] byArray2 = new byte[s];
                byteBuffer.get(byArray2);
                this.c(byArray2, true);
            }
        } else {
            byte[] byArray3;
            PlayerStatisticsReport playerStatisticsReport;
            short s;
            short s2;
            long l2;
            int n2;
            int n3;
            int n4;
            int n5 = byteBuffer.getInt();
            for (n4 = n5 - 1; 0 <= n4; --n4) {
                this.bA.m(byteBuffer.getLong(), byteBuffer.getInt());
            }
            n4 = byteBuffer.getInt();
            for (n3 = n4 - 1; 0 <= n3; --n3) {
                this.bB.m(byteBuffer.getLong(), byteBuffer.getInt());
            }
            n3 = byteBuffer.get();
            for (n2 = 0; n2 < n3; ++n2) {
                l2 = byteBuffer.getLong();
                s2 = byteBuffer.getShort();
                s = byteBuffer.getShort();
                playerStatisticsReport = null;
                if (s > 0) {
                    byArray3 = new byte[s];
                    byteBuffer.get(byArray3);
                    playerStatisticsReport = (PlayerStatisticsReport)arq_0.aEv().aa(byArray3);
                }
                this.bw.add(new anp_2(this, l2, s2, playerStatisticsReport));
            }
            n3 = byteBuffer.get();
            for (n2 = 0; n2 < n3; ++n2) {
                l2 = byteBuffer.getLong();
                s2 = byteBuffer.getShort();
                s = byteBuffer.getShort();
                playerStatisticsReport = null;
                if (s > 0) {
                    byArray3 = new byte[s];
                    byteBuffer.get(byArray3);
                    playerStatisticsReport = (PlayerStatisticsReport)arq_0.aEv().aa(byArray3);
                }
                this.bx.add(new anp_2(this, l2, s2, playerStatisticsReport));
            }
            n2 = byteBuffer.getShort();
            if (n2 > 0) {
                byte[] byArray4 = new byte[n2];
                byteBuffer.get(byArray4);
                this.c(byArray4, false);
            }
            if ((n2 = (int)byteBuffer.getShort()) > 0) {
                byte[] byArray5 = new byte[n2];
                byteBuffer.get(byArray5);
                this.c(byArray5, true);
            }
            int n6 = byteBuffer.get();
            for (int j = 0; j < n6; ++j) {
                long l3 = byteBuffer.getLong();
                short s3 = byteBuffer.getShort();
                byArray3 = new byte[s3];
                byteBuffer.get(byArray3);
                this.cbF.a(l3, new OW(byArray3));
            }
            this.cbI = byteBuffer.get();
            this.cbH = byteBuffer.get();
            this.cbG = byteBuffer.getInt();
        }
        return true;
    }

    public int getId() {
        return 8300;
    }

    public boolean amO() {
        return this.cbE;
    }

    public ArrayList amP() {
        return this.bw;
    }

    public ArrayList amQ() {
        return this.bx;
    }

    public ArrayList amR() {
        return this.by;
    }

    public ArrayList amS() {
        return this.bz;
    }

    public aLO amT() {
        return this.bA;
    }

    public aLO amU() {
        return this.bB;
    }

    public int M() {
        return 0;
    }

    public cp_2 amV() {
        return this.cbF;
    }

    public int amW() {
        return this.cbG;
    }

    public byte amX() {
        return this.cbH;
    }

    public byte amY() {
        return this.cbI;
    }

    public jl_0 N() {
        return jl_0.bjI;
    }

    private void c(byte[] byArray, boolean bl2) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            ArrayList arrayList = bl2 ? this.by : this.bz;
            int n3 = byteBuffer.get();
            for (int i2 = 0; i2 < n3; ++i2) {
                wy_2 wy_22 = new wy_2(byteBuffer.getInt());
                arrayList.add(wy_22);
            }
        }
    }
}

