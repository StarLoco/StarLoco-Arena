/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public abstract class Cs
implements sq_1 {
    private static final boolean aLx = false;
    private static final Logger a = Logger.getLogger(Cs.class);
    private final ArrayList aLy = new ArrayList(0);

    public abstract JG h();

    private boolean a(JG jG) {
        int n2 = this.aLy.size();
        for (int j = 0; j < n2; ++j) {
            if (this.aLy.get(j) != jG) continue;
            return true;
        }
        return false;
    }

    public void s(Object object) {
        try {
            ((JG)object).b();
        }
        catch (Exception exception) {
            a.error((Object)"Exception on checkOut : ", (Throwable)exception);
        }
    }

    public void t(Object object) {
        try {
            ((JG)object).j();
        }
        catch (Exception exception) {
            a.error((Object)"Exception on checkIn : ", (Throwable)exception);
            throw new RuntimeException(exception.toString());
        }
    }

    public void u(Object object) {
    }

    public boolean v(Object object) {
        return true;
    }
}

