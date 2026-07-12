package com.ankamagames.framework.graphics.opengl.base.render;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public interface GLRenderable {
  void init(GLAutoDrawable paramGLAutoDrawable);
  
  void setFrustumSize(float paramFloat1, float paramFloat2);
  
  void process(long paramLong, int paramInt);
  
  void processGeometry(GL paramGL);
  
  void display(GL paramGL);
  
  String toString();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\GLRenderable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */