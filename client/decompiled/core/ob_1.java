/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.xulor2.graphics.XulorParticleSystem;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from Ob
 */
public class ob_1
extends apf_0 {
    public static final String TAG = "Particle";
    public static final int bBo = "alignment".hashCode();
    public static final int bBp = "file".hashCode();
    public static final int bBq = "followBorders".hashCode();
    public static final int bBr = "level".hashCode();
    public static final int bBs = "moveClockWise".hashCode();
    public static final int bBt = "speed".hashCode();
    public static final int bBu = "timeToLive".hashCode();
    public static final int bBv = "useParentScissor".hashCode();
    public static final int ars = "x".hashCode();
    public static final int art = "y".hashCode();
    public static final int bBw = "zoom".hashCode();
    private int aG;
    private int aH;
    private BT bBx;
    private boolean bBy = false;
    private XulorParticleSystem bBz;
    private EntityGroup arC;
    private oq_0 bBA;
    private agu_0 Jl = new agu_0(0.0f, 0.0f, 0.0f);
    private boolean bBB = false;
    private float aaS = 200.0f;
    private int bBC;
    private boolean bBD = true;
    private agu_0 bBE;
    private String eA;
    private int bBF;
    private float aaw;

    public final xL getMesh() {
        return null;
    }

    public String getTag() {
        return TAG;
    }

    public final Entity getEntity() {
        return null;
    }

    public final void setFile(String string) {
        this.eA = string;
        this.setNeedsToPreProcess();
    }

    public final void setLevel(int n2) {
        this.bBF = n2;
        if (this.eA != null) {
            this.setNeedsToPreProcess();
        }
    }

    public final void setX(int n2) {
        this.aG = n2;
        this.Jl.x(this.aG);
    }

    public final void setY(int n2) {
        this.aH = n2;
        this.Jl.y(this.aH);
    }

    public final void setAlignment(BT bT) {
        this.bBx = bT;
    }

    public final void setUseParentScissor(boolean bl2) {
        this.bBy = bl2;
        this.bBA.setUseParentScissor(this.bBy);
    }

    public void setFollowBorders(boolean bl2) {
        this.bBB = bl2;
    }

    public float getZoom() {
        return this.aaw;
    }

    public void setZoom(float f) {
        this.aaw = f;
        avz avz2 = (avz)this.arC.aUM().aI(0);
        avz2.m(this.aaw, this.aaw, this.aaw);
        this.arC.aUM().b(0, avz2);
    }

    public void setSpeed(float f) {
        this.aaS = f;
    }

    public void setMoveClockWise(boolean bl2) {
        this.bBD = bl2;
    }

    public void setTimeToLive(int n2) {
        if (this.bBC == n2) {
            return;
        }
        this.bBC = n2;
        if (this.bBz != null) {
            this.bBz.iZ(n2);
        }
    }

    public void setParticleSystem(XulorParticleSystem xulorParticleSystem) {
        this.bBz = xulorParticleSystem;
    }

    public final boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.bBz != null) {
            if (this.bBz.isAlive()) {
                this.bBz.alQ();
            }
            this.bBz = null;
            this.arC.removeAllChildren();
        }
        this.abo();
        return bl2;
    }

    public final boolean cb(int n2) {
        Entity entity;
        super.cb(n2);
        this.arC.removeAllChildren();
        this.gX(n2);
        if (this.bBz != null && this.bBz.avb() < 0) {
            this.aab();
            return false;
        }
        if (this.bBz != null) {
            this.bBz.b(this.arC);
        }
        if (this.arC.aUL() == null && (entity = this.getParentEntity()) != null) {
            entity.j(this.arC);
            entity.i(this.arC);
        }
        if (this.bBz != null && this.bBz.avb() < 0) {
            this.aab();
            return false;
        }
        return true;
    }

    public final void b(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ob_1 ob_12 = (ob_1)air_12;
        ob_12.setFile(this.eA);
        ob_12.setAlignment(this.bBx);
        ob_12.setFollowBorders(this.bBB);
        ob_12.setLevel(this.bBF);
        ob_12.setMoveClockWise(this.bBD);
        ob_12.setSpeed(this.aaS);
        ob_12.setTimeToLive(this.bBC);
        ob_12.setUseParentScissor(this.bBy);
        ob_12.setX(this.aG);
        ob_12.setY(this.aH);
        ob_12.setParticleSystem(this.bBz);
    }

    public void b() {
        assert (this.arC == null);
        super.b();
        this.bBC = -1;
        this.bBF = fh_1.rK;
        this.setNeedsToPostProcess();
        this.arC = (EntityGroup)yW.FL().a(EntityGroup.it(), EntityGroup.class);
        this.bBA = new oq_0();
        this.arC.a(this.bBA);
        this.arC.a(this.bBA);
        this.arC.aUM().a(new avz());
        this.bBA.setUseParentScissor(this.bBy);
        this.bBE = this.bBD ? new agu_0(1.0f, 0.0f, 0.0f) : new agu_0(-1.0f, 0.0f, 0.0f);
        this.aaw = 1.0f;
    }

    public void j() {
        super.j();
        this.eA = null;
        if (this.bBz != null && this.bBz.isAlive()) {
            this.bBz.alQ();
            this.bBz = null;
        }
        this.arC.removeAllChildren();
        this.arC.HF();
        Entity entity = this.getParentEntity();
        if (entity != null) {
            entity.j(this.arC);
        }
        this.arC = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bBo) {
            this.setAlignment(BT.dv(string));
        } else if (n2 == bBp) {
            this.setFile(if_12.eM(string));
        } else if (n2 == bBq) {
            this.setFollowBorders(Gr.getBoolean(string));
        } else if (n2 == bBr) {
            this.setLevel(Gr.R(string));
        } else if (n2 == bBs) {
            this.setMoveClockWise(Gr.getBoolean(string));
        } else if (n2 == bBt) {
            this.setSpeed(Gr.getFloat(string));
        } else if (n2 == bBu) {
            this.setTimeToLive(Gr.R(string));
        } else if (n2 == bBv) {
            this.setUseParentScissor(Gr.getBoolean(string));
        } else if (n2 == ars) {
            this.setX(Gr.R(string));
        } else if (n2 == art) {
            this.setY(Gr.R(string));
        } else if (n2 == bBw) {
            this.setZoom(Gr.getFloat(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == bBo) {
            this.setAlignment((BT)((Object)object));
        } else if (n2 == bBp) {
            this.setFile(String.valueOf(object));
        } else if (n2 == bBq) {
            this.setFollowBorders(Gr.getBoolean(object));
        } else if (n2 == bBr) {
            this.setLevel(Gr.R(object));
        } else if (n2 == bBs) {
            this.setMoveClockWise(Gr.getBoolean(object));
        } else if (n2 == bBt) {
            this.setSpeed(Gr.getFloat(object));
        } else if (n2 == bBu) {
            this.setTimeToLive(Gr.R(object));
        } else if (n2 == bBv) {
            this.setUseParentScissor(Gr.getBoolean(object));
        } else if (n2 == ars) {
            this.setX(Gr.R(object));
        } else if (n2 == art) {
            this.setY(Gr.R(object));
        } else if (n2 == bBw) {
            this.setZoom(Gr.getFloat(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    private void abo() {
        assert (this.bBz == null) : "Particle system is already initialized";
        assert (this.eA != null) : "The particle system file is null";
        String string = add_1.aOG().cFE + this.eA;
        this.bBz = mq_1.rk().j(string, this.bBF);
        if (this.bBz != null) {
            afo_1.dGT.e(this.bBz);
        }
    }

    private void gX(int n2) {
        adg_2 adg_22 = this.getParentWidget();
        agj_1 agj_12 = adg_22.getSize();
        if (this.bBB) {
            this.v(n2, agj_12.width, agj_12.height);
        } else {
            this.Jl.d((float)(this.aG + this.bBx.eL(agj_12.width)) / this.aaw, (float)(this.aH + this.bBx.eM(agj_12.height)) / this.aaw, 0.0f);
        }
        if (this.bBz != null) {
            this.bBz.r(this.Jl.getX(), this.Jl.getY());
        }
    }

    private void v(int n2, int n3, int n4) {
        this.Jl.a(this.aaS * (float)n2 / 1000.0f, this.bBE);
        if (this.Jl.getX() > (float)n3) {
            this.Jl.x(n3);
            if (this.Jl.getY() == 0.0f) {
                this.bBE.d(0.0f, 1.0f, 0.0f);
            } else {
                this.bBE.d(0.0f, -1.0f, 0.0f);
            }
        } else if (this.Jl.getX() < 0.0f) {
            this.Jl.x(0.0f);
            if (this.Jl.getY() == 0.0f) {
                this.bBE.d(0.0f, 1.0f, 0.0f);
            } else {
                this.bBE.d(0.0f, -1.0f, 0.0f);
            }
        }
        if (this.Jl.getY() > (float)n4) {
            this.Jl.y(n4);
            if (this.Jl.getX() == 0.0f) {
                this.bBE.d(1.0f, 0.0f, 0.0f);
            } else {
                this.bBE.d(-1.0f, 0.0f, 0.0f);
            }
        } else if (this.Jl.getY() < 0.0f) {
            this.Jl.y(0.0f);
            if (this.Jl.getX() == 0.0f) {
                this.bBE.d(1.0f, 0.0f, 0.0f);
            } else {
                this.bBE.d(-1.0f, 0.0f, 0.0f);
            }
        }
    }

    public agu_0 getPosition() {
        return this.Jl;
    }

    private Entity getParentEntity() {
        adg_2 adg_22 = this.getParentWidget();
        if (adg_22 == null) {
            return null;
        }
        return adg_22.getEntity();
    }
}

