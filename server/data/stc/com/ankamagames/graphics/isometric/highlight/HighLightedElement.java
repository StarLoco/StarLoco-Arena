package com.ankamagames.graphics.isometric.highlight;

import com.ankamagames.framework.kernel.core.maths.Point3;

public abstract interface HighLightedElement
  extends HighLightMeshTransformer
{
  public abstract UniqueHandleReference getLayerReference();
  
  public abstract Point3 getCoordinates();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightedElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */