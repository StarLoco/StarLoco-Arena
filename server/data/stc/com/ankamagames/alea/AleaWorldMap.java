package com.ankamagames.alea;

import com.ankamagames.framework.fileFormat.document.DocumentContainer;
import com.ankamagames.framework.struct.space.ManageablePartition;

public abstract interface AleaWorldMap
  extends ManageablePartition, DocumentContainer
{
  public abstract void allocateGeometry(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void releaseGeometry();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaWorldMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */