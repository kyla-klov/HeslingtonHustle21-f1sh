package com.mygdx.game.Utils;

import java.util.ArrayList;
//import java.util.Objects;

import com.badlogic.gdx.utils.Disposable;

/**
 * The ResourceManager class is responsible for managing disposable resources.
 * It provides methods to add resources to be managed and to dispose of all managed resources.
 */
public class ResourceManager {
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    /**
     * Adds a disposable resource to the list of managed resources.
     *
     * @param resource the resource to be managed
     * @param <T>      the type of the resource, which must implement the Disposable interface
     * @return the added resource
     */
    public <T extends Disposable> T addDisposable(T resource) {
        disposables.add(resource);
        return resource;
    }

    /**
     * Disposes of all managed resources.
     * This method should be called to release resources when they are no longer needed.
     */
    public void disposeAll() {
        for (Disposable resource : disposables) {
            resource.dispose();
        }
    }

    /**
     * Returns the list of managed disposable resources.
     *
     * @return the list of managed disposable resources
     */
    public ArrayList<Disposable> getDisposables() {
        return disposables;
    }

}