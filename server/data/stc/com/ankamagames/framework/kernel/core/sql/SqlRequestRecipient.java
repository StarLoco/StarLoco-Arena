package com.ankamagames.framework.kernel.core.sql;

import com.ankamagames.framework.kernel.core.common.Validable;
import com.ankamagames.framework.kernel.core.common.message.MessageHandler;

public abstract interface SqlRequestRecipient
  extends MessageHandler, Validable
{
  public abstract void onExceptionRaised(SqlRequest paramSqlRequest, Exception paramException);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\sql\SqlRequestRecipient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */