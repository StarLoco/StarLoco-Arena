/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.FPSAnimator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Renamed from RL
 */
class rl_0
extends WindowAdapter {
    final /* synthetic */ FPSAnimator bKt;
    final /* synthetic */ afg_0 bKu;

    rl_0(afg_0 afg_02, FPSAnimator fPSAnimator) {
        this.bKu = afg_02;
        this.bKt = fPSAnimator;
    }

    public void windowClosing(WindowEvent windowEvent) {
        new Thread(new fn_1(this)).start();
    }
}

