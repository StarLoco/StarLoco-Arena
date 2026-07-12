package com.ankamagames.framework.ai.dataProvider;

import com.ankamagames.framework.ai.LOS.LineOfSightObstacle;
import java.util.Iterator;

public interface LineOfSightObstacleInformationProvider {
  Iterator<? extends LineOfSightObstacle> getLineOfSightObstacles();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\dataProvider\LineOfSightObstacleInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */