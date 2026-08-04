/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from awY
 */
public class awy_0
implements aE {
    protected static final Logger a = Logger.getLogger((String)"mainLog.fightLog");
    private static final awy_0 diC = new awy_0();

    public static awy_0 aJK() {
        return diC;
    }

    public void a(OZ oZ) {
        if (oZ instanceof aej_0) {
            ((aej_0)oZ).a(this);
        } else {
            a.error((Object)"ObservationEndEvent utilis\u00e9 avec un mauvais time event handler");
        }
    }

    awy_0() {
    }
}

