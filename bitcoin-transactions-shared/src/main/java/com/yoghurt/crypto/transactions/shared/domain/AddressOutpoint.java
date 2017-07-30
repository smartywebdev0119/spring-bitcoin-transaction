package com.yoghurt.crypto.transactions.shared.domain;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddressOutpoint extends TransactionOutPoint implements Serializable, IsSerializable {
	private static final long serialVersionUID = -2104537209099839858L;

	private TransactionOutput output;
	private boolean spent;

	public AddressOutpoint() {
	}

	public TransactionOutput getOutput() {
		return output;
	}

	public void setOutput(final TransactionOutput output) {
		this.output = output;
	}

	public void setSpent(final boolean spent) {
		this.spent = spent;
	}

	public boolean isSpent() {
		return spent;
	}
}
