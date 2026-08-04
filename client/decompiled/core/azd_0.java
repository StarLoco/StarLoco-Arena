/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from azD
 */
public class azd_0
extends ael_2 {
    private int dnX;
    private int dnY;
    private int dnZ;
    private int doa;
    private ArrayList dob;
    private ArrayList bhR;
    private mm_0 doc;
    private mm_0 dod;
    private mm_0 doe;
    private mm_0 dof;
    private mm_0 dog;
    private mm_0 blM;
    private mm_0 blN;
    private boolean blP;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.dnY = byteBuffer.getInt();
        this.dnZ = byteBuffer.getInt();
        this.doa = byteBuffer.getInt();
        this.dnX = byteBuffer.getInt();
        this.dob = new ArrayList();
        this.bhR = new ArrayList();
        this.doc = new mm_0();
        this.dod = new mm_0();
        this.doe = new mm_0();
        this.dof = new mm_0();
        this.dog = new mm_0();
        this.blM = new mm_0();
        this.blN = new mm_0();
        for (int j = 0; j < this.doa - this.dnZ; ++j) {
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            byte[] byArray3 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray3);
            this.dob.add(new String(byArray2));
            this.bhR.add(new String(byArray3));
            this.doc.add(byteBuffer.getShort());
            this.dod.add((short)byteBuffer.getInt());
            this.doe.add((short)byteBuffer.getInt());
            this.dof.add((short)byteBuffer.getInt());
            this.dog.add((short)byteBuffer.getInt());
            this.blM.add((short)byteBuffer.getInt());
            this.blN.add((short)byteBuffer.getInt());
        }
        this.blP = byteBuffer.get() != 0;
        return true;
    }

    public int getId() {
        return 27501;
    }

    public int aMb() {
        return this.dnY;
    }

    public int aMc() {
        return this.dnZ;
    }

    public int aMd() {
        return this.doa;
    }

    public int aMe() {
        return this.dnX;
    }

    public String getNames(int n2) {
        return (String)this.dob.get(n2);
    }

    public String gs(int n2) {
        return (String)this.bhR.get(n2);
    }

    public short mW(int n2) {
        return this.doc.get(n2);
    }

    public short mX(int n2) {
        return this.dod.get(n2);
    }

    public short mY(int n2) {
        return this.doe.get(n2);
    }

    public short mZ(int n2) {
        return this.dof.get(n2);
    }

    public short na(int n2) {
        return this.dog.get(n2);
    }

    public short gq(int n2) {
        return this.blM.get(n2);
    }

    public short gr(int n2) {
        return this.blN.get(n2);
    }

    public boolean VY() {
        return this.blP;
    }
}

