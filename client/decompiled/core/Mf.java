/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.EnumMap;
import org.apache.log4j.Logger;

public class Mf
implements aho_0 {
    protected static Logger a = Logger.getLogger(Mf.class);
    public static final Mf btd = new Mf();
    private final EnumMap bte = new EnumMap(amA.class);
    private static String[] ce;

    private Mf() {
    }

    public void a(amA amA2, boolean bl2) {
        this.bte.put(amA2, bl2);
    }

    public boolean a(amA amA2) {
        Boolean bl2 = (Boolean)this.bte.get((Object)amA2);
        if (bl2 != null) {
            return bl2;
        }
        return false;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        return this.a(amA.iI(string));
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    static {
        amA[] amAArray = amA.values();
        ce = new String[amAArray.length];
        for (int j = 0; j < amAArray.length; ++j) {
            Mf.ce[j] = amAArray[j].getPropertyName();
        }
    }
}

