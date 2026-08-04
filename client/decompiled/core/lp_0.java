/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from lp
 */
public class lp_0
extends bb_2 {
    public static final String GX = "FightRules";
    private int[] GY = ug_2.bQd;

    public lp_0(String string) {
        super((byte)0, GX, string);
    }

    public int[] qi() {
        return this.GY;
    }

    public void e(int[] nArray) {
        this.GY = nArray;
    }

    public byte[] cd() {
        byte[] byArray = eu;
        int n2 = this.GY.length;
        if (0 < n2) {
            int n3 = 4 + 4 * n2;
            ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
            byteBuffer.putInt(n2);
            for (int j = n2 - 1; 0 <= j; --j) {
                byteBuffer.putInt(this.GY[j]);
            }
            byArray = byteBuffer.array();
        }
        return byArray;
    }

    public void b(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        this.GY = new int[n2];
        for (int j = n2 - 1; 0 <= j; --j) {
            this.GY[j] = byteBuffer.getInt();
        }
    }
}

