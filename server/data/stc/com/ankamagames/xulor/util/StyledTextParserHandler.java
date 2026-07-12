package com.ankamagames.xulor.util;

import java.awt.Color;

public abstract interface StyledTextParserHandler
{
  public abstract Font getFont();
  
  public abstract void setFont(Font paramFont);
  
  public abstract void append(String paramString, Font paramFont, Color paramColor);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\StyledTextParserHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */