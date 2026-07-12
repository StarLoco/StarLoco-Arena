package com.ankamagames.xulor.util;

import java.awt.Color;

public interface StyledTextParserHandler {
  Font getFont();
  
  void setFont(Font paramFont);
  
  void append(String paramString, Font paramFont, Color paramColor);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\StyledTextParserHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */