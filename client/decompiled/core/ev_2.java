/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.ArrayList;

/*
 * Renamed from Ev
 */
public class ev_2
extends su_1 {
    protected void k(ArrayList arrayList) {
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(j);
            if (!displayedScreenElement.atV().avY().aop()) continue;
            this.bLo.add(displayedScreenElement);
        }
    }
}

