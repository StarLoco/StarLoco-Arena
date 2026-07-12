package com.ankamagames.framework.graphics.opengl.base.render;

import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
import javax.media.opengl.GL;

public abstract interface Transformable
{
  public abstract void pushMatrixFront(GLMatrix paramGLMatrix, int paramInt);
  
  public abstract void pushMatrixBack(GLMatrix paramGLMatrix, int paramInt);
  
  public abstract GLMatrix popMatrixFront(int paramInt);
  
  public abstract GLMatrix popMatrixBack(int paramInt);
  
  public abstract void clearMatrices(int paramInt);
  
  public abstract void removeMatrix(GLMatrix paramGLMatrix, int paramInt);
  
  public abstract void applyTransformations(GL paramGL, int paramInt);
  
  public abstract void saveMatrix(GL paramGL, int paramInt);
  
  public abstract void restoreMatrices(GL paramGL, int paramInt);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\Transformable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */