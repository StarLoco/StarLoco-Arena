/*
 * Decompiled with CFR 0.152.
 */
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/*
 * Renamed from SI
 */
class si_1
implements Enumeration {
    private boolean bLD;
    private final URL bLE;
    private final dj_2 bLF;

    si_1(dj_2 dj_22, URL uRL) {
        this.bLF = dj_22;
        this.bLE = uRL;
        this.bLD = true;
    }

    public boolean hasMoreElements() {
        return this.bLD;
    }

    public Object nextElement() {
        if (this.bLD) {
            this.bLD = false;
            return this.bLE;
        }
        throw new NoSuchElementException();
    }
}

