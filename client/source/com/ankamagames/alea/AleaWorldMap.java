package com.ankamagames.alea;

import com.ankamagames.framework.fileFormat.document.DocumentContainer;
import com.ankamagames.framework.struct.space.ManageablePartition;

public interface AleaWorldMap extends ManageablePartition, DocumentContainer {
  void allocateGeometry(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void releaseGeometry();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaWorldMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */