package com.ankamagames.framework.ai.pathfinder;

import com.ankamagames.framework.kernel.core.maths.Direction8;

public abstract interface PathFindCell
{
  public static final short IMPOSSIBLE_ALTITUDE = -32768;
  
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract boolean getMovementValidity(PathFindMover paramPathFindMover, short paramShort, Direction8 paramDirection8);
  
  public abstract short getArrivalAltitude(PathFindMover paramPathFindMover, short paramShort, Direction8 paramDirection8, PathFindParameters paramPathFindParameters);
  
  public abstract short getMaximumAltitude();
  
  public abstract boolean getMovementAcrossValidity(PathFindMover paramPathFindMover, short paramShort1, Direction8 paramDirection81, short paramShort2, Direction8 paramDirection82, PathFindParameters paramPathFindParameters);
  
  public abstract boolean isWalkable(short paramShort);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFindCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */