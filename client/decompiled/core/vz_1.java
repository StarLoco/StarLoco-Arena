/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;

/*
 * Renamed from VZ
 */
public abstract class vz_1
implements ayi {
    protected vP AC = null;

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public abstract void a(int var1, int var2, int var3, int var4, float var5);

    public abstract void setPixmaps(akq_1 var1, akq_1 var2, akq_1 var3, akq_1 var4, akq_1 var5, akq_1 var6, akq_1 var7, akq_1 var8, akq_1 var9);

    public abstract Entity getEntity();

    public abstract void b();

    public void j() {
        this.AC = null;
    }

    public abstract void setHorizontal(boolean var1);

    public abstract void setColor(vP var1);

    public abstract vP getColor();

    public abstract void setFullCirclePercentage(float var1);

    public abstract float getFullCirclePercentage();

    public abstract void setDeltaAngle(float var1);

    public abstract float getDeltaAngle();
}

