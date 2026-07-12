package com.ankamagames.framework.ai.pathfinder;

import com.ankamagames.framework.kernel.core.maths.Point3;

public abstract interface MovementObstacle
{
  public abstract float getMovementObstruction();
  
  public abstract Point3 getPosition();
  
  public abstract short getHeight();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\MovementObstacle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */