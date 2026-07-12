package com.ankamagames.framework.kernel.core.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract interface MouseController
{
  public abstract boolean mouseClicked(MouseEvent paramMouseEvent);
  
  public abstract boolean mousePressed(MouseEvent paramMouseEvent);
  
  public abstract boolean mouseReleased(MouseEvent paramMouseEvent);
  
  public abstract boolean mouseEntered(MouseEvent paramMouseEvent);
  
  public abstract boolean mouseExited(MouseEvent paramMouseEvent);
  
  public abstract boolean mouseDragged(MouseEvent paramMouseEvent);
  
  public abstract boolean mouseMoved(MouseEvent paramMouseEvent);
  
  public abstract boolean mouseWheelMoved(MouseWheelEvent paramMouseWheelEvent);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\controllers\MouseController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */