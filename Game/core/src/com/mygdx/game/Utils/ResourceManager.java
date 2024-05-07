package com.mygdx.game.Utils;

import java.util.ArrayList;
import java.util.Objects;

import com.badlogic.gdx.utils.Disposable;

public class ResourceManager {
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    public <T extends Disposable> T addDisposable(T resource) {
        disposables.add(resource);
        return resource;
    }

    public void disposeAll() {
        for (Disposable resource : disposables) {
            resource.dispose();
        }
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceManager)) return false;
        ResourceManager that = (ResourceManager) o;
        return Objects.equals(disposables, that.disposables);
    }*/
}