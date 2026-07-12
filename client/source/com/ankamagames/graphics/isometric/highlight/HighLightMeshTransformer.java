package com.ankamagames.graphics.isometric.highlight;

import com.ankamagames.graphics.isometric.lines.Segment;
import com.ankamagames.graphics.isometric.lines.SegmentMesh;

interface HighLightMeshTransformer {
  void transformHighLightMesh(HighLightMesh paramHighLightMesh, int paramInt);
  
  void transformLineMesh(SegmentMesh paramSegmentMesh, Segment paramSegment, double paramDouble1, double paramDouble2, int paramInt, float paramFloat);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightMeshTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */