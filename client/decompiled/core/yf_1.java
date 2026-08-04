/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from YF
 */
public class yf_1
implements LM {
    private static final Logger a = Logger.getLogger(yf_1.class);
    private int aG;
    private int aH;
    private xy_0 aI;
    private int HX;
    private ArrayList cbm;
    private boolean aK = false;

    public yf_1(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("animatedCursor")) {
            return;
        }
        try {
            k_0 k_03 = k_02.f("x");
            k_0 k_04 = k_02.f("y");
            k_0 k_05 = k_02.f("delay");
            k_0 k_06 = k_02.f("type");
            this.aG = k_03 == null ? 0 : k_03.getIntValue();
            this.aH = k_04 == null ? 0 : k_04.getIntValue();
            this.HX = k_05 == null ? 500 : k_05.getIntValue();
            this.aI = k_06 == null ? xy_0.bYl : xy_0.valueOf(k_06.getStringValue().toUpperCase());
            ArrayList arrayList = k_02.d("cursorFrame");
            this.cbm = new ArrayList(arrayList.size());
            int n2 = arrayList.size();
            for (int j = 0; j < n2; ++j) {
                k_0 k_07 = (k_0)arrayList.get(j);
                k_0 k_08 = k_07.f("path");
                this.cbm.add(k_08.getStringValue());
            }
            this.aK = true;
        }
        catch (Exception exception) {
            a.warn((Object)"Probl\u00e8me \u00e0 la lecture d'un AnimatedCursor");
        }
    }

    public yf_1(int n2, int n3, xy_0 xy_02, int n4, int n5, ArrayList arrayList) {
        this.aG = n2;
        this.aH = n3;
        this.aI = xy_02;
        this.HX = n4;
        this.cbm = arrayList;
    }

    public void a(DS dS) {
        if (this.aK) {
            dS.a(this.aI, this.aG, this.aH, this.HX, this.cbm);
        }
    }

    public String a(Ga ga) {
        if (!this.aK) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = ga.GQ();
        stringBuilder.append(new aKI(ArrayList.class, string, "new ArrayList<String>()").a(ga));
        stringBuilder.append("\n");
        int n2 = this.cbm.size();
        for (int j = 0; j < n2; ++j) {
            stringBuilder.append(new aza(null, "add", string, "\"" + (String)this.cbm.get(j) + "\"").a(ga)).append("\n");
        }
        stringBuilder.append("\n");
        stringBuilder.append("InitLoaderManager.getInstance().addLoader(new AnimatedCursorInitLoader(").append(this.aG).append(", ").append(this.aH).append(", ").append("CursorFactory.CursorType.").append(this.aI.name()).append(", ").append(this.HX).append(", ").append(string).append("));");
        return stringBuilder.toString();
    }

    public void a(sf_1 sf_12) {
        if (!this.aK) {
            return;
        }
        sf_12.j(ArrayList.class);
        sf_12.j(xy_0.class);
        String string = sf_12.yg();
        String string2 = sf_12.GQ();
        sf_12.a(new aKI(ArrayList.class, string2, "new ArrayList<String>()"));
        int n2 = this.cbm.size();
        for (int j = 0; j < n2; ++j) {
            sf_12.a(new aza(null, "add", string2, "\"" + (String)this.cbm.get(j) + "\""));
        }
        sf_12.a(new aza(null, "loadAnimatedCursor", string, xy_0.class.getSimpleName() + "." + this.aI.name(), String.valueOf(this.aG), String.valueOf(this.aH), String.valueOf(this.HX), string2));
    }

    public boolean isInitialized() {
        return this.aK;
    }
}

