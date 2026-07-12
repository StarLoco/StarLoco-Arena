package com.ankamagames.alea;

import com.ankamagames.framework.ai.pathfinder.PathFindCell;
import com.ankamagames.framework.kernel.core.maths.Direction8;
import com.ankamagames.framework.struct.space.Partition;

public interface AleaWorldCell extends Partition, PathFindCell {
  boolean isLineOfSightValid(short paramShort, Direction8 paramDirection8);
  
  boolean isLineOfSightEndValid(short paramShort);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaWorldCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */