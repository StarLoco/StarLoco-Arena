/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

public abstract class gJ
implements aEe {
    protected GL go;

    public void initialize() {
        this.go = (GL)arX.cQT.iE().LV();
    }

    public void cleanUp() {
        this.go = null;
    }
}

