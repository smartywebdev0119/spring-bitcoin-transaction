package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.yoghurt.crypto.transactions.client.ui.BlockActivity;
import com.yoghurt.crypto.transactions.client.ui.ConfigActivity;
import com.yoghurt.crypto.transactions.client.ui.MineActivity;
import com.yoghurt.crypto.transactions.client.ui.ScriptActivity;
import com.yoghurt.crypto.transactions.client.ui.StartupActivity;
import com.yoghurt.crypto.transactions.client.ui.TransactionActivity;

public class ApplicationActivityMapper implements ActivityMapper {

  @Inject
  private ActivityFactory factory;

  @Override
  public Activity getActivity(final Place place) {
    Activity presenter = null;

    if (place instanceof StartupPlace) {
      presenter = factory.createStartupPresenter((StartupPlace) place);
    } else if (place instanceof TransactionPlace) {
      presenter = factory.createTransactionPresenter((TransactionPlace) place);
    } else if (place instanceof BlockPlace) {
      presenter = factory.createBlockPresenter((BlockPlace) place);
    } else if (place instanceof MinePlace) {
      presenter = factory.createMinePresenter((MinePlace) place);
    } else if (place instanceof ScriptPlace) {
      presenter = factory.createScriptPresenter((ScriptPlace) place);
    } else if (place instanceof ConfigPlace) {
      presenter = factory.createConfigPresenter((ConfigPlace) place);
    }

    return presenter;
  }

  public interface ActivityFactory {
    StartupActivity createStartupPresenter(StartupPlace place);

    ConfigActivity createConfigPresenter(ConfigPlace place);

    BlockActivity createBlockPresenter(BlockPlace place);

    TransactionActivity createTransactionPresenter(TransactionPlace place);

    MineActivity createMinePresenter(MinePlace place);

    ScriptActivity createScriptPresenter(ScriptPlace place);
  }
}
