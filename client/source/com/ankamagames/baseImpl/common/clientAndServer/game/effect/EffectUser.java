package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
import com.ankamagames.baseImpl.common.clientAndServer.game.part.PartLocalisator;
import com.ankamagames.framework.ai.targetfinder.Target;
import com.ankamagames.framework.kernel.core.maths.Direction;
import com.ankamagames.framework.kernel.core.maths.Point3;

public interface EffectUser extends Target {
  long getId();
  
  RunningEffectManager getRunningEffectManager();
  
  Point3 getPosition();
  
  void setPosition(int paramInt1, int paramInt2, short paramShort);
  
  void setPosition(Point3 paramPoint3);
  
  boolean hasCharacteristic(CharacteristicType paramCharacteristicType);
  
  AbstractCharacteristic getCharacteristic(CharacteristicType paramCharacteristicType);
  
  int getCharacteristicValue(CharacteristicType paramCharacteristicType) throws UnsupportedOperationException;
  
  Direction getDirection();
  
  void setDirection(Direction paramDirection);
  
  PartLocalisator getPartLocalisator();
  
  boolean shouldBeDead();
  
  boolean isDead();
  
  void onDeath();
  
  void onEffectUsed();
  
  void die();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */