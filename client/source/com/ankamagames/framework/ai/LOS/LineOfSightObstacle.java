package com.ankamagames.framework.ai.LOS;

import com.ankamagames.framework.kernel.core.maths.Point3;

public interface LineOfSightObstacle {
  boolean isBlockingLOS(Object paramObject);
  
  boolean isPotentialTarget();
  
  Point3 getPosition();
  
  short getHeight();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\LOS\LineOfSightObstacle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */