package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.misc.Color;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;

public class BlockHexViewer extends HexViewer<BlockPartType> {
  public BlockHexViewer() {
    super(new SimpleBlockContextWidget());
  }

  @Override
  protected Color getFieldColor(final Entry<BlockPartType, byte[]> value) {
    return BlockPartColorPicker.getFieldColor(value.getKey());
  }
}
