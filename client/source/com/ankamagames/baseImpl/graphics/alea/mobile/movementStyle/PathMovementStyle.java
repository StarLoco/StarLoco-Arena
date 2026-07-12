package com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle;

import com.ankamagames.baseImpl.graphics.alea.mobile.StyleMobile;
import com.ankamagames.framework.kernel.core.maths.Direction8;

public interface PathMovementStyle {
  void setMobile(StyleMobile paramStyleMobile);
  
  int getCellSpeed();
  
  int getAirImpulsion();
  
  void onStandingOnLastCell();
  
  void onMovingOnAir(double paramDouble);
  
  void onMovingOnGround(int paramInt);
  
  void onWaiting();
  
  void onDirectionChanged(Direction8 paramDirection8);
  
  boolean createPathOnSetPosition();
  
  boolean isAirImpulsionNeeded(int paramInt);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\movementStyle\PathMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */