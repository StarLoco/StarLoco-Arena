/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from NJ
 */
public class nj_2 {
    private ArrayList ec;
    private ArrayList c;
    private int bfO;
    private int bfN;

    public nj_2(ArrayList arrayList, ArrayList arrayList2, int n2, int n3) {
        this.bfO = n2;
        this.bfN = n3;
        this.ec = arrayList;
        this.c = arrayList2;
    }

    public ArrayList getItems() {
        return this.ec;
    }

    public void n(ArrayList arrayList) {
        this.ec = arrayList;
    }

    public int getRowCount() {
        return this.bfO;
    }

    public void setRowCount(int n2) {
        this.bfO = n2;
    }

    public int getColumnCount() {
        return this.bfN;
    }

    public void setColumnCount(int n2) {
        this.bfN = n2;
    }

    public ArrayList d() {
        return this.c;
    }

    public void e(ArrayList arrayList) {
        this.c = arrayList;
    }
}

