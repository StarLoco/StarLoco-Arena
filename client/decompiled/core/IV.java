/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

public final class IV {
    private static final Logger a = Logger.getLogger(IV.class);
    private static final IV biF = new IV();
    private String eA;
    private final zm_1 biG = new zm_1();
    private short biH = 0;

    private IV() {
    }

    public static IV Vd() {
        return biF;
    }

    public String getFileName() {
        return this.eA;
    }

    public void setFile(String string) {
        this.eA = string;
    }

    public short Ve() {
        short s = this.biH;
        this.biH = (short)(s + 1);
        return s;
    }

    public void load() {
        this.clear();
        if (this.eA == null) {
            return;
        }
        try {
            acf acf2 = new acf(vq_2.gm(String.format(this.eA, new Object[0])));
            this.b(acf2);
            acf2.close();
        }
        catch (IOException iOException) {
            a.error((Object)("Error while loading PlayList file : " + this.eA), (Throwable)iOException);
        }
    }

    public void save() {
        if (this.eA == null) {
            return;
        }
        try {
            FileOutputStream fileOutputStream = vq_2.gw(this.eA);
            aij_1 aij_12 = new aij_1(fileOutputStream);
            this.a(aij_12);
            aij_12.close();
            ((OutputStream)fileOutputStream).close();
        }
        catch (IOException iOException) {
            a.error((Object)("Error while saving PlayList file : " + this.eA), (Throwable)iOException);
        }
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/PlayListManager.load must not be null");
        }
        short s = acf2.readShort();
        this.biG.ensureCapacity(s);
        for (short s2 = 0; s2 < s; s2 = (short)(s2 + 1)) {
            aix_0 aix_02 = new aix_0();
            aix_02.b(acf2);
            this.a(aix_02);
        }
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/PlayListManager.save must not be null");
        }
        aij_12.writeShort((short)this.biG.size());
        dk_1 dk_12 = this.biG.Gi();
        while (dk_12.hasNext()) {
            dk_12.fK();
            ((aix_0)dk_12.value()).a(aij_12);
        }
    }

    public final void a(aix_0 aix_02) {
        this.biG.b(aix_02.aVe(), aix_02);
    }

    public aix_0 aE(short s) {
        return (aix_0)this.biG.an(s);
    }

    public short b(aix_0 aix_02) {
        dk_1 dk_12 = this.biG.Gi();
        while (dk_12.hasNext()) {
            dk_12.fK();
            if (!((aix_0)dk_12.value()).c(aix_02)) continue;
            return dk_12.fL();
        }
        return -1;
    }

    public void clear() {
        this.biG.clear();
    }
}

