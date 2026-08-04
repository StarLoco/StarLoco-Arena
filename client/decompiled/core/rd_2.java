/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;

/*
 * Renamed from RD
 */
public class rd_2
extends ka_0
implements afI {
    List bJw;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.bJw = new ArrayList();
        qq_02.a(this);
    }

    public void a(qq_0 qq_02, String string) {
        qq_02.b(this);
        Object object = qq_02.wa();
        if (object instanceof nd_2) {
            nd_2 nd_22 = (nd_2)object;
            axy axy2 = new axy(this.Pb, this.bJw, nd_22.Dr());
            nd_22.a(axy2);
        }
    }

    public void b(xg_0 xg_02) {
        this.bJw.add(xg_02);
    }

    public List aeo() {
        return this.bJw;
    }
}

