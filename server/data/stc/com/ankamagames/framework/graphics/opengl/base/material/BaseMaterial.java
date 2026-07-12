package com.ankamagames.framework.graphics.opengl.base.material;

public abstract interface BaseMaterial
{
  public abstract void reset();
  
  public abstract BaseMaterial duplicate();
  
  public abstract boolean hasChanged();
  
  public abstract boolean hasDefaultValue();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\material\BaseMaterial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */