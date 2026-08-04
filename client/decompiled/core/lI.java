/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;

public class lI
extends alp_2 {
    public static final String TAG = "MapNavigator";
    private zF HF;
    private EntityGroup HG = new EntityGroup();
    private EntitySprite HH = new EntitySprite();

    protected void pX() {
        this.arC.i(this.HG);
        super.pX();
    }

    public String getTag() {
        return TAG;
    }

    private void qv() {
        this.HF = new zF();
        this.HG.a(this.HF);
        this.HG.b(new zd_0());
        this.HH.a(new GLGeometrySprite());
        this.HH.setSize(256, 256);
        this.HH.x(256.0f, -64.0f);
        this.HH.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.HH.setMaterial(aPb.enf);
        this.HH.setTexture(null);
        this.HG.i(this.HH);
    }

    public void j() {
        super.j();
    }

    public void b() {
        super.b();
    }

    public boolean cb(int n2) {
        super.cb(n2);
        return true;
    }
}

