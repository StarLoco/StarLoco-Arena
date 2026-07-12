package com.ankamagames.framework.ai.dataProvider;

import com.ankamagames.framework.ai.LOS.LineOfSightObstacle;
import java.util.Iterator;

public abstract interface LineOfSightObstacleInformationProvider
{
  public abstract Iterator<? extends LineOfSightObstacle> getLineOfSightObstacles();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\dataProvider\LineOfSightObstacleInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */