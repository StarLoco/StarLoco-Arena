/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class NM {
    protected static final Logger a = Logger.getLogger(NM.class);
    private int m_index;
    private String m_name;
    private ArrayList bAv;
    private ArrayList bAw;

    public NM(int n2, String string) {
        this.m_index = n2;
        this.m_name = string;
        this.bAv = new ArrayList();
        this.bAw = new ArrayList();
    }

    public int getIndex() {
        return this.m_index;
    }

    public String getName() {
        return this.m_name;
    }

    public ArrayList aaS() {
        return this.bAv;
    }

    public void a(azX azX2) {
        this.bAv.add(azX2);
        this.bAw.add(azX2);
    }

    public azX aaT() {
        if (!this.bAw.isEmpty()) {
            int n2 = (int)Math.floor(Math.random() * (double)this.bAw.size());
            return (azX)this.bAw.remove(n2);
        }
        return null;
    }

    public void aaU() {
        this.bAw = new ArrayList();
        for (azX azX2 : this.bAv) {
            this.bAw.add(azX2);
        }
    }

    public static ArrayList a(ed_1 ed_12, String string, String string2) {
        ArrayList<NM> arrayList = new ArrayList<NM>();
        try {
            ArrayList arrayList2 = ed_12.U(string);
            ArrayList arrayList3 = ed_12.V(string2);
            for (int j = 0; j < arrayList2.size(); ++j) {
                NM nM = new NM(j + 1, (String)arrayList2.get(j));
                if (j < arrayList3.size()) {
                    String[] stringArray = (String[])arrayList3.get(j);
                    for (int i2 = 0; i2 < stringArray.length; ++i2) {
                        String string3 = stringArray[i2];
                        String[] stringArray2 = string3.split(":");
                        if (stringArray2.length != 2) continue;
                        String string4 = stringArray2[0];
                        String[] stringArray3 = stringArray2[1].split(";");
                        int[] nArray = new int[stringArray3.length];
                        for (int i3 = 0; i3 < stringArray3.length; ++i3) {
                            nArray[i3] = Integer.parseInt(stringArray3[i3]);
                        }
                        azX azX2 = new azX(string4, nArray);
                        nM.a(azX2);
                    }
                }
                arrayList.add(nM);
            }
        }
        catch (aih_2 aih_22) {
            a.error((Object)"Exception", (Throwable)aih_22);
        }
        return arrayList;
    }

    public String toString() {
        return this.m_name;
    }
}

