/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aOZ
 */
public class aoz_1 {
    protected static final Logger a = Logger.getLogger(aoz_1.class);
    public ef_1 emW;
    public ef_1 emX;
    public static final float emY = 0.004f;
    private static final boolean emZ = false;
    private static aoz_1 ena = new aoz_1();
    private boolean aer = false;
    private aNe enb;
    private String enc;

    public static aoz_1 aYF() {
        return ena;
    }

    public void e(aNe aNe2) {
        this.enb = aNe2;
        this.v(aNe2.aXo().c("engine"));
    }

    public void lR(String string) {
        aAN aAN2 = aAN.aMW();
        aNe aNe2 = aAN2.aMX();
        try {
            aAN2.iJ(string);
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        this.e(aNe2);
    }

    public void lS(String string) {
        assert (this.enb != null) : "You must call initializePools before";
        k_0 k_02 = this.enb.aXo().c("engine");
        this.d(k_02, string);
        cx_0 cx_02 = cx_0.JY();
        db_2 db_22 = arX.cQT.iE();
        this.enc = string;
        this.emW = cx_02.a(db_22, -1296775008915292159L, string + "textures/maskAlpha.tga", false);
        this.emW.HE();
        this.emX = cx_02.a(db_22, -1296775008915292158L, string + "textures/perturb.tga", false);
        this.emX.HE();
        this.aer = true;
    }

    public final boolean aYG() {
        return this.aer;
    }

    public final String aYH() {
        return this.enc;
    }

    private aoz_1() {
    }

    private void v(k_0 k_02) {
        assert (k_02 != null) : "Unable to find engine node";
        k_0 k_03 = k_02.c("pools");
        assert (k_03 != null) : "Unable to find pools node";
        ArrayList arrayList = new ArrayList(64);
        this.a(k_03, arrayList);
        this.b(k_03, arrayList);
        this.c(k_03, arrayList);
        aoj_1.aXZ().E(arrayList);
    }

    private void a(k_0 k_02, ArrayList arrayList) {
        assert (k_02 != null) : "Unable to find pools node";
        k_0 k_03 = k_02.c("texture_pools");
        assert (k_03 != null) : "Unable to find texture_pools node";
        ArrayList arrayList2 = k_03.d("texture");
        assert (arrayList2 != null) : "Unable to find texture nodes";
        for (k_0 k_04 : arrayList2) {
            k_0 k_05 = k_04.f("width");
            assert (k_05 != null) : "Unable to find width parameter and it's not optionnal";
            k_0 k_06 = k_04.f("height");
            assert (k_06 != null) : "Unable to find height parameter and it's not optionnal";
            k_0 k_07 = k_04.f("bpp");
            assert (k_07 != null) : "Unable to find bpp parameter and it's not optionnal";
            assert (this.pR(k_07.getIntValue())) : "Texture with a bit per pixel of " + k_07.getIntValue() + " is not supported";
            k_0 k_08 = k_04.f("count");
            assert (k_08 != null) : "Unable to find count parameter and it's not optionnal";
            int n2 = 1;
            k_0 k_09 = k_04.f("compression");
            if (k_09 != null) {
                pw pw2 = new pw(k_09.getStringValue());
                assert (this.a(pw2)) : "Compression mode " + pw2.getString() + " is not supported";
                n2 = this.a(pw2, k_07.getIntValue());
            }
            int n3 = (int)((float)(k_05.getIntValue() * k_06.getIntValue() * k_07.getIntValue()) / (8.0f * (float)n2));
            cq_1 cq_12 = new cq_1();
            cq_12.a(rf.afJ);
            cq_12.fc(1);
            cq_12.setBufferSize(n3);
            arrayList.add(cq_12);
        }
    }

    private void b(k_0 k_02, ArrayList arrayList) {
        assert (k_02 != null) : "Unable to find pools node";
        k_0 k_03 = k_02.c("vertex_buffer_pools");
        assert (k_03 != null) : "Unable to find vertex_buffer_pools node";
        ArrayList arrayList2 = k_03.d("vertex_buffer");
        assert (arrayList2 != null) : "Unable to find vertex_buffer nodes";
        for (k_0 k_04 : arrayList2) {
            k_0 k_05 = k_04.f("vertex_size");
            assert (k_05 != null) : "Unable to find vertex_size parameter and it's not optionnal";
            assert (k_05.getIntValue() == 32) : "Vertex size should be equal to (color + position + texcoord)*4";
            k_0 k_06 = k_04.f("num_vertices");
            assert (k_06 != null) : "Unable to find num_vertices parameter and it's not optionnal";
            k_0 k_07 = k_04.f("count");
            assert (k_07 != null) : "Unable to find count parameter and it's not optionnal";
            int n2 = 2 * k_06.getIntValue() * 4;
            int n3 = 4 * k_06.getIntValue() * 4;
            int n4 = 2 * k_06.getIntValue() * 4;
            cq_1 cq_12 = new cq_1();
            cq_12.a(rf.afL);
            cq_12.fc(k_07.getIntValue());
            cq_12.setBufferSize(n4);
            this.a(arrayList, cq_12);
            cq_12 = new cq_1();
            cq_12.a(rf.afL);
            cq_12.fc(k_07.getIntValue());
            cq_12.setBufferSize(n2);
            this.a(arrayList, cq_12);
            cq_12 = new cq_1();
            cq_12.a(rf.afL);
            cq_12.fc(k_07.getIntValue());
            cq_12.setBufferSize(n3);
            this.a(arrayList, cq_12);
        }
    }

    private void a(ArrayList arrayList, cq_1 cq_12) {
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            cq_1 cq_13 = (cq_1)arrayList.get(j);
            if (cq_13.Ll() != cq_12.Ll() || cq_13.getBufferSize() != cq_12.getBufferSize()) continue;
            cq_13.fc(cq_13.Lk() + cq_12.Lk());
            return;
        }
        arrayList.add(cq_12);
    }

