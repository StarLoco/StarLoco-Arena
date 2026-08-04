/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.particleSystem;

import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

class ParticleSystem$1
extends Particle {
    final /* synthetic */ ParticleSystem qd;

    ParticleSystem$1(ParticleSystem particleSystem) {
        this.qd = particleSystem;
    }

    public float getX() {
        return this.Lh ? 0.0f : this.qd.getX();
    }

    public float getY() {
        return this.Lh ? 0.0f : this.qd.getY();
    }

    public float id() {
        return this.Lh ? 0.0f : this.qd.id();
    }
}

