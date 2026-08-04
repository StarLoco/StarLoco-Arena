/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBuffer;
import com.ankamagames.framework.graphics.engine.entity.EntityBatch;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;

/*
 * Renamed from DB
 */
public abstract class db_2 {
    protected Object aNM = null;
    protected Matrix44 aMI;
    protected Matrix44 aNN;
    protected final Matrix44 aNO;
    protected ef_1 aNP;
    protected int aNQ = 1;
    protected float[] tb = new float[8];
    protected float[] td = new float[8];
    protected ahB aNR = new ahB();
    protected EntityBatch aNS;
    protected boolean aNT = false;
    protected boolean aNU = true;
    protected int aNV;
    protected int aNW;

    protected db_2() {
        this.aNO = new Matrix44();
    }

    public void uR() {
    }

    public abstract arX vg();

    public abstract VertexBuffer vh();

    public abstract ams_1 vi();

    public abstract ef_1 a(long var1, String var3, boolean var4);

    public abstract ef_1 a(long var1, aon_2 var3, boolean var4);

    public abstract ef_1 a(long var1, int var3, int var4, boolean var5);

    public abstract void c(VertexBuffer var1);

    public abstract void a(ams_1 var1);

    public abstract void b(Matrix44 var1);

    public abstract void c(Matrix44 var1);

    public abstract void vl();

    public final Matrix44 LT() {
        return this.aMI;
    }

    public final Matrix44 LU() {
        return this.aNN;
    }

    public abstract void a(aPb var1);

    public abstract void f(ArrayList var1);

    public abstract void a(rh_1 var1);

    public abstract void a(ef_1 var1);

    public abstract void b(ef_1 var1);

    public void B(Object object) {
        this.aNM = object;
    }

    public final Object LV() {
        return this.aNM;
    }

    public abstract void a(float var1, float var2, float var3, float var4, int var5);

    public abstract void cO(int var1);

    public abstract boolean vj();

    public final boolean LW() {
        return this.aNT;
    }

    public void am(boolean bl2) {
        this.aNT = bl2;
        this.aNU = true;
    }

    public boolean LX() {
        return this.aNU;
    }

    public void bf(boolean bl2) {
        this.aNU = bl2;
    }

    public final Matrix44 LY() {
        return this.aNO;
    }

    public final void an(int n2, int n3) {
        this.aNV = n2;
        this.aNW = n3;
    }

    public final int LZ() {
        return this.aNV;
    }

    public final int Ma() {
        return this.aNW;
    }
}

