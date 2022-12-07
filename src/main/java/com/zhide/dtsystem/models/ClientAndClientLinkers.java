package com.zhide.dtsystem.models;

import java.io.Serializable;

public class ClientAndClientLinkers implements Serializable {
    tbClient client;
    tbClientLinkers clientLinkers;

    public tbClient getClient() {
        return client;
    }

    public void setClient(tbClient client) {
        this.client = client;
    }

    public tbClientLinkers getClientLinkers() {
        return clientLinkers;
    }

    public void setClientLinkers(tbClientLinkers clientLinkers) {
        this.clientLinkers = clientLinkers;
    }
}
