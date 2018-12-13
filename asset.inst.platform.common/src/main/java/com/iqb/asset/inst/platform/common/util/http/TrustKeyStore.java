package com.iqb.asset.inst.platform.common.util.http;

import javax.net.ssl.TrustManagerFactory;

public class TrustKeyStore {
    private TrustManagerFactory trustManagerFactory;

    TrustKeyStore(TrustManagerFactory trustManagerFactory) {
        this.trustManagerFactory = trustManagerFactory;
    }

    TrustManagerFactory getTrustManagerFactory() {
        return trustManagerFactory;
    }
}
