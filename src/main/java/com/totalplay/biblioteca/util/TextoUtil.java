package com.totalplay.biblioteca.util;

import java.text.Normalizer;

public class TextoUtil {

    public static String normalizar(String texto) {
        if (texto == null) {
            return null;
        }

        String limpio = texto.trim().replaceAll("\\s+", " ");

        if (limpio.matches(".*\\d.*")) {
            throw new RuntimeException("El texto no debe contener números");
        }

        limpio = Normalizer.normalize(limpio, Normalizer.Form.NFD);
        limpio = limpio.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        limpio = limpio.replace("ñ", "n").replace("Ñ", "N");

        return limpio.toUpperCase();
    }
}