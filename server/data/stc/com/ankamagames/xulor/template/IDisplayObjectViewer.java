package com.ankamagames.xulor.template;

import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;

public abstract interface IDisplayObjectViewer
  extends ISceneCanvas
{
  public abstract void setDescriptorLibrary(ModifiableDescriptorLibrary paramModifiableDescriptorLibrary);
  
  public abstract AbstractDescriptorLibrary getDescriptorLibrary();
  
  public abstract void setLinkage(String paramString);
  
  public abstract String getLinkage();
  
  public abstract int getXOffset();
  
  public abstract void setXOffset(int paramInt);
  
  public abstract int getYOffset();
  
  public abstract void setYOffset(int paramInt);
  
  public abstract float getScale();
  
  public abstract void setScale(float paramFloat);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IDisplayObjectViewer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */