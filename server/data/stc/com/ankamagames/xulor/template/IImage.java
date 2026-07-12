package com.ankamagames.xulor.template;

import com.ankamagames.xulor.util.Alignment;
import com.ankamagames.xulor.util.Pixmap;
import com.ankamagames.xulor.util.ThemeTexture;

public abstract interface IImage
{
  public abstract void setAlign(Alignment paramAlignment);
  
  public abstract void setKeepAspectRatio(boolean paramBoolean);
  
  public abstract void setScaled(boolean paramBoolean);
  
  public abstract void setPixmap(Pixmap paramPixmap);
  
  public abstract void setTexture(ThemeTexture paramThemeTexture);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */