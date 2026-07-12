package com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle;

import com.ankamagames.baseImpl.graphics.alea.mobile.StyleMobile;
import com.ankamagames.framework.kernel.core.maths.Direction8;

public abstract interface PathMovementStyle
{
  public abstract void setMobile(StyleMobile paramStyleMobile);
  
  public abstract int getCellSpeed();
  
  public abstract int getAirImpulsion();
  
  public abstract void onStandingOnLastCell();
  
  public abstract void onMovingOnAir(double paramDouble);
  
  public abstract void onMovingOnGround(int paramInt);
  
  public abstract void onWaiting();
  
  public abstract void onDirectionChanged(Direction8 paramDirection8);
  
  public abstract boolean createPathOnSetPosition();
  
  public abstract boolean isAirImpulsionNeeded(int paramInt);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\movementStyle\PathMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */