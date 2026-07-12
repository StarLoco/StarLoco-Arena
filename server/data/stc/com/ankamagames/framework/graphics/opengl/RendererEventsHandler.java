package com.ankamagames.framework.graphics.opengl;

import javax.media.opengl.GLAutoDrawable;

public abstract interface RendererEventsHandler
{
  public abstract void onInit(GLAutoDrawable paramGLAutoDrawable);
  
  public abstract void onDisplay(GLAutoDrawable paramGLAutoDrawable);
  
  public abstract void onReshape(GLAutoDrawable paramGLAutoDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void onDisplayChanged(GLAutoDrawable paramGLAutoDrawable, boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\RendererEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */