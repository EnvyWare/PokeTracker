package com.envyful.poke.tracker.forge.gui;

import com.envyful.api.gui.Transformer;
import com.envyful.papi.api.util.UtilPlaceholder;

public class PlaceholderAPITransformer implements Transformer {

    @Override
    public String transformName(String name) {
        return UtilPlaceholder.replaceIdentifiers(null, name);
    }
}
