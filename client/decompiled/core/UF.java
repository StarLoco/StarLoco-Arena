/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.log4j.Logger;

public class UF {
    private static final Logger a = Logger.getLogger(UF.class);
    private static final lb_0 bPY = new lb_0();

    private UF() {
    }

    public static void a(zl_1 zl_12) {
        assert (!bPY.bY(zl_12.getId())) : "Un \u00e9l\u00e9ment avec l'id " + zl_12.getId() + " existe d\u00e9j\u00e0";
        bPY.c(zl_12.getId(), zl_12);
    }

    public static void load(String string) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(vq_2.readFile(string));
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int n2 = byteBuffer.getInt();
        for (int j = 0; j < n2; ++j) {
            UF.a(new zl_1(byteBuffer));
        }
    }

    public static void aM(String string) {
        FileOutputStream fileOutputStream = vq_2.gw(string);
        aij_1 aij_12 = new aij_1(fileOutputStream);
        aij_12.writeInt(bPY.size());
        ll_0 ll_02 = bPY.pK();
        for (int j = bPY.size(); j > 0; --j) {
            ll_02.fK();
            ((zl_1)ll_02.value()).a(aij_12);
        }
        aij_12.close();
    }

    public static zl_1 ig(int n2) {
        return (zl_1)bPY.get(n2);
    }

    public static void clear() {
        bPY.clear();
    }

    public static int size() {
        return bPY.size();
    }
}

