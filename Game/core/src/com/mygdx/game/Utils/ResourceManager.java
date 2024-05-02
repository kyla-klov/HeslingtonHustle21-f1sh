package com.mygdx.game.Utils;

import java.util.ArrayList;
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
}