    private void c(k_0 k_02, ArrayList arrayList) {
        assert (k_02 != null) : "Unable to find pools node";
        k_0 k_03 = k_02.c("index_buffer_pools");
        assert (k_03 != null) : "Unable to find index_buffer_pools node";
        ArrayList arrayList2 = k_03.d("index_buffer");
        assert (arrayList2 != null) : "Unable to find index_buffer nodes";
        for (k_0 k_04 : arrayList2) {
            k_0 k_05 = k_04.f("size");
            assert (k_05 != null) : "Unable to find size parameter and it's not optionnal";
            k_0 k_06 = k_04.f("count");
            assert (k_06 != null) : "Unable to find count parameter and it's not optionnal";
            cq_1 cq_12 = new cq_1();
            cq_12.a(rf.afK);
            cq_12.fc(k_06.getIntValue());
            cq_12.setBufferSize(k_05.getIntValue());
            arrayList.add(cq_12);
        }
    }

    private void d(k_0 k_02, String string) {
        assert (k_02 != null) : "Unable to find engine node";
        k_0 k_03 = k_02.c("effects");
        assert (k_03 != null) : "Unable to find shaders node";
        ArrayList arrayList = k_03.d("effect");
        assert (arrayList != null) : "Unable to find effect nodes";
        for (k_0 k_04 : arrayList) {
            k_0 k_05 = k_04.f("name");
            assert (k_05 != null) : "Unable to find name parameter and it's not optionnal";
            k_0 k_06 = k_04.f("file");
            assert (k_06 != null) : "Unable to find file parameter and it's not optionnal";
            k_0 k_07 = k_04.f("class");
            String string2 = k_07 == null ? null : k_07.getStringValue();
            String string3 = string + k_06.getStringValue();
            ahA.axi().j(k_05.getStringValue(), string3, string2);
        }
    }

    private boolean pR(int n2) {
        switch (n2) {
            case 24: 
            case 32: {
                return true;
            }
        }
        return false;
    }

    private boolean a(pw pw2) {
        int n2 = pw2.getID();
        if (n2 == pw.bu("DXT1")) {
            return true;
        }
        if (n2 == pw.bu("DXT2")) {
            return true;
        }
        if (n2 == pw.bu("DXT3")) {
            return true;
        }
        if (n2 == pw.bu("DXT4")) {
            return true;
        }
        return n2 == pw.bu("DXT5");
    }

    private int a(pw pw2, int n2) {
        assert (this.a(pw2)) : "Compression mode " + pw2.getString() + " is not supported";
        assert (n2 == 32 || n2 == 24) : "Compressed texture with a bit per pixel of " + n2 + " is not supported";
        int n3 = pw2.getID();
        if (n3 == pw.bu("DXT1")) {
            return n2 / 4;
        }
        return n2 / 8;
    }
}

