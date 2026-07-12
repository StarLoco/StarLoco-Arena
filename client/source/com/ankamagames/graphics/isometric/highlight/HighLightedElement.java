package com.ankamagames.graphics.isometric.highlight;

import com.ankamagames.framework.kernel.core.maths.Point3;

public interface HighLightedElement extends HighLightMeshTransformer {
  UniqueHandleReference getLayerReference();
  
  Point3 getCoordinates();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightedElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */