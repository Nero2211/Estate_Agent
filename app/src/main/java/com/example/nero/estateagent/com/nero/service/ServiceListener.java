package com.example.nero.estateagent.com.nero.service;

import org.json.JSONException;

/**
 * Created by Nero on 20/03/2017.
 */

public interface ServiceListener {

    public void serviceComplete(AbstractService abstractService) throws JSONException;
}
