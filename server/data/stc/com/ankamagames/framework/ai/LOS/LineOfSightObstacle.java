package com.ankamagames.framework.ai.LOS;

import com.ankamagames.framework.kernel.core.maths.Point3;

public abstract interface LineOfSightObstacle
{
  public abstract boolean isBlockingLOS(Object paramObject);
  
  public abstract boolean isPotentialTarget();
  
  public abstract Point3 getPosition();
  
  public abstract short getHeight();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\LOS\LineOfSightObstacle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */