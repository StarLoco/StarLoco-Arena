/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;

/*
 * Renamed from aIP
 */
final class aip_0
implements Enumeration {
    private final Enumeration dQI;
    private final Enumeration dQJ;

    public aip_0(Enumeration enumeration, Enumeration enumeration2) {
        this.dQI = enumeration;
        this.dQJ = enumeration2;
    }

    public boolean hasMoreElements() {
        return this.dQI.hasMoreElements() || this.dQJ.hasMoreElements();
    }

    public Object nextElement() {
        if (this.dQI.hasMoreElements()) {
            return this.dQI.nextElement();
        }
        return this.dQJ.nextElement();
    }
}

