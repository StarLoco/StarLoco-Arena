/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
import java.util.Comparator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum DisplayedElementComparator {
    DEPTH_COMPARATOR(new Comparator<DisplayedElement>(){

        @Override
        public int compare(DisplayedElement o1, DisplayedElement o2) {
            float z2;
            float z1 = o1.getZOrder();
            if (z1 > (z2 = o2.getZOrder())) {
                return -1;
            }
            if (z1 < z2) {
                return 1;
            }
            return 0;
        }
    }),
    MOUSE_DISTANCE_COMPARATOR(new Comparator<DisplayedElement>(){

        @Override
        public int compare(DisplayedElement o1, DisplayedElement o2) {
            double d2;
            double d1 = o1.getDistanceFromTopToMouse();
            if (d1 > (d2 = o2.getDistanceFromTopToMouse())) {
                return 1;
            }
            if (d1 < d2) {
                return -1;
            }
            return 0;
        }
    });

    private Comparator<DisplayedElement> m_comparator;

    private DisplayedElementComparator(Comparator<DisplayedElement> comparator) {
        this.m_comparator = comparator;
    }

    public Comparator<DisplayedElement> getComparator() {
        return this.m_comparator;
    }
}

