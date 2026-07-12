package com.ankamagames.xulor.template;

import com.ankamagames.xulor.util.Alignment;
import com.ankamagames.xulor.util.Pixmap;
import com.ankamagames.xulor.util.ThemeTexture;

public interface IImage {
  void setAlign(Alignment paramAlignment);
  
  void setKeepAspectRatio(boolean paramBoolean);
  
  void setScaled(boolean paramBoolean);
  
  void setPixmap(Pixmap paramPixmap);
  
  void setTexture(ThemeTexture paramThemeTexture);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */