/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import java.util.concurrent.ThreadFactory;

public class WY
implements ThreadFactory {
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "AnmLoader #" + Anm.iw());
    }
}

