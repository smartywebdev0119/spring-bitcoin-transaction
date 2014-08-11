package com.yoghurt.crypto.transactions.client.widget.script;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Unit;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.domain.transaction.script.ScriptPart;
import com.yoghurt.crypto.transactions.client.util.Color;
import com.yoghurt.crypto.transactions.client.util.EllipsisUtil;
import com.yoghurt.crypto.transactions.client.util.ScriptOperationUtil;
import com.yoghurt.crypto.transactions.client.util.ScriptPartType;
import com.yoghurt.crypto.transactions.client.util.ScriptType;
import com.yoghurt.crypto.transactions.client.util.TransactionPartColorPicker;
import com.yoghurt.crypto.transactions.client.widget.ContextField;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet;

public class ScriptViewer extends ContextFieldSet<ScriptPart> {
  private static final int HASH_ELLIPSIS = 20;
  private final ScriptType type;

  public ScriptViewer(final ScriptType type) {
    super(new SimpleScriptContextFactory());
    this.type = type;
  }

  public void setScript(final ArrayList<ScriptPart> instructions) {
    for (final ScriptPart instruction : instructions) {
      addField(instruction);
    }
  }

  @Override
  protected Color getFieldColor(final ScriptPart value) {
    if(ScriptOperationUtil.isDataPushOperation(value.getOperation())) {
      return TransactionPartColorPicker.getFieldColor(ScriptOperationUtil.getScriptPartType(type, ScriptPartType.PUSH_DATA));
    } else {
      return TransactionPartColorPicker.getFieldColor(ScriptOperationUtil.getScriptPartType(type, ScriptPartType.OP_CODE));
    }
  }

  @Override
  protected String getFieldText(final ScriptPart value) {
    if(ScriptOperationUtil.isDataPushOperation(value.getOperation())) {
      return EllipsisUtil.applyInner(Str.toString(Hex.encode(value.getBytes())).toUpperCase(), HASH_ELLIPSIS);
    } else {
      return value.getOperation().name();
    }
  }

  @Override
  protected void addField(final ContextField<ScriptPart> field) {
    field.getElement().getStyle().setMarginRight(10, Unit.PX);

    super.addField(field);
  }
}
