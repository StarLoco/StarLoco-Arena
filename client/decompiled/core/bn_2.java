/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

/*
 * Renamed from bN
 */
public class bn_2 {
    public static afw_2 a(acf acf2, int n2) {
        byte[] byArray = acf2.jE(n2);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        return new afw_2(ImageIO.read(byteArrayInputStream), true);
    }

    public static afw_2 c(acf acf2) {
        int n2 = acf2.readUnsignedShort();
        int n3 = acf2.readUnsignedShort();
        int n4 = (int)acf2.readUnsignedInt();
        byte[] byArray = null;
        if (n4 > 0) {
            byArray = acf2.jE(n4);
        }
        return new afw_2(n2, n3, byArray);
    }
}

