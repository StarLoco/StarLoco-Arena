/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.worldElement;

import com.ankamagames.baseImpl.graphics.alea.WorldElementManager;
import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;

public class GraphicalWorldElement
extends WorldElement {
    public GraphicalWorldElement(int elementId, int paramsCount, byte[] params, int state, int groupId) {
        super(elementId, paramsCount, params, state, groupId);
        this.m_element = WorldElementManager.getInstance().getGraphicalElement(elementId);
    }

    public GraphicalElement getElement() {
        return (GraphicalElement)this.m_element;
    }

    public double getWeight() {
        return this.getElement().getStateProperties(this.m_state).getWeight();
    }

    public double getHeight() {
        return this.getElement().getStateProperties(this.m_state).getHeight();
    }

    public double getVisualHeight() {
        return this.getElement().getStateProperties(this.m_state).getVisualHeight();
    }

    public byte getSlope() {
        return this.getElement().getStateProperties(this.m_state).getSlope();
    }
}

