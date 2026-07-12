package com.ankamagames.framework.graphics.opengl.base.render;

import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
import javax.media.opengl.GL;

public interface Transformable {
  void pushMatrixFront(GLMatrix paramGLMatrix, int paramInt);
  
  void pushMatrixBack(GLMatrix paramGLMatrix, int paramInt);
  
  GLMatrix popMatrixFront(int paramInt);
  
  GLMatrix popMatrixBack(int paramInt);
  
  void clearMatrices(int paramInt);
  
  void removeMatrix(GLMatrix paramGLMatrix, int paramInt);
  
  void applyTransformations(GL paramGL, int paramInt);
  
  void saveMatrix(GL paramGL, int paramInt);
  
  void restoreMatrices(GL paramGL, int paramInt);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\Transformable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */