/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.texture.TextureCoords;
import org.apache.log4j.Logger;

/*
 * Renamed from acY
 */
public class acy_1
extends ep_2
implements JG {
    private static final Logger a = Logger.getLogger(acy_1.class);
    private static final float[] clr = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    private static final float[] cls = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] clt = new float[]{0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    private static final short[] clu = new short[]{0, 1, 2, 2, 3, 1};
    private static final acl_0 uG = new ym_0(new Oa());
    private acl_0 clv = null;
    private boolean clw = true;
    protected float bsE;
    protected float bsF;
    protected float KX;
    protected float KY;
    protected float clx;
    protected boolean cly;
    protected boolean clz;
    protected be_2 clA;
    protected amf_2 clB;
    protected amf_2 clC;
    protected og_0 clD;
    protected aGs clE;
    protected nn_2 clF;
    protected aoc_0 clG;
    private static int clH = 0;

    protected acy_1() {
        ++clH;
        this.clA = new be_2();
        this.clB = new amf_2();
        this.clC = new amf_2();
        this.clD = new og_0();
        this.clE = new aGs();
        this.clF = new nn_2();
        this.initialize();
    }

    public ati_0 arJ() {
        return (ati_0)this.aQu;
    }

    public static int arK() {
        return clH;
    }

    public void initialize() {
        if (this.isInitialized()) {
            return;
        }
        super.initialize();
        this.bsE = 10.0f;
        this.bsF = 10.0f;
        this.b(this.clC, 5888);
        this.b(this.clD, 5888);
        this.b(this.clE, 5888);
        this.b(this.clF, 5888);
        this.b(this.clA, 5888);
        this.aQt = 5;
        if (this.aQy == null) {
            this.aQy = BufferUtil.newFloatBuffer(16);
        }
        if (this.aQz == null) {
            this.aQz = BufferUtil.newFloatBuffer(16);
        }
        if (this.aQA == null) {
            this.aQA = BufferUtil.newFloatBuffer(8);
        }
        if (this.aQB == null) {
            this.aQB = BufferUtil.newShortBuffer(6);
        }
        try {
            this.aQy.rewind();
            this.aQz.rewind();
            this.aQA.rewind();
            this.aQB.rewind();
            this.aQy.put(clr);
            this.aQz.put(cls);
            this.aQA.put(clt);
            this.aQB.put(clu);
        }
        catch (Exception exception) {
            a.error((Object)("Exception : " + exception.getMessage() + "\n\t+ Deux buffers ont la m\u00eame adresse"));
        }
        this.aQy.flip();
        this.aQz.flip();
        this.aQA.flip();
        this.aQB.flip();
    }

    public void uninitialize() {
        if (!this.isInitialized()) {
            return;
        }
        super.uninitialize();
        this.cly = false;
        this.clz = false;
        this.aQD = false;
        this.clC.reset();
        this.clD.reset();
        this.clE.reset();
        this.clF.reset();
        this.clA.reset();
        this.clB.reset();
        this.clG = null;
        this.bsE = 0.0f;
        this.bsF = 0.0f;
        this.KX = 0.0f;
        this.KY = 0.0f;
        this.clx = 0.0f;
        this.cly = false;
        this.clz = false;
        this.fo(5888);
        this.fo(5889);
        this.fo(5890);
    }

    public void arL() {
        Vb vb = this.Nh();
        if (vb != null) {
            TextureCoords textureCoords = vb.getImageTexCoords();
            if (textureCoords != null) {
                if (this.cly) {
                    this.aQA.put(0, textureCoords.right());
                    this.aQA.put(1, textureCoords.bottom());
                    this.aQA.put(2, textureCoords.right());
                    this.aQA.put(3, textureCoords.top());
                    this.aQA.put(4, textureCoords.left());
                    this.aQA.put(5, textureCoords.bottom());
                    this.aQA.put(6, textureCoords.left());
                    this.aQA.put(7, textureCoords.top());
                } else {
                    this.aQA.put(0, textureCoords.left());
                    this.aQA.put(1, textureCoords.bottom());
                    this.aQA.put(2, textureCoords.left());
                    this.aQA.put(3, textureCoords.top());
                    this.aQA.put(4, textureCoords.right());
                    this.aQA.put(5, textureCoords.bottom());
                    this.aQA.put(6, textureCoords.right());
                    this.aQA.put(7, textureCoords.top());
                }
            }
            this.setWidth(vb.aif());
            this.aN(vb.aig());
        }
    }

    public void b(TextureCoords textureCoords) {
        Vb vb = this.Nh();
        if (vb != null) {
            this.aQA.put(0, textureCoords.right());
            this.aQA.put(1, textureCoords.bottom());
            this.aQA.put(2, textureCoords.right());
            this.aQA.put(3, textureCoords.top());
            this.aQA.put(4, textureCoords.left());
            this.aQA.put(5, textureCoords.bottom());
            this.aQA.put(6, textureCoords.left());
            this.aQA.put(7, textureCoords.top());
        }
    }

    public void setColor(float f, float f2, float f3, float f4) {
        float[] fArray = this.arJ().aGl();
        this.arJ().dY(true);
        if (fArray[0] != f || fArray[1] != f2 || fArray[2] != f3 || fArray[3] != f4) {
            this.arJ().u(f, f2, f3, f4);
            this.GD = true;
            this.bm();
        }
    }

    public void bm() {
        if (this.GD || this.aQu != null && this.arJ().aGv()) {
            this.aQz.rewind();
            if (this.aQu == null || !this.arJ().aGh()) {
                this.aQz.put(ati_0.cTs);
                this.aQz.put(ati_0.cTs);
                this.aQz.put(ati_0.cTs);
                this.aQz.put(ati_0.cTs);
            } else {
                this.aQz.put(this.arJ().aGo());
                this.aQz.put(this.arJ().aGm());
                this.aQz.put(this.arJ().aGp());
                this.aQz.put(this.arJ().aGn());
            }
            this.aQz.rewind();
            this.GD = false;
        }
    }

    public void bI(int n2) {
        super.bI(n2);
    }

    public boolean arM() {
        return false;
    }

    public float arN() {
        return this.clC.id();
    }

    public void aM(float f) {
        this.clx = f;
        this.clC.X(this.clx);
    }

    public float arO() {
        return this.clx;
    }

    public float arP() {
        return this.clC.getX();
    }

    public float arQ() {
        return this.clC.getY();
    }

    public float getWidth() {
        return this.bsE;
    }

    public void setWidth(float f) {
        this.bsE = f;
        this.aQy.put(8, this.bsE);
        this.aQy.put(12, this.bsE);
    }

    public float getHeight() {
        return this.bsF;
    }

    public void aN(float f) {
        this.bsF = f;
        this.aQy.put(5, this.bsF);
        this.aQy.put(13, this.bsF);
        if (this.clG != null) {
            this.clB.d(0.0f, -this.bsF, 0.0f);
        } else {
            this.clA.d(this.KX, this.KY - this.bsF, 0.0f);
        }
    }

    public float arR() {
        return this.clA.getX();
    }

    public float arS() {
        return this.clA.getY() + this.bsF;
    }

    public void F(float f, float f2) {
        this.KX = f;
        this.KY = f2;
        if (this.clG == null) {
            this.clA.d(f, f2 - this.bsF, 0.0f);
        } else {
            this.clA.d(f, f2, 0.0f);
        }
    }

    public void q(float f, float f2, float f3, float f4) {
        this.clC.d(f, f2, this.clx);
        this.setWidth(f3);
        this.aN(f4);
    }

    public float bw() {
        return this.clx;
    }

    public boolean aok() {
        return this.cly;
    }

    public void cZ(boolean bl2) {
        if (this.cly != bl2) {
            this.cly = bl2;
            this.clz = true;
        }
    }

    public void G(float f, float f2) {
        this.clC.d(f, f2, this.clx);
    }

    public void H(float f, float f2) {
        this.clC.p(f, f2, 0.0f);
    }

    public void aO(float f) {
        this.clE.add(f);
    }

    public void ad(float f) {
        this.clE.bG(f);
    }

    public aGs arT() {
        return this.clE;
    }

    public void I(float f, float f2) {
        this.clF.l(f, f2);
    }

    public void J(float f, float f2) {
        this.clF.k(f, f2);
    }

    public void K(float f, float f2) {
        this.clD.add(f, f2);
    }

    public void L(float f, float f2) {
        this.clD.k(f, f2);
    }

    public amf_2 arU() {
        return this.clC;
    }

    public og_0 arV() {
        return this.clD;
    }

    public nn_2 arW() {
        return this.clF;
    }

    public void a(aoc_0 aoc_02) {
        this.fo(5888);
        this.clG = aoc_02;
        this.b(this.clG, 5888);
        this.b(this.clF, 5888);
        this.b(this.clA, 5888);
        this.b(this.clB, 5888);
    }

    public aoc_0 arX() {
        return this.clG;
    }

    public void arY() {
        this.clA.x(this.clA.getX() + this.clB.getX());
        this.clA.y(this.clA.getY() + this.clB.getY());
        this.clA.X(this.clA.id() + this.clB.id());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Mesh2D[").append(this.arP()).append(";").append(this.arQ()).append(";").append(this.arN()).append(" - ").append(this.bsE).append(";").append(this.bsF).append("] (").append(this.uA.size()).append(" childs)\n");
        for (ah_2 ah_22 : this.uA) {
            stringBuffer.append("\t").append(ah_22.toString());
        }
        return stringBuffer.toString();
    }

    public void a(aaf_2 aaf_22) {
        super.a(aaf_22);
    }

    public void b(aaf_2 aaf_22) {
        super.b(aaf_22);
    }

    public long HV() {
        return super.HV() + 64L + 64L + 32L + 12L;
    }

    public boolean arZ() {
        return this.clw;
    }

    public void da(boolean bl2) {
        this.clw = bl2;
    }

    public boolean ajU() {
        return this.clv == null;
    }

    public final void release() {
        if (this.clv != null) {
            try {
                this.clv.af(this);
            }
            catch (Exception exception) {
                this.j();
                a.error((Object)"release() Mesh2D exception raised : ", (Throwable)exception);
            }
        } else {
            this.j();
        }
        this.clv = null;
    }

    protected void a(acl_0 acl_02) {
        this.clv = acl_02;
    }

    public static acy_1 asa() {
        acy_1 acy_12;
        try {
            acy_12 = (acy_1)uG.adr();
            acy_12.a(uG);
        }
        catch (Exception exception) {
            acy_12 = new acy_1();
            a.error((Object)("Erreur lors d'un checkOut sur un Mesh2D : " + exception.getMessage()));
        }
        return acy_12;
    }

    public void j() {
        super.j();
        this.uninitialize();
    }

    public void b() {
        super.b();
        this.initialize();
    }
}

