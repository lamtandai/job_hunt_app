package application.project.util.Pluralize;

public class Pluralize {

    public static String pluralize(String word) {
    // 1) If it ends in a vowel + “y”, just add “s” (e.g. “key” → “keys”)
    if (word.matches(".*[aeiou]y$")) {
        return word + "s";
    }
    // 2) If it ends in consonant + “y”, drop “y” add “ies” (“company” → “companies”)
    if (word.endsWith("y")) {
        return word.substring(0, word.length() - 1) + "ies";
    }
    // 3) If it ends in s, x, z, “ch” or “sh”, add “es” (“class” → “classes”)
    if (word.matches(".*(s|x|z|ch|sh)$")) {
        return word + "es";
    }
    // 4) Otherwise just add “s”
    return word + "s";
    }
    
}
