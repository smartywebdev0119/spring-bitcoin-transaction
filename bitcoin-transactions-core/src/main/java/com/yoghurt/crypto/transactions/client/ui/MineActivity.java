package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.place.MinePlace;
import com.yoghurt.crypto.transactions.client.place.MinePlace.MineDataType;
import com.yoghurt.crypto.transactions.client.util.MorphCallback;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;
import com.yoghurt.crypto.transactions.shared.util.block.BlockEncodeUtil;
import com.yoghurt.crypto.transactions.shared.util.block.BlockParseUtil;
import com.yoghurt.crypto.transactions.shared.util.transaction.ComputeUtil;

public class MineActivity extends LookupActivity<Block, MinePlace> implements MineView.Presenter {
  private static final long NONCE_DECREMENT = 10;

  private final MineView view;
  private final BlockchainRetrievalServiceAsync service;

  @Inject
  public MineActivity(final MineView view, @Assisted final MinePlace place, final BlockchainRetrievalServiceAsync service) {
    super(place);

    this.view = view;
    this.service = service;
  }

  @Override
  protected void doLookup(final MinePlace place, final AsyncCallback<Block> callback) {
    final MorphCallback<String, Block> morphCallback = new MorphCallback<String, Block>(callback) {
      @Override
      protected Block morphResult(final String result) {
        return getBlockFromHex(result);
      }
    };

    switch(place.getType()) {
    case HEIGHT:
      service.getRawBlockHex(Integer.parseInt(place.getPayload()), morphCallback);
      break;
    case LAST:
      service.getRawBlockHex(place.getPayload(), morphCallback);
      break;
    default:
      callback.onFailure(new IllegalStateException("No support lookup for type: " + place.getType().name()));
      return;
    }
  }

  @Override
  protected boolean mustPerformLookup(final MinePlace place) {
    return place.getType() == MineDataType.HEIGHT || place.getType() == MineDataType.LAST;
  }

  @Override
  protected Block createInfo(final MinePlace place) {
    return place.getType() == MineDataType.RAW ? getBlockFromHex(place.getPayload()) : null;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final Block block) {
    panel.setWidget(view);

    final RawBlockContainer rawBlock = new RawBlockContainer();
    try {
      BlockEncodeUtil.encodeBlock(block, rawBlock);
    } catch (final Throwable e) {
      // Eat.
    }

    block.setNonce(block.getNonce() - NONCE_DECREMENT);
    final byte[] blockHash = ComputeUtil.computeDoubleSHA256(rawBlock.values());
    ArrayUtil.reverse(blockHash);
    block.setBlockHash(blockHash);

    view.setInformation(block, rawBlock, true || place.getType() == MineDataType.LAST);
  }

  private Block getBlockFromHex(final String hex) {
    final Block b = new Block();

    try {
      BlockParseUtil.parseBlockBytes(Hex.decode(hex), b);
    } catch (final IllegalStateException e) {
      e.printStackTrace();
      // Eat
    }

    return b;
  }
}
