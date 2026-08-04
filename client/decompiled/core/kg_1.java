/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from KG
 */
public class kg_1
extends pt_2 {
    public static final String TAG = "ListCondition";
    public static final String boN = "evenIndex";
    public static final String boO = "oddIndex";
    public static final String boP = "index";
    public static final String boQ = "tableIndex";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (!(object instanceof qa_1)) {
            return false;
        }
        qa_1 qa_12 = (qa_1)object;
        px_2 px_22 = qa_12.getRenderableCollection();
        if (px_22 == null) {
            return false;
        }
        if (this.fZ != null) {
            if (this.fZ.equalsIgnoreCase(boN)) {
                int n2 = px_22.getTableIndex(qa_12);
                return this.dD.isValid(n2 % 2 == 0);
            }
            if (this.fZ.equalsIgnoreCase(boO)) {
                int n3 = px_22.getTableIndex(qa_12);
                return this.dD.isValid(n3 % 2 != 0);
            }
            if (this.fZ.equalsIgnoreCase(boP)) {
                int n4 = px_22.getItemIndex(qa_12.getItemValue());
                return this.dD.isValid(n4);
            }
            if (this.fZ.equalsIgnoreCase(boQ)) {
                int n5 = px_22.getTableIndex(qa_12);
                return this.dD.isValid(n5);
            }
        }
        return false;
    }
}

