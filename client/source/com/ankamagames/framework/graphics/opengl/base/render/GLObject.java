package com.ankamagames.framework.graphics.opengl.base.render;

import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
import com.ankamagames.framework.graphics.opengl.base.ManagedTexture;
import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
import com.ankamagames.framework.graphics.opengl.base.material.BaseMaterial;
import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
import java.util.ArrayList;
import javax.media.opengl.GL;

public interface GLObject extends Comparable<GLObject>, GLRenderable {
  void initialize();
  
  void uninitialize();
  
  void drawPrimitives(GL paramGL);
  
  void setPreRenderStates(GLRenderStates paramGLRenderStates);
  
  void applyPreRenderStates(GL paramGL);
  
  void setPostRenderStates(GLRenderStates paramGLRenderStates);
  
  void applyPostRenderStates(GL paramGL);
  
  void setViewPort(ViewPort paramViewPort);
  
  void pushMaterialFront(BaseMaterial paramBaseMaterial);
  
  void pushMaterialBack(BaseMaterial paramBaseMaterial);
  
  BaseMaterial popMaterialFront();
  
  BaseMaterial popMaterialBack();
  
  void removeMaterial(BaseMaterial paramBaseMaterial);
  
  void clearMaterials();
  
  void applyMaterial();
  
  void pushTextureFront(BaseTexture paramBaseTexture);
  
  void pushTextureBack(BaseTexture paramBaseTexture);
  
  BaseTexture popTextureFront();
  
  BaseTexture popTextureBack();
  
  void removeTexture(ManagedTexture paramManagedTexture);
  
  void clearTextures();
  
  void addChild(GLObject paramGLObject);
  
  void removeChild(GLObject paramGLObject);
  
  void removeAllChilds();
  
  boolean hasChild();
  
  GLObject getFirstChild();
  
  GLObject getNextChild();
  
  GLObject getPreviousChild();
  
  int getChildCount();
  
  GLObject getChildAt(int paramInt);
  
  ArrayList<GLObject> getChildren();
  
  GLObject getParent();
  
  void setParent(GLObject paramGLObject);
  
  void sort();
  
  float getSortPosition();
  
  void setVisible(boolean paramBoolean);
  
  void setVisibilityInheritance(boolean paramBoolean);
  
  void setEffectEnabled(boolean paramBoolean);
  
  void setEffect(Effect paramEffect, boolean paramBoolean);
  
  Effect getEffect();
  
  void setEffectContext(EffectContext paramEffectContext);
  
  EffectContext getEffectContext();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\GLObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */