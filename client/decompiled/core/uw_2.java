/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from UW
 */
public class uw_2
extends ael_2 {
    private static ArrayList bRX;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        bRX = new ArrayList();
        for (int j = n2 - 1; 0 <= j; --j) {
            bRX.add(vy_0.x(byteBuffer));
        }
        return true;
    }

    public static ArrayList ahZ() {
        return bRX;
    }

    public int getId() {
        return 28622;
    }
}

