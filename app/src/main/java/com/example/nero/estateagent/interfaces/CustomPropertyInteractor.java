package com.example.nero.estateagent.interfaces;

import com.example.nero.estateagent.Property;

/**
 * Created by Nero on 18/05/2017.
 */

public interface CustomPropertyInteractor {
    void OpenPropertyDescription(Property property);

    void onLongClick(Property property);
}
