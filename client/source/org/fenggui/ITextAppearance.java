package org.fenggui;

import org.fenggui.render.Font;
import org.fenggui.util.Color;

public interface ITextAppearance extends IAppearance {
  void setTextColor(Color paramColor);
  
  void setFont(Font paramFont);
  
  Color getTextColor();
  
  Font getFont();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ITextAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */