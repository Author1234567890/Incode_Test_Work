package ru.testwork.incode.filter.impl;

import org.apache.commons.lang3.tuple.Pair;
import ru.testwork.incode.filter.Filter;
import ru.testwork.incode.filter.FilterBean;

import java.util.Map;
import java.util.stream.Collector;

/**
 * Filter that detects Cyrillic and Greek letters in Latin text and convert those letters to Latin
 */
@FilterBean(group = "convert", id = "cyrillicAndGreek")
public class ConvertCyrillicAndGreekFilter implements Filter {

    @Override
    public String apply(String value, String... params) {
        if (value == null) {
            return null;
        }
        return value.codePoints()
            .mapToObj(c -> (char) c)
            .map(this::convertChars)
            .collect(toStringBuilder())
            .toString();
    }

    private Object convertChars(char ch) {
        if (isCyrillic(ch) || isGreek(ch)) {
            return replace(ch);
        } else {
            return ch;
        }
    }

    private boolean isCyrillic(char ch) {
        return Character.UnicodeBlock.of(ch).equals(Character.UnicodeBlock.CYRILLIC);
    }

    private boolean isGreek(char ch) {
        return Character.UnicodeBlock.of(ch).equals(Character.UnicodeBlock.GREEK);
    }

    private CharSequence replace(char ch) {
        return CharConverter.convert(ch);
    }

    private Collector<Object, StringBuilder, StringBuilder> toStringBuilder() {
        return Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    }


    private static class CharConverter {
        private static final Map<Character, String> CHAR_MAP = Map.<Character, String>ofEntries(
            //Cyrillic
            Pair.of('а', "a"),
            Pair.of('б', "b"),
            Pair.of('в', "v"),
            Pair.of('г', "g"),
            Pair.of('д', "d"),
            Pair.of('е', "ie"),
            Pair.of('ё', "io"),
            Pair.of('ж', "zh"),
            Pair.of('з', "z"),
            Pair.of('и', "i"),
            Pair.of('й', "y"),
            Pair.of('к', "k"),
            Pair.of('л', "l"),
            Pair.of('м', "m"),
            Pair.of('н', "n"),
            Pair.of('о', "o"),
            Pair.of('п', "p"),
            Pair.of('р', "r"),
            Pair.of('с', "s"),
            Pair.of('т', "t"),
            Pair.of('у', "u"),
            Pair.of('ф', "f"),
            Pair.of('х', "kh"),
            Pair.of('ц', "ts"),
            Pair.of('ч', "ch"),
            Pair.of('ш', "sh"),
            Pair.of('щ', "sch"),
            Pair.of('ъ', "‘"),
            Pair.of('ы', "y"),
            Pair.of('ь', "‘"),
            Pair.of('э', "e"),
            Pair.of('ю', "iu"),
            Pair.of('я', "ia"),

            //Greek
            Pair.of('α', "a"),
            Pair.of('β', "b"),
            Pair.of('γ', "g"),
            Pair.of('δ', "d"),
            Pair.of('ε', "e"),
            Pair.of('ζ', "z"),
            Pair.of('η', "h"),
            Pair.of('θ', "q"),
            Pair.of('ι', "i"),
            Pair.of('κ', "k"),
            Pair.of('λ', "l"),
            Pair.of('μ', "m"),
            Pair.of('ν', "n"),
            Pair.of('ξ', "x"),
            Pair.of('ο', "o"),
            Pair.of('π', "p"),
            Pair.of('ρ', "r"),
            Pair.of('σ', "s"),
            Pair.of('τ', "t"),
            Pair.of('υ', "y"),
            Pair.of('φ', "f"),
            Pair.of('χ', "c"),
            Pair.of('ψ', "u"),
            Pair.of('ω', "w")
        );

        static CharSequence convert(char ch) {
            var newChars = CHAR_MAP.get(Character.toLowerCase(ch));
            return Character.isLowerCase(ch) ? newChars : newChars.toUpperCase();
        }
    }
}
