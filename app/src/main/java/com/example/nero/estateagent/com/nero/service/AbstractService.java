package com.example.nero.estateagent.com.nero.service;

import android.os.Bundle;
import android.os.Message;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by Nero on 20/03/2017.
 */

public abstract class AbstractService implements Serializable, Runnable {

    private ArrayList<ServiceListener> listeners;
    private boolean error;

    public AbstractService() {
        listeners = new ArrayList<>();
    }

    public void addListener(ServiceListener serviceListener) {
        listeners.add(serviceListener);
    }

    public void removeListener(ServiceListener serviceListener) {
        listeners.remove(serviceListener);
    }

    public boolean hasError() {
        return error;
    }

    public void serviceCallComplete(boolean error) {
        this.error = error;

        Message m = _handler.obtainMessage();
        Bundle b = new Bundle();
        b.putSerializable("service", this);
        m.setData(b);
        _handler.sendMessage(m);
    }

    final android.os.Handler _handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            AbstractService service = (AbstractService) msg.getData().getSerializable("service");

            for (ServiceListener serviceListener : service.listeners) {
                try {
                    serviceListener.serviceComplete(service);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
