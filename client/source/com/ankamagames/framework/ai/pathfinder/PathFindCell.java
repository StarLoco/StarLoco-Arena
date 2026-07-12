package com.ankamagames.framework.ai.pathfinder;

import com.ankamagames.framework.kernel.core.maths.Direction8;

public interface PathFindCell {
  public static final short IMPOSSIBLE_ALTITUDE = -32768;
  
  int getX();
  
  int getY();
  
  boolean getMovementValidity(PathFindMover paramPathFindMover, short paramShort, Direction8 paramDirection8);
  
  short getArrivalAltitude(PathFindMover paramPathFindMover, short paramShort, Direction8 paramDirection8, PathFindParameters paramPathFindParameters);
  
  short getMaximumAltitude();
  
  boolean getMovementAcrossValidity(PathFindMover paramPathFindMover, short paramShort1, Direction8 paramDirection81, short paramShort2, Direction8 paramDirection82, PathFindParameters paramPathFindParameters);
  
  boolean isWalkable(short paramShort);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFindCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */