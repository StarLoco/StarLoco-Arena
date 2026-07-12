package com.ankamagames.baseImpl.graphics.alea.adviser;

import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
import com.ankamagames.graphics.isometric.IsoWorldTarget;

public interface Adviser extends GLObject {
  IsoWorldTarget getTarget();
  
  void setTarget(IsoWorldTarget paramIsoWorldTarget);
  
  int getXOffset();
  
  void setXOffset(int paramInt);
  
  int getYOffset();
  
  void setYOffset(int paramInt);
  
  double getWorldX();
  
  double getWorldY();
  
  double getAltitude();
  
  void setPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  int getDuration();
  
  boolean isAlive();
  
  void process(AleaWorldScene paramAleaWorldScene, long paramLong, int paramInt);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\Adviser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */