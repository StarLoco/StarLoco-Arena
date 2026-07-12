package com.ankamagames.alea;

import com.ankamagames.framework.ai.pathfinder.PathFindCell;
import com.ankamagames.framework.kernel.core.maths.Direction8;
import com.ankamagames.framework.struct.space.Partition;

public abstract interface AleaWorldCell
  extends Partition, PathFindCell
{
  public abstract boolean isLineOfSightValid(short paramShort, Direction8 paramDirection8);
  
  public abstract boolean isLineOfSightEndValid(short paramShort);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaWorldCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */