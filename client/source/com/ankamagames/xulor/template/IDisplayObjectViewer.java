package com.ankamagames.xulor.template;

import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;

public interface IDisplayObjectViewer extends ISceneCanvas {
  void setDescriptorLibrary(ModifiableDescriptorLibrary paramModifiableDescriptorLibrary);
  
  AbstractDescriptorLibrary getDescriptorLibrary();
  
  void setLinkage(String paramString);
  
  String getLinkage();
  
  int getXOffset();
  
  void setXOffset(int paramInt);
  
  int getYOffset();
  
  void setYOffset(int paramInt);
  
  float getScale();
  
  void setScale(float paramFloat);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IDisplayObjectViewer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */