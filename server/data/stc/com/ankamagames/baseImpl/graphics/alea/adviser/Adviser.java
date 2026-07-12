package com.ankamagames.baseImpl.graphics.alea.adviser;

import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
import com.ankamagames.graphics.isometric.IsoWorldTarget;

public abstract interface Adviser
  extends GLObject
{
  public abstract IsoWorldTarget getTarget();
  
  public abstract void setTarget(IsoWorldTarget paramIsoWorldTarget);
  
  public abstract int getXOffset();
  
  public abstract void setXOffset(int paramInt);
  
  public abstract int getYOffset();
  
  public abstract void setYOffset(int paramInt);
  
  public abstract double getWorldX();
  
  public abstract double getWorldY();
  
  public abstract double getAltitude();
  
  public abstract void setPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  public abstract int getDuration();
  
  public abstract boolean isAlive();
  
  public abstract void process(AleaWorldScene paramAleaWorldScene, long paramLong, int paramInt);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\Adviser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */