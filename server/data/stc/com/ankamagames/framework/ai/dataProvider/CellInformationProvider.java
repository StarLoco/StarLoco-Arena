package com.ankamagames.framework.ai.dataProvider;

import com.ankamagames.framework.ai.pathfinder.PathFindCell;
import com.ankamagames.framework.kernel.core.maths.Direction8;

public abstract interface CellInformationProvider
{
  public abstract boolean getCellValidity(int paramInt1, int paramInt2, short paramShort);
  
  public abstract PathFindCell getPathFindCell(int paramInt1, int paramInt2, short paramShort);
  
  public abstract boolean getLineOfSightValidity(int paramInt1, int paramInt2, short paramShort, Direction8 paramDirection8);
  
  public abstract boolean getLineOfSightEndValidity(int paramInt1, int paramInt2, short paramShort);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\dataProvider\CellInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */