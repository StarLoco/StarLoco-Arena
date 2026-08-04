/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mg
 */
public class mg_1 {
    private float IN = 1000.0f;
    private boolean IO = false;
    private int IP;
    private float IQ = 1.0f;
    private float IR = 1.0f;
    private float IS = 1.0f;
    private float IT = 1.0f;
    private float IU = 1.0f;
    private float IV = 1.0f;
    private float IW = 1.0f;
    private float IX = 1.0f;
    private float IY = 1.0f;
    private float IZ = 1.0f;
    private float Ja = 1.0f;
    private float Jb = 1.0f;
    private final cp_2 Jc = new cp_2();
    private final aeh_2 Jd = new aeh_2(this, null);
    private static final mg_1 Je = new mg_1();

    private mg_1() {
    }

    public static mg_1 qV() {
        return Je;
    }

    public final zo_0 al(long l2) {
        return (zo_0)this.Jc.t(l2);
    }

    public void a(long l2, float f, float f2, float f3, float f4) {
        this.a(l2, f, f2, f3, f4, 1000.0f);
    }

    public void a(long l2, float f, float f2, float f3, float f4, float f5) {
        zo_0 zo_02 = (zo_0)this.Jc.t(l2);
        if (zo_02 == null) {
            zo_02 = new zo_0(null);
            this.Jc.a(l2, zo_02);
        }
        zo_02.a(f, f2, f3, f4, f5);
    }

    public void clear() {
        this.Jc.clear();
    }

    public void b(boolean bl2, int n2) {
        this.IN = n2;
        if (this.IO != bl2) {
            this.IY = this.IQ;
            this.IZ = this.IR;
            this.Ja = this.IS;
            this.Jb = this.IT;
        }
        if (!bl2) {
            this.IU = 1.0f;
            this.IV = 1.0f;
            this.IW = 1.0f;
            this.IX = 1.0f;
        }
        this.IO = bl2;
        this.IP = 0;
    }

    public boolean qW() {
        return (float)this.IP < this.IN;
    }

    public void update(int n2) {
        if (!this.Jc.isEmpty()) {
            this.Jd.cpk = n2;
            this.Jc.a(this.Jd);
            this.Jd.clean();
        }
        if (this.IP == Integer.MAX_VALUE) {
            return;
        }
        this.IP += n2;
        if ((float)this.IP > this.IN) {
            this.IP = Integer.MAX_VALUE;
            this.IQ = this.IY = this.IU;
            this.IR = this.IZ = this.IV;
            this.IS = this.Ja = this.IW;
            this.IT = this.Jb = this.IX;
            return;
        }
        float f = (float)this.IP / this.IN;
        this.IQ = this.IY + (this.IU - this.IY) * f;
        this.IR = this.IZ + (this.IV - this.IZ) * f;
        this.IS = this.Ja + (this.IW - this.Ja) * f;
        this.IT = this.Jb + (this.IX - this.Jb) * f;
    }

    public void d(float[] fArray) {
        assert (fArray.length == 4);
        fArray[0] = fArray[0] * this.IQ;
        fArray[1] = fArray[1] * this.IR;
        fArray[2] = fArray[2] * this.IS;
        fArray[3] = fArray[3] * this.IT;
    }

    public void w(float f) {
        this.IU = 0.3f * f;
        this.IV = 0.3f * f;
        this.IW = 0.3f * f;
        this.IX = f;
    }

    static /* synthetic */ cp_2 a(mg_1 mg_12) {
        return mg_12.Jc;
    }
}

