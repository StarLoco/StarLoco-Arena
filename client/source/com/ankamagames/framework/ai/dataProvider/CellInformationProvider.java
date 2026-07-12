package com.ankamagames.framework.ai.dataProvider;

import com.ankamagames.framework.ai.pathfinder.PathFindCell;
import com.ankamagames.framework.kernel.core.maths.Direction8;

public interface CellInformationProvider {
  boolean getCellValidity(int paramInt1, int paramInt2, short paramShort);
  
  PathFindCell getPathFindCell(int paramInt1, int paramInt2, short paramShort);
  
  boolean getLineOfSightValidity(int paramInt1, int paramInt2, short paramShort, Direction8 paramDirection8);
  
  boolean getLineOfSightEndValidity(int paramInt1, int paramInt2, short paramShort);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\dataProvider\CellInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */