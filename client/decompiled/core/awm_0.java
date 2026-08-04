/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

/*
 * Renamed from awm
 */
public class awm_0 {
    public static String dhD = "WALK";
    public static String dhE = "RUN";
    public static String dhF = "SLIDE";
    public static String dhG = "SWIM";
    public static String dhH = "WALK_CARRY";
    public static String dhI = "THROW";
    public static String dhJ = "CUSTOM_WALK";
    private static final awm_0 dhK = new awm_0();
    private final HashMap dhL = new HashMap();

    public static awm_0 aJy() {
        return dhK;
    }

    private awm_0() {
        this.a(dhD, new awn(this));
        this.a(dhE, new awt(this));
        this.a(dhF, new awv(this));
        this.a(dhH, new awr_0(this));
        this.a(dhI, new aws_0(this));
        this.a(dhJ, new awA(this));
    }

    public void a(String string, dp_1 dp_12) {
        this.dhL.put(string, dp_12);
    }

    public jp_1 jU(String string) {
        dp_1 dp_12 = (dp_1)this.dhL.get(string);
        if (dp_12 == null) {
            return null;
        }
        return dp_12.fN();
    }
}

