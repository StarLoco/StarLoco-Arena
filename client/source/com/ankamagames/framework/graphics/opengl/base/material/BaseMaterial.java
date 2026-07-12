package com.ankamagames.framework.graphics.opengl.base.material;

public interface BaseMaterial {
  void reset();
  
  BaseMaterial duplicate();
  
  boolean hasChanged();
  
  boolean hasDefaultValue();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\material\BaseMaterial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */