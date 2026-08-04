/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import java.util.Comparator;

class SJ
implements Comparator {
    private static final SJ bLG = new SJ();

    private SJ() {
    }

    public int a(Entity entity, Entity entity2) {
        return (int)(((EntitySprite)entity2).HD() - ((EntitySprite)entity).HD());
    }

    static /* synthetic */ SJ afm() {
        return bLG;
    }
}

