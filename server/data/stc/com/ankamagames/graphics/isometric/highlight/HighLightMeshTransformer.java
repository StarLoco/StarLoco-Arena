package com.ankamagames.graphics.isometric.highlight;

import com.ankamagames.graphics.isometric.lines.Segment;
import com.ankamagames.graphics.isometric.lines.SegmentMesh;

abstract interface HighLightMeshTransformer
{
  public abstract void transformHighLightMesh(HighLightMesh paramHighLightMesh, int paramInt);
  
  public abstract void transformLineMesh(SegmentMesh paramSegmentMesh, Segment paramSegment, double paramDouble1, double paramDouble2, int paramInt, float paramFloat);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightMeshTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */