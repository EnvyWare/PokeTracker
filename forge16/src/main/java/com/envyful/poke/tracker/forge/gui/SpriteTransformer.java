package com.envyful.poke.tracker.forge.gui;

import com.envyful.api.gui.Transformer;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;

public class SpriteTransformer implements Transformer {

    private final String path;

    public static SpriteTransformer of(Species species) {
        return new SpriteTransformer(species.getDefaultForm().getDefaultGenderProperties().getDefaultPalette().getSprite().toString());
    }

    private SpriteTransformer(String path) {
        this.path = path;
    }

    @Override
    public String transformName(String name) {
        return name.replace("%sprite%", this.path);
    }
}
