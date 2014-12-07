package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class LookupActivity<E, P extends Place> extends AbstractActivity {
  protected final P place;

  public LookupActivity(final P place) {
    this.place = place;
  }

  @Override
  public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
    if(!mustPerformLookup(place)) {
      doDeferredStart(panel, createInfo(place));
    } else {
      doLookup(place, new AsyncCallback<E>() {
        @Override
        public void onFailure(final Throwable caught) {
          throw new RuntimeException(caught);
        }

        @Override
        public void onSuccess(final E result) {
          doDeferredStart(panel, result);
        }
      });
    }
  }

  protected abstract void doLookup(final P place, final AsyncCallback<E> callback);

  /**
   * Whether or not to perform an asynchronous lookup.
   * 
   * @param place The place to determine whether or not to lookup for.
   * 
   * @return Whether or not to lookup asynchronously.
   */
  protected abstract boolean mustPerformLookup(final P place);

  /**
   * Create the desired information object out of the place (not doing asynchronous lookup, all information is already available)
   * 
   * @param place Place to get information out of.
   * 
   * @return Information
   */
  protected abstract E createInfo(final P place);

  /**
   * Deferredly start the activity after the information to be given to the concrete implementation has been determined.
   * 
   * @param panel Panel in which content may live.
   * @param info Information for which content will be created.
   */
  protected abstract void doDeferredStart(final AcceptsOneWidget panel, final E info);
}
