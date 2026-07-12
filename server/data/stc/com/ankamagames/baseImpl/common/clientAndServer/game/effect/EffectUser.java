package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
import com.ankamagames.baseImpl.common.clientAndServer.game.part.PartLocalisator;
import com.ankamagames.framework.ai.targetfinder.Target;
import com.ankamagames.framework.kernel.core.maths.Direction;
import com.ankamagames.framework.kernel.core.maths.Point3;

public abstract interface EffectUser
  extends Target
{
  public abstract long getId();
  
  public abstract RunningEffectManager getRunningEffectManager();
  
  public abstract Point3 getPosition();
  
  public abstract void setPosition(int paramInt1, int paramInt2, short paramShort);
  
  public abstract void setPosition(Point3 paramPoint3);
  
  public abstract boolean hasCharacteristic(CharacteristicType paramCharacteristicType);
  
  public abstract AbstractCharacteristic getCharacteristic(CharacteristicType paramCharacteristicType);
  
  public abstract int getCharacteristicValue(CharacteristicType paramCharacteristicType)
    throws UnsupportedOperationException;
  
  public abstract Direction getDirection();
  
  public abstract void setDirection(Direction paramDirection);
  
  public abstract PartLocalisator getPartLocalisator();
  
  public abstract boolean shouldBeDead();
  
  public abstract boolean isDead();
  
  public abstract void onDeath();
  
  public abstract void onEffectUsed();
  
  public abstract void die();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */