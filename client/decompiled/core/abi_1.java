/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from abI
 */
public class abi_1
implements aE {
    protected static final Logger a = Logger.getLogger((String)"mainLog.fightLog");
    private static final abi_1 ciy = new abi_1();

    public static abi_1 aqv() {
        return ciy;
    }

    public void a(OZ oZ) {
        if (oZ instanceof aej_0) {
            ((aej_0)oZ).a(this);
        } else {
            a.error((Object)"ObservationEndEvent utilis\u00e9 avec un mauvais time event handler");
        }
    }

    abi_1() {
    }
}

