package com.ankamagames.framework.graphics.opengl;

import javax.media.opengl.GLAutoDrawable;

public interface RendererEventsHandler {
  void onInit(GLAutoDrawable paramGLAutoDrawable);
  
  void onDisplay(GLAutoDrawable paramGLAutoDrawable);
  
  void onReshape(GLAutoDrawable paramGLAutoDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void onDisplayChanged(GLAutoDrawable paramGLAutoDrawable, boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\RendererEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */