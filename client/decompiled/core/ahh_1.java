/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.sun.opengl.util.texture.TextureCoords;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.media.opengl.Threading;
import org.apache.log4j.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from aHh
 */
public class ahh_1
implements Du,
qq_1,
xw_0,
aFy,
aGf,
aog_2,
amw_0,
arp_0 {
    private static final Logger a = Logger.getLogger(ahh_1.class);
    public static final float dKW = 0.4375f;
    private String dKX = "AnimStatique";
    private String dKY = "AnimHit";
    protected agu_0 dKZ;
    protected boolean dLa;
    protected String dw = "AnimStatique";
    protected boolean Gy = false;
    protected float dLb = 1.0f;
    protected boolean dLc = true;
    protected String dLd = "AnimStatique";
    protected boolean dLe = true;
    protected static final float dLf = 500.0f;
    protected float dLg = 1.0f;
    protected boolean dLh = false;
    protected boolean dLi = false;
    protected final aHX dLj = new aHX(5);
    protected yj_0 dLk = asr.cSd;
    protected String dLl = null;
    protected float cpB = 1.0f;
    protected boolean dLm = true;
    protected EntityGroup arC;
    protected Entity3D dLn;
    protected String dLo;
    protected String aJ;
    protected int dLp = -1;
    protected int dLq = -1;
    protected int dLr = 0;
    protected int IP = 0;
    protected boolean GG = false;
    protected gw_2 cAF;
    protected String eA;
    private final agf_0 dLs = new agf_0();
    private static final agf_0 dLt = new agf_0(-256, 256, -256, 256);
    protected aPb tJ;
    private air tK = air.cya;
    private air tL = air.cye;
    private final avz dLu = new avz();
    private static final ArrayList dLv = new ArrayList();
    private float dLw = 0.4375f;
    private final ArrayList dLx = new ArrayList();
    private final ArrayList dLy = new ArrayList(5);
    private boolean dLz = false;
    private boolean dLA = false;
    private Entity3D dLB;
    public static boolean dLC = false;
    private double dLD = Double.MIN_VALUE;
    private double dLE = Double.MIN_VALUE;
    protected long nD;
    protected double oF;
    protected double oG;
    protected double oH = 0.0;
    protected final ry dLF = new ry();
    protected int coE;
    protected byte dLG;
    protected int oI = Integer.MIN_VALUE;
    protected int oJ = Integer.MIN_VALUE;
    protected int oK;
    protected ArrayList oL = null;
    protected boolean dLH = true;
    protected boolean dLI = true;
    protected ArrayList dLJ = null;
    private boolean aQv = true;
    protected boolean dLK = true;
    private boolean dLL = true;
    protected final float[] aaV = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private boolean dLM = false;
    protected float dLN = 1.0f;
    protected float dLO = 1.0f;
    private byte dLP = (byte)3;
    private static final float[] coH = new float[4];
    protected float Gx = 1.0f;
    protected acq_2 eo;
    protected boolean dLQ = false;
    protected short dLR = (short)6;
    private boolean dLS = false;
    private static agf_0 dLT = new agf_0();
    private mp_0[] dLU;
    private static ArrayList dLV = new ArrayList();

    private void reset() {
        this.cpB = 1.0f;
        this.dLg = 1.0f;
        this.aaV[3] = 1.0f;
        this.aaV[2] = 1.0f;
        this.aaV[1] = 1.0f;
        this.aaV[0] = 1.0f;
        this.dLN = 1.0f;
        this.dLO = 1.0f;
        this.dLS = false;
        this.dLh = false;
        this.dLi = false;
        this.dLD = Double.MIN_VALUE;
        this.dLE = Double.MIN_VALUE;
        this.dLp = -1;
        this.dLq = -1;
        this.dLr = 0;
        this.tJ.d(gw_2.jO());
        this.Gy = true;
        this.dLe = true;
        this.dLP = (byte)3;
        if (this.cAF != null) {
            this.cAF.reset();
            this.cAF = null;
        }
        if (this.dLn != null) {
            this.dLn.clear();
        }
    }

    private Entity3D aTl() {
        Entity3D entity3D = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.h(entity3D);
        return entity3D;
    }

    private void h(Entity entity) {
        avz avz2 = new avz();
        avz2.OH();
        avz2.m(this.getScale(), this.getScale(), 1.0f);
        entity.aUM().a(avz2);
        entity.b(ahA.axi().ih("transform"));
        entity.oM(-180157682);
        entity.at(2.0f);
    }

    public byte ox() {
        return 0;
    }

    public boolean hC() {
        return this.oI != Integer.MIN_VALUE && this.oJ != Integer.MIN_VALUE;
    }

    public float zR() {
        return (float)(this.oF - this.oG);
    }

    public float zS() {
        return (float)(-(this.oF + this.oG));
    }

    public boolean zT() {
        return false;
    }

    public int zU() {
        return this.Ge();
    }

    public final Entity getEntity() {
        return this.arC;
    }

    public final Entity aTm() {
        return this.dLn;
    }

    public boolean aTn() {
        return dLC;
    }

    public final aPb getMaterial() {
        return this.tJ;
    }

    public final void setMaterial(aPb aPb2) {
        this.tJ.d(aPb2);
    }

    public String getPath() {
        return this.aJ;
    }

    public final float aTo() {
        return this.dLw;
    }

    public final void bR(float f) {
        this.dLw = f;
    }

    public final void oC(int n2) {
        this.dLw += (float)n2 * 0.0625f;
    }

    public void setScale(float f) {
        this.Gx = f;
        this.dLu.m(this.getScale(), this.getScale(), 1.0f);
    }

    public void lq(String string) {
        String string2 = this.dLo;
        this.dLo = string;
        if (string2 != this.dLo) {
            this.aTt();
        }
    }

    protected String en(String string) {
        if (this.dLl != null) {
            return string + this.dLl;
        }
        return string;
    }

    public void a(yj_0 yj_02) {
        this.dLk = yj_02;
    }

    public final void aTp() {
        this.tJ.d(gw_2.jO());
    }

    public void b(ahh_1 ahh_12) {
        if (ahh_12 == null) {
            return;
        }
        this.GG = true;
        this.dLc = true;
        this.dLM = true;
        if (ahh_12.dLm) {
            ahh_12.aTs();
        }
        Entity entity = ahh_12.getEntity();
        this.arC.b(entity.aUP());
        ArrayList arrayList = entity.aUQ();
        ArrayList arrayList2 = this.arC.aUQ();
        for (int j = 1; j < arrayList.size(); ++j) {
            arrayList2.add(new QI((QI)arrayList.get(j)));
        }
        this.arC.dPC.c(entity.dPC);
        this.tJ.d(ahh_12.tJ);
        this.tK = ahh_12.tK;
        this.tL = ahh_12.tL;
        this.aJ = ahh_12.aJ;
        this.dLo = ahh_12.dLo;
        this.cpB = ahh_12.cpB;
        if (ahh_12.cAF != null) {
            this.cAF = new gw_2(ahh_12.cAF);
        }
    }

    public final void a(air air2, air air3) {
        this.tK = air2;
        this.tL = air3;
    }

    private static Anm x(String string, boolean bl2) {
        String string2;
        Object object;
        try {
            object = new URL(string);
            String string3 = ((URL)object).getPath();
            string2 = ((URL)object).getProtocol() + ":" + string3;
        }
        catch (MalformedURLException malformedURLException) {
            if (bl2) {
                a.error((Object)"Exception", (Throwable)malformedURLException);
                return null;
            }
            string2 = string;
        }
        try {
            object = xw_1.EB().f(string2, bl2);
        }
        catch (IOException iOException) {
            a.error((Object)"Unable to load equipment", (Throwable)iOException);
            return null;
        }
        return object;
    }

    public static Anm lr(String string) {
        return ahh_1.x(string, true);
    }

    public void b(String string, String ... stringArray) {
        if (string == null) {
            a.warn((Object)"on veut appliquer un equipemnt depuis un path null");
            return;
        }
        Anm anm = ahh_1.lr(string);
        if (anm == null) {
            return;
        }
        this.a(anm, stringArray);
    }

    public void a(Anm anm, String ... stringArray) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/animatedElement/AnimatedElement.applyParts must not be null");
        }
        if (this.cAF == null) {
            return;
        }
        this.cAF.a(anm, stringArray);
        this.aTq();
    }

    public void aTq() {
        this.GG = true;
    }

    public void c(Anm anm, String ... stringArray) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/animatedElement/AnimatedElement.removeParts must not be null");
        }
        if (stringArray == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/animatedElement/AnimatedElement.removeParts must not be null");
        }
        this.cAF.b(anm, stringArray);
        this.aTq();
    }

    private void y(String string, boolean bl2) {
        this.aJ = string;
        this.cAF = xw_1.EB().e(string, bl2);
        this.cAF.b(new aim_0(this));
    }

    private void aTr() {
        if (this.cAF == null) {
            return;
        }
        this.aP(this.cAF.jX() ? this.cAF.jY() : 1.0f);
    }

    public void b(String string, boolean bl2) {
        assert (string.endsWith("anm") || string.endsWith("ANM"));
        this.y(string, bl2);
    }

    public ahh_1() {
        this(0L);
    }

    public ahh_1(long l2) {
        this(l2, 0.0, 0.0);
    }

    public ahh_1(long l2, double d, double d2) {
        this(l2, d, d2, 0.0);
    }

    public ahh_1(long l2, double d, double d2, double d3) {
        this(l2, d, d2, d3, true);
    }

    public ahh_1(long l2, double d, double d2, double d3, boolean bl2) {
        this.c(l2);
        this.oF = d;
        this.oG = d2;
        this.oH = d3;
        this.eX(bl2);
        this.dKZ = new agu_0();
        this.dLa = true;
        this.aTy();
        this.tJ = aPb.aYI();
        this.tJ.d(gw_2.jO());
        if (Threading.isOpenGLThread()) {
            this.aTs();
            this.dLm = false;
        } else {
            this.dLm = true;
        }
        ajh_2.b(this);
    }

    private void aTs() {
        this.arC = new EntityGroup();
        this.dLn = this.aTl();
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        int n2 = 48 + this.ge() * 8;
        gLGeometrySprite.setSize(48, n2);
        gLGeometrySprite.x(n2 - 16, -24.0f);
        gLGeometrySprite.setColor(0.5f, 0.5f, 0.5f, 1.0f);
        gLGeometrySprite.k(0.0f, 0.0f, 1.0f, 1.0f);
        this.dLB = new Entity3D();
        this.dLB.a(gLGeometrySprite, aoz_1.aYF().emW, aPb.enf);
        this.dLB.b(ahA.axi().ih("transform"));
        this.dLB.oM(-180157682);
        this.dLB.at(2.0f);
        this.dLB.a(dk_0.aNu);
        this.dLB.b(auJ.cWH);
        this.dLB.setVisible(false);
        this.arC.i(this.dLn);
        this.arC.i(this.dLB);
        this.h(this.arC);
    }

    public void aTt() {
        this.Gy = true;
    }

    public String AU() {
        return this.dw;
    }

    public boolean aY(String string) {
        if (this.dw.equals(string)) {
            return false;
        }
        this.aTr();
        this.eZ(false);
        this.dLd = this.dw;
        this.dw = string;
        this.aTt();
        return true;
    }

    public void eR(boolean bl2) {
        this.dLe = bl2;
        if (!this.dLe) {
            this.aTB();
        }
    }

    public String aTu() {
        return this.dLl;
    }

    public String aEX() {
        return this.dLd;
    }

    public void ls(String string) {
        boolean bl2;
        boolean bl3 = bl2 = string != this.dLl || string != null && !string.equals(this.dLl);
        if (bl2) {
            this.dLl = string;
            this.aTt();
        }
    }

    public void ar(float f) {
        this.dLb = f;
    }

    public void a(acq_2 acq_22) {
        this.eo = acq_22;
        this.aTt();
    }

    public void eS(boolean bl2) {
        this.dLQ = bl2;
        this.aTt();
    }

    public String Sg() {
        String string = this.dw;
        if (this.dLl != null) {
            string = string + this.dLl;
        }
        return string;
    }

    public final void aTv() {
        this.dLh = true;
    }

    public boolean T(float f, float f2) {
        boolean bl2;
        boolean bl3 = bl2 = this.cAF != null && this.dLs.contains((int)f, (int)f2);
        if (bl2) {
            float f3 = f - (float)this.dLs.aSQ();
            float f4 = f2 - (float)this.dLs.aSS();
            bl2 = this.a(this.arC, f3, f4);
        }
        return bl2;
    }

    private boolean a(Entity entity, float f, float f2) {
        agu_0[] agu_0Array = new agu_0[]{new agu_0(), new agu_0(), new agu_0()};
        agw_1 agw_12 = new agw_1();
        agw_1 agw_13 = new agw_1();
        agu_0 agu_02 = new agu_0(f, f2, 0.0f, 0.0f);
        boolean bl2 = this.aTF().cL();
        boolean bl3 = false;
        ArrayList arrayList = entity.aUK();
        float f3 = this.cAF.getMinX();
        float f4 = this.cAF.getMinY();
        for (Entity entity2 : arrayList) {
            int n2 = ((Entity3D)entity2).aFz();
            if (n2 > 1) {
                return true;
            }
            for (int j = 0; j < n2; ++j) {
                Geometry geometry = ((Entity3D)entity2).ma(j);
                if (!(geometry instanceof GLGeometryMesh)) continue;
                GLGeometryMesh gLGeometryMesh = (GLGeometryMesh)geometry;
                VertexBufferPCT vertexBufferPCT = gLGeometryMesh.ab();
                int n3 = vertexBufferPCT.fq();
                FloatBuffer floatBuffer = vertexBufferPCT.ys();
                FloatBuffer floatBuffer2 = vertexBufferPCT.yt();
                for (int i2 = 0; i2 < n3; i2 += 4) {
                    float f5;
                    float f6;
                    int n4 = i2 * 2;
                    agu_0Array[0].d(floatBuffer.get(n4 + 0), floatBuffer.get(n4 + 1), 0.0f, 1.0f);
                    agu_0Array[1].d(floatBuffer.get(n4 + 2), floatBuffer.get(n4 + 3), 0.0f, 1.0f);
                    agu_0Array[2].d(floatBuffer.get(n4 + 4), floatBuffer.get(n4 + 5), 0.0f, 1.0f);
                    agw_12.k(agu_0Array[2].getX() - agu_0Array[1].getX(), agu_0Array[2].getY() - agu_0Array[1].getY());
                    agw_13.k(agu_0Array[0].getX() - agu_0Array[1].getX(), agu_0Array[0].getY() - agu_0Array[1].getY());
                    float f7 = f3 - agu_0Array[1].getX();
                    float f8 = f4 - agu_0Array[1].getY();
                    agu_02.d(f + f7, f2 + f8, 0.0f, 0.0f);
                    float f9 = agw_13.getY() * agw_12.getX() - agw_13.getX() * agw_12.getY();
                    if (f9 == 0.0f) continue;
                    if (agw_12.getX() == 0.0f) {
                        assert (agw_13.getX() != 0.0f);
                        f6 = agw_13.getY() * agu_02.getX() - agw_13.getX() * agu_02.getY();
                        if ((f6 /= f9) < 0.0f || f6 > 1.0f || (f5 = (agu_02.getX() - f6 * agw_12.getX()) / agw_13.getX()) < 0.0f || f5 > 1.0f) {
                            continue;
                        }
                    } else {
                        f5 = agu_02.getY() * agw_12.getX() - agu_02.getX() * agw_12.getY();
                        if ((f5 /= f9) < 0.0f || f5 > 1.0f || (f6 = (agu_02.getX() - f5 * agw_13.getX()) / agw_12.getX()) < 0.0f || f6 > 1.0f) continue;
                    }
                    assert (f6 >= 0.0f && f6 <= 1.0f && f5 >= 0.0f && f5 <= 1.0f) : "et hop, du code tout bugg\u00e9";
                    FloatBuffer floatBuffer3 = vertexBufferPCT.yu();
                    TextureCoords textureCoords = new TextureCoords(floatBuffer3.get(n4), floatBuffer3.get(n4 + 1), floatBuffer3.get(n4 + 4), floatBuffer3.get(n4 + 5));
                    if (bl2 && !this.a(f6, f5, textureCoords, ((Entity3D)entity2).ln(j))) continue;
                    return true;
                }
            }
            if (bl3) continue;
            bl3 = this.a(entity2, f, f2);
        }
        return bl3;
    }

    private boolean a(float f, float f2, TextureCoords textureCoords, ef_1 ef_12) {
        if (ef_12 == null) {
            return false;
        }
        try {
            kf_0 kf_02 = ef_12.lB(0);
            awL awL2 = kf_02.pq();
            int n2 = kf_02.getWidth();
            int n3 = kf_02.getHeight();
            float f3 = ej_0.a(textureCoords.left(), textureCoords.right(), f);
            float f4 = ej_0.a(textureCoords.top(), textureCoords.bottom(), f2);
            int n4 = Math.round(f3 * (float)n2);
            int n5 = n3 - Math.round(f4 * (float)n3);
            return awL2.ca(n4, n5);
        }
        catch (Throwable throwable) {
            a.error((Object)"", throwable);
            return false;
        }
    }

    public void dispose() {
        wj_2.Df().b(this);
        this.reset();
    }

    protected boolean Se() {
        return this.Gy || this.dLc || this.dLK || this.dLM || this.GG;
    }

    protected void Sf() {
        this.Gy = false;
        this.dLc = false;
        this.dLM = false;
        this.GG = false;
    }

    private void aTw() {
        String string = this.Po();
        this.cAF.setAnimation(this.en(string));
    }

    public boolean b(aba_2 aba_22, int n2) {
        return this.a(aba_22, n2, 0);
    }

    public boolean a(aba_2 aba_22, int n2, int n3) {
        int n4;
        ju_2 ju_22;
        if (n3 > 5) {
            a.info((Object)("Boucle infinie dans le process de l'anm " + this.cAF.ka() + " animation courante=" + this.dw + " (probl\u00e8me dans l'enchainement des goto ?)"));
            return false;
        }
        if (this.dLz) {
            this.dLx.removeAll(this.dLy);
            this.dLy.clear();
            this.dLz = false;
        }
        if (this.cAF == null) {
            return false;
        }
        if (this.dLh) {
            this.dLh = false;
            if (this.e(aba_22)) {
                if (this.cAF != null && !this.cAF.is()) {
                    this.dLi = true;
                } else {
                    this.dLg = 0.0f;
                }
            }
        }
        if (this.dLi && this.cAF != null && this.cAF.is()) {
            this.dLg = 0.0f;
            this.dLi = false;
        }
        if (this.dLm) {
            this.aTs();
            this.dLm = false;
        }
        assert (n2 < 1000000) : "DeltaTime is very high (" + n2 + "), did you use realTime instead ?";
        if (this.dLo == null) {
            return false;
        }
        this.IP = (int)((float)this.IP + (float)n2 * this.dLb);
        assert (this.cAF != null);
        if (!this.cAF.is()) {
            return false;
        }
        this.cAF.jV();
        if (this.dLg < this.dLO) {
            this.dLg += (float)n2 / 500.0f;
            if (this.dLg > this.dLO) {
                this.dLg = this.dLO;
            }
            if (this.aaV[3] != this.dLg) {
                this.aaV[3] = this.dLg;
                this.dLM = true;
            }
        }
        boolean bl2 = false;
        this.GG |= this.cAF.kb();
        if (this.Se()) {
            bl2 = true;
            this.dLK = false;
            ju_22 = this.cAF.jL();
            this.cAF.setAnimation(this.en(this.dw));
            n4 = this.cAF.jM();
            if (this.cAF.jL() == null) {
                this.cAF.setAnimation(this.en("AnimStatique"));
            }
            if (this.cAF.jL() != null || n4 != 0) {
                if (this.Gy) {
                    this.IP = 0;
                    if (this.dLj.size() > 0) {
                        this.aTB();
                    }
                }
                this.dLr = this.IP;
                this.Gy = false;
                this.dLp = -1;
                this.dLq = -1;
            } else {
                this.aTw();
                if (this.cAF.jL() != ju_22) {
                    this.IP = 0;
                    this.dLp = -1;
                    this.dLq = -1;
                    this.dLr = 0;
                }
            }
            this.Sf();
            if (n4 != 0) {
                this.dLc = true;
            }
        }
        if ((ju_22 = this.cAF.jL()) != null && ju_22.oh()) {
            this.aTx();
            this.a(aba_22, n2, n3 + 1);
        }
        if (this.cAF.jK()) {
            bl2 = true;
        }
        if (this.cAF.jN()) {
            bl2 = true;
        }
        if (this.cAF.jL() == null) {
            return false;
        }
        n4 = this.cAF.aD(this.IP);
        if (n4 != this.dLq || n4 != this.dLp) {
            bl2 = true;
        }
        if (!bl2) {
            bl2 = this.cAF.jU();
        }
        if (bl2) {
            if (this.dLq != n4 && (aba_22 == null || this.isVisible() && this.e(aba_22))) {
                this.cAF.setMaterial(this.tJ);
                this.cAF.a(this.tK, this.tL);
                this.cAF.a(this.IP, this.dLn, n2);
                this.dLq = n4;
            }
            if (this.dLp != n4) {
                this.dLp = n4;
                this.aTx();
                if (n4 == this.cAF.jQ() - 1) {
                    this.aTH();
                    if (this.cAF == null) {
                        return false;
                    }
                }
            }
            this.dLr = this.IP;
        }
        this.cAF.r(n2);
        return true;
    }

    private void aTx() {
        this.cAF.a(dLv, this.IP, this.dLr);
        this.D(dLv);
        dLv.clear();
    }

    public agu_0 d(aba_2 aba_22) {
        if (!this.dLa) {
            // empty if block
        }
        int n2 = (int)Math.floor(aba_22.aNA());
        float f = (float)this.getAltitude() * (float)n2;
        float f2 = (float)aba_22.i(this.getWorldX(), this.getWorldY());
        float f3 = (float)aba_22.j(this.getWorldX(), this.getWorldY()) + f;
        float f4 = -1.0f;
        this.dKZ.d(f2, f3, -1.0f);
        this.dLa = false;
        return this.dKZ;
    }

    public void setVisible(boolean bl2) {
        if (bl2 != this.aQv) {
            this.aQv = bl2;
            this.dLK = true;
            this.b(bl2, ns_1.bzw);
            this.eX(bl2);
        }
        if (this.dLK && bl2) {
            this.dLa = true;
        }
    }

    public void aTy() {
        this.dLa = true;
    }

    public boolean e(aba_2 aba_22) {
        agu_0 agu_02 = this.d(aba_22);
        yg_1 yg_12 = aba_22.vC();
        int n2 = (int)agu_02.getX();
        int n3 = (int)agu_02.getY();
        dLT.set(n2 + dLt.aSQ(), n2 + dLt.aSR(), n3 + dLt.aSS(), n3 + dLt.aST());
        return yg_12.y(ahh_1.dLT.bAE, ahh_1.dLT.bAB, ahh_1.dLT.bAD, ahh_1.dLT.bAC);
    }

    public boolean a(qs_2 qs_22) {
        float[] fArray;
        boolean bl2;
        if (!this.isVisible() || !this.e(qs_22) || this.cAF == null) {
            return false;
        }
        if (this.cAF.jL() == null) {
            this.dLs.set(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE);
            return false;
        }
        agu_0 agu_02 = this.d(qs_22);
        YR yR = qs_22.vn();
        agf_0 agf_02 = this.cAF.jP();
        int n2 = (int)agu_02.getX() - yR.getScreenX();
        int n3 = (int)agu_02.getY() - yR.getScreenY();
        this.arC.EN = n2 + agf_02.bAB + yR.getScreenX();
        this.arC.EP = n2 + agf_02.bAC + yR.getScreenX();
        this.arC.EO = n3 + agf_02.bAD + yR.getScreenY();
        this.arC.EQ = n3 + agf_02.bAE + yR.getScreenY();
        this.dLs.set(n2 + agf_02.aSQ(), n2 + agf_02.aSR(), n3 + agf_02.aSS(), n3 + agf_02.aST());
        if (!yR.y(this.arC.EQ, this.arC.EN, this.arC.EO, this.arC.EP)) {
            return false;
        }
        double d = this.getWorldX();
        double d2 = this.getWorldY();
        double d3 = this.getAltitude();
        float f = (float)d;
        float f2 = (float)d2;
        if (ajh_2.c(this)) {
            int n4 = this.bS(f);
            int bl22 = this.bS(f2);
            qs_22.a(this, this.arC, n4, bl22, (float)d3, this.dLw);
        }
        boolean bl3 = bl2 = (fArray = ((yg_1)yR).a(this))[3] != 0.0f;
        if (this.dLL != bl2) {
            this.b(bl2, ns_1.bzv);
            this.dLL = bl2;
        }
        if (fArray[3] <= 0.0f) {
            return false;
        }
        System.arraycopy(fArray, 0, coH, 0, fArray.length);
        this.dLP = axG.a(this.dLP, coH);
        this.E(coH);
        this.dLu.e(agu_02);
        this.arC.aUM().b(0, this.dLu);
        this.arC.cpB = this.cpB;
        if (this.cpB >= 1.0f && (this.dLD != d || this.dLE != d2)) {
            this.dLD = d;
            this.dLE = d2;
            this.arC.cpB = this.cpB + 1.0f;
        }
        this.arC.dPy = f;
        this.arC.dPz = f2;
        this.arC.dPA = (float)d3;
        this.arC.bsF = this.ge();
        qs_22.b(this.arC, this.cpB > 0.0f);
        return true;
    }

    protected double Sc() {
        return 0.0;
    }

    protected double Sd() {
        return 0.0;
    }

    private int bS(float f) {
        if (f == (float)((int)f)) {
            return (int)f;
        }
        if (f < 0.0f) {
            if ((f = -f) - (float)((int)f) < 0.5f) {
                return -((int)f);
            }
            return -((int)(f + 1.0f));
        }
        if (f - (float)((int)f) < 0.5f) {
            return (int)f;
        }
        return (int)(f + 1.0f);
    }

    public void a(qs_2 qs_22, int n2) {
        this.dLK = false;
    }

    public long aTz() {
        return this.arC.dPx;
    }

    public boolean a(jw_1 jw_12) {
        return false;
    }

    public String Po() {
        return this.dKX;
    }

    public void dW(String string) {
        assert (string != null);
        this.dKX = string;
    }

    public void D(ArrayList arrayList) {
        boolean bl2 = false;
        int n2 = -1;
        int n3 = arrayList.size();
        for (int j = 0; j < n3; ++j) {
            int n4 = ((jw_1)arrayList.get(j)).Wo();
            if (bl2 && n2 != n4) break;
            bl2 |= ((jw_1)arrayList.get(j)).a(this);
            n2 = n4;
        }
    }

    public void c(ati_0 ati_02) {
        this.f(ati_02.aGl(), ati_02.aGr());
    }

    public void f(float[] fArray, float[] fArray2) {
        this.tJ.G(fArray);
        this.tJ.H(fArray2);
        this.tJ.aYL()[3] = 0.0f;
        ArrayList arrayList = this.arC.aUK();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Entity entity = (Entity)arrayList.get(j);
            entity.oM(-180157682);
        }
        this.dLn.c(this.tJ);
    }

    public void aTA() {
        this.tJ.H(0.0f, 0.0f, 0.0f, 0.0f);
        ArrayList arrayList = this.arC.aUK();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Entity entity = (Entity)arrayList.get(j);
            entity.oM(-180157682);
        }
        this.dLn.c(this.tJ);
    }

    public static void b(mp_0 mp_02) {
        dLV.add(mp_02);
    }

    public mp_0[] aEY() {
        if (this.dLU == null) {
            this.dLU = new mp_0[2 + dLV.size()];
            this.dLU[0] = new agk(this);
            this.dLU[1] = new bh_1(this);
            int n2 = dLV.size();
            for (int j = 0; j < n2; ++j) {
                this.dLU[j + 2] = (mp_0)dLV.get(j);
            }
        }
        return this.dLU;
    }

    protected void aTB() {
        for (int j = this.dLj.size() - 1; j >= 0; --j) {
            avE avE2 = ahz_1.aUa().ex(this.dLj.oJ(j));
            if (avE2 == null) continue;
            avE2.et(true);
            avE2.j(0.0f, 0.7f);
        }
        this.dLj.clear();
    }

    public short ge() {
        return this.dLR;
    }

    public void bJ(short s) {
        this.dLR = s;
    }

    public String aTC() {
        return this.dKY;
    }

    public void lt(String string) {
        this.dKY = string;
    }

    public void E(float[] fArray) {
        assert (fArray.length == 4);
        float[] fArray2 = this.tJ.aYK();
        float f = this.aaV[3];
        fArray2[0] = f * this.aaV[0] * fArray[0];
        fArray2[1] = f * this.aaV[1] * fArray[1];
        fArray2[2] = f * this.aaV[2] * fArray[2];
        fArray2[3] = f * fArray[3];
    }

    public void aP(float f) {
        this.cpB = f;
    }

    public float jY() {
        return this.cpB;
    }

    public float hA() {
        return this.arC.cpB;
    }

    public boolean a(mg_2 mg_22) {
        if (!this.dLx.contains(mg_22)) {
            return this.dLx.add(mg_22);
        }
        return false;
    }

    public void aTD() {
        this.dLx.clear();
    }

    public void b(mg_2 mg_22) {
        this.dLy.add(mg_22);
        this.dLz = true;
    }

    public Anm aTE() {
        return this.cAF == null ? null : this.cAF.jF();
    }

    public gw_2 aTF() {
        return this.cAF;
    }

    public int an(String string) {
        if (this.cAF == null || string == null) {
            return 0;
        }
        return this.cAF.an(string);
    }

    public final void eT(boolean bl2) {
        if (this.dLA == bl2) {
            return;
        }
        this.eU(bl2);
    }

    public final void eU(boolean bl2) {
        this.dLA = bl2;
        if (this.dLA) {
            this.arC.dPB |= 1;
            this.dLB.setVisible(!ahA.axi().kK(-98564371));
        } else {
            this.arC.dPB &= 0xFFFFFFFE;
            this.dLB.setVisible(false);
        }
    }

    public final boolean aTG() {
        return this.dLA;
    }

    public void eV(boolean bl2) {
        this.arC.dPB = bl2 ? (this.arC.dPB |= 2) : (this.arC.dPB &= 0xFFFFFFFD);
    }

    private void aTH() {
        int n2 = this.dLx.size();
        for (int j = 0; j < n2; ++j) {
            mg_2 mg_22 = (mg_2)this.dLx.get(j);
            mg_22.a(this);
        }
    }

    public void eW(boolean bl2) {
        this.dLM = bl2;
    }

    public int JU() {
        return -1;
    }

    public vP Zv() {
        return vP.atL;
    }

    public String JV() {
        return "";
    }

    public void e(float[] fArray) {
        if (fArray[0] == 1.0f && fArray[1] == 1.0f && fArray[2] == 1.0f) {
            return;
        }
        float[] fArray2 = this.tJ.aYK();
        fArray2[0] = fArray2[0] * fArray[0];
        fArray2[1] = fArray2[1] * fArray[1];
        fArray2[2] = fArray2[2] * fArray[2];
        this.dLn.c(this.tJ);
    }

    public void a(String[] stringArray, boolean bl2) {
        for (String string : stringArray) {
            this.z(string, bl2);
        }
    }

    public void z(String string, boolean bl2) {
        int n2 = ej_0.Z(string);
        if (bl2) {
            this.cAF.aB(n2);
        } else {
            this.cAF.aA(n2);
        }
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public long getId() {
        return this.nD;
    }

    public double getAltitude() {
        return this.oH;
    }

    public double getWorldX() {
        return this.oF;
    }

    public double getWorldY() {
        return this.oG;
    }

    public int gn() {
        return (int)Math.round(this.oF);
    }

    public int go() {
        return (int)Math.round(this.oG);
    }

    public short gp() {
        return (short)Math.round(this.oH);
    }

    public void b(double d, double d2) {
        this.oF = d;
        this.oG = d2;
        this.aTy();
        ajh_2.b(this);
    }

    public void a(double d, double d2, double d3) {
        this.oF = d;
        this.oG = d2;
        if (d3 == -32768.0) {
            try {
                throw new Exception("on vient de setter une altitude anormale");
            }
            catch (Exception exception) {
                aiu_0.a.error((Object)"", (Throwable)exception);
            }
        }
        this.oH = d3;
        this.aTy();
        ajh_2.b(this);
    }

    public ry aTI() {
        this.dLF.l(this.gn(), this.go(), (short)this.oH);
        return this.dLF;
    }

    public int Ge() {
        return this.coE;
    }

    public void if(int n2) {
        this.coE = n2;
    }

    public final byte atZ() {
        return this.dLG;
    }

    public void an(byte by) {
        this.dLG = by;
    }

    public int getScreenX() {
        return this.oI;
    }

    public int getScreenY() {
        return this.oJ;
    }

    public void ai(int n2) {
        this.oI = n2;
    }

    public void aj(int n2) {
        this.oJ = n2;
    }

    public void ak(int n2) {
        this.oK = n2;
    }

    public int hB() {
        return this.oK;
    }

    public float getScale() {
        return this.Gx;
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public float getAlpha() {
        return this.aaV[3];
    }

    public void W(float f) {
        this.aaV[3] = f;
    }

    public void bT(float f) {
        this.aaV[3] = this.dLO = f;
    }

    public void aTJ() {
        this.aaV[3] = this.dLO;
    }

    public float aTK() {
        return this.dLN;
    }

    public boolean aTL() {
        return this.oL != null;
    }

    public void a(fj_0 fj_02) {
        if (this.oL == null) {
            this.oL = new ArrayList();
        }
        this.oL.add(fj_02);
        fj_02.setTargetIsVisible(this.dLH);
    }

    public void b(fj_0 fj_02) {
        if (this.oL == null) {
            return;
        }
        this.oL.remove(fj_02);
        if (this.oL.size() == 0) {
            this.oL = null;
            this.oI = Integer.MIN_VALUE;
            this.oJ = Integer.MIN_VALUE;
        }
    }

    public void hD() {
        if (this.oL != null) {
            for (int j = 0; j < this.oL.size(); ++j) {
                ((fj_0)this.oL.get(j)).a(this, this.oI, this.oJ, this.oK);
            }
        }
    }

    public void eX(boolean bl2) {
        if (bl2 == this.dLH) {
            return;
        }
        this.dLH = bl2;
        this.EK();
    }

    public void eY(boolean bl2) {
        if (bl2 == this.dLI) {
            return;
        }
        this.dLI = bl2;
        this.EK();
    }

    private void EK() {
        if (this.oL == null) {
            return;
        }
        for (int j = 0; j < this.oL.size(); ++j) {
            ((fj_0)this.oL.get(j)).setTargetIsVisible(!this.dLI && this.dLH);
        }
    }

    public void b(boolean bl2, ns_1 ns_12) {
        if (this.dLJ != null) {
            for (int j = 0; j < this.dLJ.size(); ++j) {
                ((dE)this.dLJ.get(j)).a(bl2, ns_12);
            }
        }
    }

    public void a(dE dE2) {
        if (this.dLJ == null) {
            this.dLJ = new ArrayList();
        }
        this.dLJ.add(dE2);
    }

    public void b(dE dE2) {
        if (this.dLJ != null) {
            this.dLJ.remove(dE2);
        }
    }

    public void aTM() {
        if (this.dLJ != null) {
            this.dLJ.clear();
        }
    }

    public int aTN() {
        return this.gn();
    }

    public int aTO() {
        return this.go();
    }

    public tj_1 aTP() {
        return tj_1.amE;
    }

    public int aEZ() {
        return -1;
    }

    public final void dk(boolean bl2) {
        this.dLP = (byte)(bl2 ? 0 : 2);
    }

    public void eZ(boolean bl2) {
        this.dLS = bl2;
        this.dLS = false;
    }

    static /* synthetic */ void c(ahh_1 ahh_12) {
        ahh_12.aTr();
    }
}

