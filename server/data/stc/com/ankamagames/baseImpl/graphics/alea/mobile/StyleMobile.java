package com.ankamagames.baseImpl.graphics.alea.mobile;

import com.ankamagames.framework.kernel.core.maths.Direction8;

public abstract interface StyleMobile
{
  public abstract void setAnimation(String paramString);
  
  public abstract String getAnimation();
  
  public abstract void setDirection(Direction8 paramDirection8);
  
  public abstract void setMovementStyle(String paramString);
  
  public abstract String getStaticAnimationKey();
  
  public abstract void setStaticAnimationKey(String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\StyleMobile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */