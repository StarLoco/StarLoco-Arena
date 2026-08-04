/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import com.ankamagames.framework.graphics.engine.text.EntityText;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Renamed from wQ
 */
public final class wq_1 {
    private static final boolean cR = false;
    private static final wq_1 avD = new wq_1();
    private ArrayList avE;
    private float avF;
    private float avG;
    private HashMap avH;
    private int avI;
    private EntityText avJ;

    public static wq_1 Dn() {
        return avD;
    }

    public void a(kp_1 kp_12) {
    }

    public final void Do() {
        if (this.avH != null) {
            this.avH.clear();
        }
        this.avI = 0;
    }

    public void a(Geometry geometry) {
    }

    public void a(db_2 db_22) {
    }

    public void Dp() {
    }

    private wq_1() {
        this.Do();
    }

    private void a(db_2 db_22, kp_1 kp_12) {
        float f = 200.0f * (float)kp_12.pA() / (float)kp_12.getSize();
        db_22.a(this.avF, this.avG, f, 14.0f, -12582912);
        db_22.a(this.avF + f, this.avG, 200.0f - f, 14.0f, -16760832);
        this.avJ.setText(kp_12.py().getSimpleName() + "(" + kp_12.pA() + "/" + kp_12.getSize() + ")");
        this.avJ.a(new agu_0(this.avF, this.avG + 2.0f, 0.0f));
        this.avJ.setVisible(true);
        this.avJ.a(db_22);
        this.avG -= 16.0f;
    }
}

