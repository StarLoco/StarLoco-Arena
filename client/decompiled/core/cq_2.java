/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from cQ
 */
public abstract class cq_2
extends ke {
    private static Logger a = Logger.getLogger(cq_2.class);
    protected int jH;

    public int getModifiers() {
        return this.jH;
    }

    public void setModifiers(int n2) {
        this.jH = n2;
    }

    public void j() {
        super.j();
        this.jH = 0;
    }
}

