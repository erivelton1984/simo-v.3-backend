package br.com.ciccr.simo.modules.transparency.util;

import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySearchItem;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;

public final class TransparencySearchMatcher {

    private TransparencySearchMatcher() {
    }

    public static TransparencySearchItem findBestMatch(
            String searchedName,
            List<TransparencySearchItem> items) {

        if (items == null || items.isEmpty()) {
            return null;
        }

        String normalizedSearch =
                normalize(searchedName);

        return items.stream()
                .max(Comparator.comparingInt(item ->
                        similarity(
                                normalizedSearch,
                                normalize(item.name())
                        )))
                .orElse(items.getFirst());
    }

    private static int similarity(
            String a,
            String b) {

        int score = 0;

        if (a.equals(b))
            score += 1000;

        if (b.startsWith(a))
            score += 200;

        if (b.contains(a))
            score += 100;

        for (String word : a.split(" ")) {

            if (b.contains(word))
                score += 10;
        }

        return score;
    }

    private static String normalize(String text) {

        return Normalizer.normalize(
                        text,
                        Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("\\s+", " ")
                .trim()
                .toUpperCase();
    }
}