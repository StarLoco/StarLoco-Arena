package com.ankamagames.graphics.isometric.text;

import javax.media.opengl.GL;

public interface DrawedBackground {
  int getLeftMargin();
  
  int getTopMargin();
  
  int getRightMargin();
  
  int getBottomMargin();
  
  void drawBubbleBackground(GL paramGL, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\text\DrawedBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */