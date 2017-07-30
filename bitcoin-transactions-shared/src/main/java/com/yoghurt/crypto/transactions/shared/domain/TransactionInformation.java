package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionInformation implements Serializable, IsSerializable {
  private static final long serialVersionUID = -5230934399747974590L;

  private TransactionState state;
  private Date time;
  private int confirmations;
  private String blockHash;
  private int fee;

  private String hex;

  public TransactionInformation() {}

  public TransactionState getState() {
    return state;
  }

  public void setState(final TransactionState state) {
    this.state = state;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(final Date time) {
    this.time = time;
  }

  public int getConfirmations() {
    return confirmations;
  }

  public void setConfirmations(final int confirmations) {
    this.confirmations = confirmations;
  }

  public void setBlockHash(final String blockHash) {
    this.blockHash = blockHash;
  }

  public String getBlockHash() {
    return blockHash;
  }

  public int getFee() {
    return fee;
  }

  public void setFee(final int fee) {
    this.fee = fee;
  }

  @Override
  public String toString() {
    return "TransactionInformation [state=" + state + ", block=" + blockHash + ", time=" + time + ", confirmations=" + confirmations + "]";
  }

  public void setRawHex(final String hex) {
    this.hex = hex;
  }

  public String getRawHex() {
    return hex;
  }
}
