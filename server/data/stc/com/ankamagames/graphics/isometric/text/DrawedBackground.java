package com.ankamagames.graphics.isometric.text;

import javax.media.opengl.GL;

public abstract interface DrawedBackground
{
  public abstract int getLeftMargin();
  
  public abstract int getTopMargin();
  
  public abstract int getRightMargin();
  
  public abstract int getBottomMargin();
  
  public abstract void drawBubbleBackground(GL paramGL, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\text\DrawedBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */