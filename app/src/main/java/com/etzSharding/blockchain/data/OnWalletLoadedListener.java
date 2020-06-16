package com.etzSharding.blockchain.data;

import org.bitcoinj.wallet.Wallet;

public interface OnWalletLoadedListener {
    void onWalletLoaded(Wallet wallet);
}
