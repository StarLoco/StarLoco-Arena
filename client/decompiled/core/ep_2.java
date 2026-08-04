/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collections;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import org.apache.log4j.Logger;

/*
 * Renamed from Ep
 */
public abstract class ep_2
implements AW,
ah_2,
awQ {
    private static final Logger a = Logger.getLogger(ep_2.class);
    protected int aQt = 4;
    protected gi aQu = new ati_0();
    protected boolean GD;
    protected boolean aQv = true;
    protected boolean aQw = true;
    protected ah_2 aQx = null;
    protected FloatBuffer aQy;
    protected FloatBuffer aQz;
    protected FloatBuffer aQA;
    protected ShortBuffer aQB;
    protected int aQC;
    protected boolean aQD = true;
    private boolean aK;
    private ap_1 aQE;
    private ap_1 aQF;
    private ArrayList aQG = new ArrayList();
    private ArrayList aQH = new ArrayList();
    private ArrayList aQI = new ArrayList();
    private ArrayList aQJ = new ArrayList();
    private ArrayList aQK = new ArrayList();
    private boolean aQL;
    private boolean aQM;
    private boolean aQN;
    protected ArrayList uA = new ArrayList();
    private int aQO = 0;
    private TV aQP;
    private acq_2 eo;
    private int aQQ;
    private ba_0 aQR;

    public gi Nc() {
        return this.aQu;
    }

    public void a(ati_0 ati_02) {
        if (this.aQu != ati_02) {
            this.aQu = ati_02;
            this.GD = true;
        }
    }

    public void bj(boolean bl2) {
        this.GD = bl2;
    }

    public void initialize() {
        this.bk(true);
        this.aQv = true;
        this.aQw = true;
        this.aQD = true;
        this.aQG = new ArrayList();
        this.aQH = new ArrayList();
        this.aQI = new ArrayList();
        this.aQJ = new ArrayList();
        this.aQK = new ArrayList();
        this.uA = new ArrayList();
    }

    public void uninitialize() {
        if (this.aQx != null) {
            this.aQx.b(this);
        }
        this.aQx = null;
        this.bk(false);
        this.a(null, true);
        for (int j = 0; j < this.uA.size(); ++j) {
            ((ah_2)this.uA.get(j)).uninitialize();
        }
        this.uA.clear();
        if (this.aQu != null) {
            this.aQu.reset();
        }
        this.aQt = 4;
        this.GD = true;
        this.aQv = true;
        this.aQw = true;
        this.aQE = null;
        this.aQF = null;
        this.aQG.clear();
        this.aQH.clear();
        this.aQI.clear();
        this.aQJ.clear();
        this.aQK.clear();
        this.aQL = false;
        this.aQM = false;
        this.aQN = false;
        this.aQP = null;
        this.aQR = null;
        this.aQQ = 0;
    }

    public boolean isInitialized() {
        return this.aK;
    }

    public void bk(boolean bl2) {
        this.aK = bl2;
    }

    public void setVisible(boolean bl2) {
        this.aQv = bl2;
        if (this.aQw) {
            for (int j = 0; j < this.uA.size(); ++j) {
                if (this.uA.get(j) != this) {
                    ((ah_2)this.uA.get(j)).setVisible(bl2);
                    continue;
                }
                a.error((Object)"boucle infinie ?");
            }
        }
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void d(boolean bl2) {
        this.aQw = bl2;
    }

    public FloatBuffer Nd() {
        return this.aQy;
    }

    public FloatBuffer Ne() {
        return this.aQz;
    }

    public FloatBuffer Nf() {
        return this.aQA;
    }

    public ShortBuffer Ng() {
        return this.aQB;
    }

    public Vb Nh() {
        if (!this.aQG.isEmpty()) {
            return (Vb)this.aQG.get(0);
        }
        return null;
    }

    public Vb fl(int n2) {
        if (this.aQG.size() < n2) {
            return (Vb)this.aQG.get(n2);
        }
        return null;
    }

    public void d(Vb vb) {
        this.bp();
        this.b(vb);
    }

    public void e(Vb vb) {
        this.b(vb);
    }

    public void a(ba_0 ba_02) {
        this.aQR = ba_02;
    }

    public ba_0 by() {
        return this.aQR;
    }

    public void a(acq_2 acq_22, boolean bl2) {
        if (acq_22 != this.eo) {
            if (this.eo != null) {
                this.eo.b(this, this.aQR);
            }
            this.eo = acq_22;
            if (this.eo != null) {
                this.aQR = this.eo.aOB();
                this.eo.a((ah_2)this, this.aQR);
            } else {
                this.aQR = null;
            }
        }
        if (bl2) {
            for (int j = 0; j < this.uA.size(); ++j) {
                ((ah_2)this.uA.get(j)).a(acq_22, bl2);
            }
        }
    }

    public acq_2 bx() {
        return this.eo;
    }

    public void bI(int n2) {
        if (this.eo != null) {
            this.aQQ = this.eo.a(this, n2);
        }
        for (int j = 0; j < this.uA.size(); ++j) {
            if (this.uA.get(j) != this) {
                ((ah_2)this.uA.get(j)).bI(n2);
                continue;
            }
            a.error((Object)"child == parent : boucle infinie ?");
        }
    }

    public void i(GL gL) {
        for (int j = 0; j < this.uA.size(); ++j) {
            ((ah_2)this.uA.get(j)).i(gL);
        }
    }

    public void h(GL gL) {
        block14: {
            int n2;
            block13: {
                this.c(gL);
                this.bm();
                for (n2 = 0; n2 < this.aQG.size(); ++n2) {
                    ((Vb)this.aQG.get(n2)).bind();
                }
                this.a(gL, 5889);
                this.a(gL, 5888);
                this.a(gL, 5890);
                this.j(gL);
                if (this.eo == null || !this.aQD) break block13;
                switch (this.aQQ) {
                    case 0: {
                        this.b(gL);
                        for (ah_2 ah_22 : this.uA) {
                            ah_22.h(gL);
                        }
                        this.eo.b(gL, this.aQR);
                        break;
                    }
                    case 1: {
                        this.eo.b(gL, this.aQR);
                        this.b(gL);
                        for (ah_2 ah_23 : this.uA) {
                            ah_23.h(gL);
                        }
                        break block14;
                    }
                    case 2: {
                        this.eo.b(gL, this.aQR);
                        break;
                    }
                    case 3: {
                        this.b(gL);
                        for (ah_2 ah_24 : this.uA) {
                            ah_24.h(gL);
                        }
                        break block14;
                    }
                    case 4: {
                        this.eo.b(gL, this.aQR);
                        for (ah_2 ah_25 : this.uA) {
                            ah_25.h(gL);
                        }
                        break;
                    }
                }
                break block14;
            }
            this.b(gL);
            for (n2 = 0; n2 < this.uA.size(); ++n2) {
                ((ah_2)this.uA.get(n2)).h(gL);
            }
        }
        this.b(gL, jq_0.bmH);
        this.b(gL, jq_0.bmI);
        this.b(gL, jq_0.bmG);
        this.d(gL);
    }

    public void b(GL gL) {
        if (this.aQv) {
            gL.glVertexPointer(4, 5126, 0, this.aQy);
            gL.glColorPointer(4, 5126, 0, this.aQz);
            gL.glTexCoordPointer(2, 5126, 0, this.aQA);
            gL.glDrawElements(4, this.aQB.limit(), 5123, this.aQB);
        }
    }

    public void a(ap_1 ap_12) {
        this.aQE = ap_12;
    }

    public void c(GL gL) {
        if (this.aQE != null) {
            this.aQE.a(gL);
        }
    }

    public void b(ap_1 ap_12) {
        this.aQF = ap_12;
    }

    public void d(GL gL) {
        if (this.aQF != null) {
            this.aQF.a(gL);
        }
    }

    public void a(TV tV) {
        this.aQP = tV;
    }

    public TV Ni() {
        return this.aQP;
    }

    public void a(gi gi2) {
        if (gi2 != null) {
            this.aQH.add(0, gi2);
        }
    }

    public void b(gi gi2) {
        if (gi2 != null) {
            this.aQH.add(gi2);
        }
    }

    public gi bj() {
        if (!this.aQH.isEmpty()) {
            return (gi)this.aQH.remove(0);
        }
        return null;
    }

    public gi bk() {
        if (!this.aQH.isEmpty()) {
            return (gi)this.aQH.remove(this.aQH.size() - 1);
        }
        return null;
    }

    public void c(gi gi2) {
        this.aQH.remove(gi2);
    }

    public void bl() {
        this.aQH.clear();
    }

    public void a(Vb vb) {
        if (vb != null) {
            this.aQG.add(0, vb);
        }
    }

    public void b(Vb vb) {
        if (vb != null) {
            this.aQG.add(vb);
        }
    }

    public Vb bn() {
        if (!this.aQG.isEmpty()) {
            return (Vb)this.aQG.remove(0);
        }
        return null;
    }

    public Vb bo() {
        if (!this.aQG.isEmpty()) {
            return (Vb)this.aQG.remove(this.aQG.size() - 1);
        }
        return null;
    }

    public void a(anm_0 anm_02) {
        this.aQG.remove(anm_02);
    }

    public void bp() {
        this.aQG.clear();
    }

    public int d(ah_2 ah_22) {
        try {
            float f = ah_22.bw();
            if (this.bw() > f) {
                return 1;
            }
            if (this.bw() < f) {
                return -1;
            }
        }
        catch (Exception exception) {
            a.error((Object)("Exception : " + exception.getMessage()));
        }
        return 0;
    }

    public float bw() {
        return 0.0f;
    }

    public void sort() {
        for (int j = 0; j < this.uA.size(); ++j) {
            ((ah_2)this.uA.get(j)).sort();
        }
        Collections.sort(this.uA);
    }

    public void a(ah_2 ah_22) {
        assert (ah_22 != null);
        ah_22.c(this);
        this.uA.add(ah_22);
        if (this.aQw) {
            ah_22.setVisible(this.aQv);
        }
    }

    public void b(ah_2 ah_22) {
        if (this.uA != null && ah_22 != null) {
            this.uA.remove(ah_22);
            ah_22.c((ah_2)null);
        }
    }

    public void bq() {
        this.uA.clear();
    }

    public boolean br() {
        return !this.uA.isEmpty();
    }

    public ah_2 bs() {
        this.aQO = 0;
        if (this.uA.isEmpty()) {
            return null;
        }
        return (ah_2)this.uA.get(this.aQO);
    }

    public ah_2 bt() {
        ++this.aQO;
        if (this.aQO >= this.uA.size()) {
            return null;
        }
        return (ah_2)this.uA.get(this.aQO);
    }

    public ah_2 bu() {
        --this.aQO;
        if (this.aQO < 0) {
            return null;
        }
        return (ah_2)this.uA.get(this.aQO);
    }

    public int getChildCount() {
        return this.uA.size();
    }

    public int Nj() {
        int n2 = 0;
        for (ah_2 ah_22 : this.uA) {
            if (!(ah_22 instanceof ep_2)) continue;
            n2 += ((ep_2)ah_22).Nj();
        }
        return n2 + this.getChildCount();
    }

    public ah_2 j(int n2) {
        if (n2 >= 0 && n2 < this.uA.size()) {
            return (ah_2)this.uA.get(n2);
        }
        return null;
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public ah_2 bv() {
        return this.aQx;
    }

    public void c(ah_2 ah_22) {
        this.aQx = ah_22;
    }

    public void a(Pq pq, int n2) {
        switch (n2) {
            case 5888: {
                this.aQJ.add(0, pq);
                break;
            }
            case 5889: {
                this.aQI.add(0, pq);
                break;
            }
            case 5890: {
                this.aQK.add(0, pq);
            }
        }
    }

    public void b(Pq pq, int n2) {
        switch (n2) {
            case 5888: {
                this.aQJ.add(pq);
                break;
            }
            case 5889: {
                this.aQI.add(pq);
                break;
            }
            case 5890: {
                this.aQK.add(pq);
            }
        }
    }

    public Pq fm(int n2) {
        switch (n2) {
            case 5888: {
                if (this.aQJ.isEmpty()) {
                    return null;
                }
                this.aQJ.remove(0);
                break;
            }
            case 5889: {
                if (this.aQI.isEmpty()) {
                    return null;
                }
                this.aQI.remove(0);
                break;
            }
            case 5890: {
                if (this.aQK.isEmpty()) {
                    return null;
                }
                this.aQK.remove(0);
            }
        }
        return null;
    }

    public Pq fn(int n2) {
        switch (n2) {
            case 5888: {
                if (this.aQJ.isEmpty()) {
                    return null;
                }
                this.aQJ.remove(this.aQJ.size() - 1);
                break;
            }
            case 5889: {
                if (this.aQI.isEmpty()) {
                    return null;
                }
                this.aQI.remove(this.aQI.size() - 1);
                break;
            }
            case 5890: {
                if (this.aQK.isEmpty()) {
                    return null;
                }
                this.aQK.remove(this.aQK.size() - 1);
            }
        }
        return null;
    }

    public void fo(int n2) {
        switch (n2) {
            case 5888: {
                this.aQJ.clear();
                break;
            }
            case 5889: {
                this.aQI.clear();
                break;
            }
            case 5890: {
                this.aQK.clear();
            }
        }
    }

    public void c(Pq pq, int n2) {
        switch (n2) {
            case 5888: {
                this.aQJ.remove(pq);
                break;
            }
            case 5889: {
                this.aQI.remove(pq);
                break;
            }
            case 5890: {
                this.aQK.remove(pq);
            }
        }
    }

    public void a(GL gL, int n2) {
        switch (n2) {
            case 5889: {
                if (this.aQI.isEmpty()) break;
                this.a(gL, jq_0.bmG);
                for (int j = 0; j < this.aQI.size(); ++j) {
                    ((Pq)this.aQI.get(j)).a(gL);
                }
                break;
            }
            case 5888: {
                if (this.aQJ.isEmpty()) break;
                this.a(gL, jq_0.bmI);
                for (int j = 0; j < this.aQJ.size(); ++j) {
                    ((Pq)this.aQJ.get(j)).a(gL);
                }
                break;
            }
            case 5890: {
                if (this.aQK.isEmpty()) break;
                this.a(gL, jq_0.bmH);
                for (int j = 0; j < this.aQK.size(); ++j) {
                    ((Pq)this.aQK.get(j)).a(gL);
                }
                break;
            }
        }
    }

    public void bm() {
    }

    public void a(GL gL, jq_0 jq_02) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(jq_02);
        vo_12.n(db_22);
        switch (jq_02) {
            case bmI: {
                gL.glPushMatrix();
                this.aQM = true;
                break;
            }
            case bmG: {
                gL.glPushMatrix();
                this.aQL = true;
                break;
            }
            case bmH: {
                gL.glPushMatrix();
                this.aQN = true;
            }
        }
    }

    public void b(GL gL, jq_0 jq_02) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(jq_02);
        vo_12.n(db_22);
        switch (jq_02) {
            case bmI: {
                if (!this.aQM) break;
                gL.glPopMatrix();
                this.aQM = false;
                break;
            }
            case bmG: {
                if (!this.aQL) break;
                gL.glPopMatrix();
                this.aQL = false;
                break;
            }
            case bmH: {
                if (!this.aQN) break;
                gL.glPopMatrix();
                this.aQN = false;
            }
        }
    }

    protected void j(GL gL) {
    }

    public void e(boolean bl2) {
        this.aQD = bl2;
        for (int j = 0; j < this.uA.size(); ++j) {
            ((ah_2)this.uA.get(j)).e(bl2);
        }
    }

    public String toString() {
        return "<<abstract Mesh>>";
    }

    public void a(aaf_2 aaf_22) {
    }

    public void b(aaf_2 aaf_22) {
    }

    public long HV() {
        int n2 = 0;
        return n2;
    }

    public void b() {
        if (!this.isInitialized()) {
            this.initialize();
        }
    }

    public void j() {
        if (this.isInitialized()) {
            this.uninitialize();
        }
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
    }

    public void P(int n2, int n3) {
    }
}

