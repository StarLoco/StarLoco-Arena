/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aly
 */
public final class aly_1
extends akf_1 {
    private static final Logger a = Logger.getLogger(aly_1.class);
    private static final aly_1 cFr = new aly_1();

    public static aly_1 aAQ() {
        return cFr;
    }

    private aly_1() {
        super("data.bdat", "indexes.bdat", true);
        this.setName("SimpleBinaryStorage");
    }

    public String toString() {
        return this.getName();
    }
}

