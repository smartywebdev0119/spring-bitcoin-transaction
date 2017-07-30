package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace;
import com.yoghurt.crypto.transactions.client.place.TransactionPlace.TransactionDataType;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.client.util.ParseUtil;
import com.yoghurt.crypto.transactions.shared.domain.Transaction;
import com.yoghurt.crypto.transactions.shared.domain.TransactionInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;

/**
 * Refactor this catastrophe.
 *
 * - Gracefully handle the case where a raw transaction is passed.
 * - Less code
 * - Less confusing code
 * - Less redundant code
 * - Error to the views
 */
public class TransactionActivity extends LookupActivity<TransactionInformation, TransactionPlace> implements TransactionView.Presenter {
  private final TransactionView view;

  private boolean transactionHasError;

  @Inject
  public TransactionActivity(final TransactionView view, @Assisted final TransactionPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);
    this.view = view;
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final TransactionInformation transactionInformation) {
    panel.setWidget(view);
    
    final Transaction transactionFromHex = ParseUtil.getTransactionFromHex(transactionInformation.getRawHex());

    view.setTransaction(transactionFromHex, transactionHasError);

    // If an error occurred while parsing, don't bother getting the tx info
    if (transactionHasError) {
      return;
    }

    if (transactionInformation.getState() != null) {
      view.setTransactionInformation(transactionInformation);
    } else {
      service.getTransactionInformation(Str.toString(Hex.encode(transactionFromHex.getTransactionId())), new AppAsyncCallback<TransactionInformation>() {
        @Override
        public void onSuccess(final TransactionInformation result) {
          view.setTransactionInformation(result);
        }
      });
    }
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    panel.setWidget(view);

    view.setError(place.getHex(), caught);
  }

  @Override
  protected boolean mustPerformLookup(final TransactionPlace place) {
    return place.getType() == TransactionDataType.ID;
  }

  @Override
  protected TransactionInformation createInfo(final TransactionPlace place) {
    final TransactionInformation ti = new TransactionInformation();
    ti.setRawHex(place.getHex());
    return ti;
  }

  @Override
  protected void doLookup(final TransactionPlace place, final AsyncCallback<TransactionInformation> callback) {
    service.getTransactionInformation(place.getHex(), callback);
  }
}