package com.example.obstaclecourse.Utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class is used to help implement the Observer pattern.
 * It's used to notify the UI when a property of a model changes.
 * Originally, any class which needed to fire a property change event would need to
 * implement the PropertyChangeSupport class and its methods. This class is a wrapper
 * around that, to remove the need to implement the PropertyChangeSupport class in every class which needs to fire events.
 */
public abstract class EventProducer {
    protected final PropertyChangeSupport support;

    public EventProducer(){
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}