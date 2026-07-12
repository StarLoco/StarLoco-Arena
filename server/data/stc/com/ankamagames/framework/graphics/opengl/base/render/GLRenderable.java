package com.ankamagames.framework.graphics.opengl.base.render;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public abstract interface GLRenderable
{
  public abstract void init(GLAutoDrawable paramGLAutoDrawable);
  
  public abstract void setFrustumSize(float paramFloat1, float paramFloat2);
  
  public abstract void process(long paramLong, int paramInt);
  
  public abstract void processGeometry(GL paramGL);
  
  public abstract void display(GL paramGL);
  
  public abstract String toString();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\GLRenderable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */