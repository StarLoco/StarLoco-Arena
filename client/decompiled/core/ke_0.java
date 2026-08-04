/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import org.apache.log4j.Logger;

/*
 * Renamed from kE
 */
public class ke_0 {
    private static final Logger a = Logger.getLogger(ke_0.class);
    private final lb_0 EZ = new lb_0();
    private int ug;
    private String eA;
    private static final ke_0 Fa = new ke_0();

    public static ke_0 pk() {
        return Fa;
    }

    private ke_0() {
    }

    public String getFileName() {
        return this.eA;
    }

    public void setFile(String string) {
        this.eA = string;
    }

    public void aH(int n2) {
        this.ug = n2;
    }

    public void load() {
        this.clear();
        if (this.eA == null) {
            return;
        }
        try {
            acf acf2 = new acf(vq_2.gm(String.format(this.eA, this.ug)));
            this.b(acf2);
            acf2.close();
        }
        catch (IOException iOException) {
            a.error((Object)("Error while loading AmbianceZone file : " + this.eA + " (world " + this.ug + ") : " + String.format(this.eA, this.ug)), (Throwable)iOException);
        }
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/AmbienceZoneBank.load must not be null");
        }
        int n2 = acf2.readInt();
        this.EZ.ensureCapacity(n2);
        for (int j = 0; j < n2; ++j) {
            afe_2 afe_22 = new afe_2();
            afe_22.b(acf2);
            assert (!this.EZ.contains(afe_22.dHq));
            this.EZ.c(afe_22.dHq, afe_22);
        }
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/AmbienceZoneBank.save must not be null");
        }
        ll_0 ll_02 = this.EZ.pK();
        aij_12.writeInt(this.EZ.size());
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((afe_2)ll_02.value()).a(aij_12);
        }
    }

    public final void a(afe_2 afe_22) {
        assert (!this.EZ.contains(afe_22.dHq));
        this.EZ.c(afe_22.dHq, afe_22);
    }

    public afe_2 bP(int n2) {
        return (afe_2)this.EZ.get(n2);
    }

    public void clear() {
        this.EZ.clear();
    }
}

