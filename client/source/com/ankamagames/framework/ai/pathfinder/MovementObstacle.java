package com.ankamagames.framework.ai.pathfinder;

import com.ankamagames.framework.kernel.core.maths.Point3;

public interface MovementObstacle {
  float getMovementObstruction();
  
  Point3 getPosition();
  
  short getHeight();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\MovementObstacle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */