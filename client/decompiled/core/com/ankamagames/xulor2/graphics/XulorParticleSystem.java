/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.xulor2.graphics;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;

public final class XulorParticleSystem
extends ParticleSystem {
    public XulorParticleSystem() {
        super(false);
    }

    public void b(Entity entity) {
        double d = this.getX();
        double d2 = this.getY();
        float f = 0.0f;
        float f2 = 0.0f;
        if (this.Lh) {
            f = (float)((double)f + d);
            f2 = (float)((double)f2 + d2);
        }
        if (this.aFz() == 0) {
            return;
        }
        entity.i(this);
        for (int j = 0; j < this.aFz(); ++j) {
            GeometryMesh geometryMesh = (GeometryMesh)this.ma(j);
            geometryMesh.ab().clear();
        }
        this.a(this.bZI, f, f2, 0);
        this.alW();
        this.bZT = null;
    }

    private void a(Particle particle, float f, float f2, int n2) {
        if (particle != this.bZI && particle.IT > 0.004f) {
            this.b(particle, particle.Hk + f, particle.Hl + f2, n2);
        }
        if (particle.Li == null) {
            return;
        }
        int n3 = particle.Li.size();
        for (int j = 0; j < n3; ++j) {
            Particle particle2;
            int n4;
            Emitter emitter = (Emitter)particle.Li.get(j);
            ArrayList arrayList = emitter.uA;
            if (arrayList == null) continue;
            int n5 = arrayList.size();
            if (emitter.dnJ.Lh) {
                for (n4 = 0; n4 < n5; ++n4) {
                    particle2 = (Particle)arrayList.get(n4);
                    this.a(particle2, particle.Hk + f, particle.Hl + f2, n2 + 1);
                }
                continue;
            }
            for (n4 = 0; n4 < n5; ++n4) {
                particle2 = (Particle)arrayList.get(n4);
                this.a(particle2, f, f2, n2 + 1);
            }
        }
    }
}

