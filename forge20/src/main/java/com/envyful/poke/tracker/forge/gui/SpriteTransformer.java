package com.envyful.poke.tracker.forge.gui;

import com.envyful.api.text.parse.SimplePlaceholder;
import com.envyful.poke.tracker.forge.tracker.data.EntityData;

public class SpriteTransformer implements SimplePlaceholder {

    private final String path;

    public static SpriteTransformer of(EntityData entity) {
        if (entity == null || entity.getSprite() == null) {
            return new SpriteTransformer("pixelmon:species/001_bulbasaur/default/none/sprite.png");
        }

        return new SpriteTransformer(entity.getSprite());
    }

    private SpriteTransformer(String path) {
        this.path = path;
    }

    @Override
    public String replace(String name) {
        return name.replace("%sprite%", this.path);
    }
}
