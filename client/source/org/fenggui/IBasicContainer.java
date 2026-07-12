package org.fenggui;

import org.fenggui.io.IOStreamSaveable;

public interface IBasicContainer extends IWidget, IOStreamSaveable {
  IWidget getNextWidget(IWidget paramIWidget);
  
  IWidget getPreviousWidget(IWidget paramIWidget);
  
  IWidget getNextTraversableWidget(IWidget paramIWidget);
  
  IWidget getPreviousTraversableWidget(IWidget paramIWidget);
  
  void layout();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\IBasicContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */