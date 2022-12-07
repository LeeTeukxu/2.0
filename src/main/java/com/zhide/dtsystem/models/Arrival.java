package com.zhide.dtsystem.models;

import java.io.Serializable;

public class Arrival implements Serializable {
    tbArrivalRegistration arrival;

    public tbArrivalRegistration getArrival() {
        return arrival;
    }

    public void setArrival(tbArrivalRegistration arrival) {
        this.arrival = arrival;
    }
}
