package com.gdx.main.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;

public class TTFGenerator {

    public static BitmapFont generateBMF(String path) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        try {
            return generator.generateFont(parameter);

        } finally {
            generator.dispose();
        }
    }

    public static BitmapFont generateBMF(String path, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;

        try {
            return generator.generateFont(parameter);

        } finally {
            generator.dispose();
        }
    }

}
