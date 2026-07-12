package com.ankamagames.framework.graphics.opengl.base.render;

import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
import com.ankamagames.framework.graphics.opengl.base.ManagedTexture;
import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
import com.ankamagames.framework.graphics.opengl.base.material.BaseMaterial;
import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
import java.util.ArrayList;
import javax.media.opengl.GL;

public abstract interface GLObject
  extends Comparable<GLObject>, GLRenderable
{
  public abstract void initialize();
  
  public abstract void uninitialize();
  
  public abstract void drawPrimitives(GL paramGL);
  
  public abstract void setPreRenderStates(GLRenderStates paramGLRenderStates);
  
  public abstract void applyPreRenderStates(GL paramGL);
  
  public abstract void setPostRenderStates(GLRenderStates paramGLRenderStates);
  
  public abstract void applyPostRenderStates(GL paramGL);
  
  public abstract void setViewPort(ViewPort paramViewPort);
  
  public abstract void pushMaterialFront(BaseMaterial paramBaseMaterial);
  
  public abstract void pushMaterialBack(BaseMaterial paramBaseMaterial);
  
  public abstract BaseMaterial popMaterialFront();
  
  public abstract BaseMaterial popMaterialBack();
  
  public abstract void removeMaterial(BaseMaterial paramBaseMaterial);
  
  public abstract void clearMaterials();
  
  public abstract void applyMaterial();
  
  public abstract void pushTextureFront(BaseTexture paramBaseTexture);
  
  public abstract void pushTextureBack(BaseTexture paramBaseTexture);
  
  public abstract BaseTexture popTextureFront();
  
  public abstract BaseTexture popTextureBack();
  
  public abstract void removeTexture(ManagedTexture paramManagedTexture);
  
  public abstract void clearTextures();
  
  public abstract void addChild(GLObject paramGLObject);
  
  public abstract void removeChild(GLObject paramGLObject);
  
  public abstract void removeAllChilds();
  
  public abstract boolean hasChild();
  
  public abstract GLObject getFirstChild();
  
  public abstract GLObject getNextChild();
  
  public abstract GLObject getPreviousChild();
  
  public abstract int getChildCount();
  
  public abstract GLObject getChildAt(int paramInt);
  
  public abstract ArrayList<GLObject> getChildren();
  
  public abstract GLObject getParent();
  
  public abstract void setParent(GLObject paramGLObject);
  
  public abstract void sort();
  
  public abstract float getSortPosition();
  
  public abstract void setVisible(boolean paramBoolean);
  
  public abstract void setVisibilityInheritance(boolean paramBoolean);
  
  public abstract void setEffectEnabled(boolean paramBoolean);
  
  public abstract void setEffect(Effect paramEffect, boolean paramBoolean);
  
  public abstract Effect getEffect();
  
  public abstract void setEffectContext(EffectContext paramEffectContext);
  
  public abstract EffectContext getEffectContext();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\GLObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */