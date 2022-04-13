package com.baomidou.mybatisplus.generator.config.rules;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库表名和字段名通用转换工具
 *
 * @author xiazijian
 * @since 2022-04-13
 */
public class Inflector {


    private static final List<String[]> singulars;
    private static final List<String[]> plurals;
    private static final List<String[]> irregulars;
    private static final List<String> uncountables;

    static {
        singulars = new ArrayList<>();
        plurals = new ArrayList<>();
        irregulars = new ArrayList<>();

        addPlural("$", "s");
        addPlural("s$", "s");
        addPlural("^(ax|test)is$", "$1es");
        addPlural("(octop|vir)us$", "$1i");
        addPlural("(octop|vir)i$", "$1i");
        addPlural("(alias|status)$", "$1es");
        addPlural("(bu)s$", "$1ses");
        addPlural("(buffal|tomat)o$", "$1oes");
        addPlural("([ti])um$", "$1a");
        addPlural("([ti])a$", "$1a");
        addPlural("sis$", "ses");
        addPlural("(?:([^f])fe|([lr])f)$", "$1$2ves");
        addPlural("(hive)$", "$1s");
        addPlural("([^aeiouy]|qu)y$", "$1ies");
        addPlural("(x|ch|ss|sh)$", "$1es");
        addPlural("(matr|vert|ind)(?:ix|ex)$", "$1ices");
        addPlural("^(m|l)ouse$", "$1ice");
        addPlural("^(m|l)ice$", "$1ice");
        addPlural("^(ox)$", "$1en");
        addPlural("^(oxen)$", "$1");
        addPlural("(quiz)$", "$1zes");

        addSingular("s$", "");
        addSingular("(n)ews$", "$1ews");
        addSingular("([ti])a$", "$1um");
        addSingular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1sis");
        addSingular("(^analy)ses$", "$1sis");
        addSingular("([^f])ves$", "$1fe");
        addSingular("(hive)s$", "$1");
        addSingular("(tive)s$", "$1");
        addSingular("([lr])ves$", "$1f");
        addSingular("([^aeiouy]|qu)ies$", "$1y");
        addSingular("(s)eries$", "$1eries");
        addSingular("(m)ovies$", "$1ovie");
        addSingular("(x|ch|ss|sh)es$", "$1");
        addSingular("([m|l])ice$", "$1ouse");
        addSingular("(bus)es$", "$1");
        addSingular("(o)es$", "$1");
        addSingular("(shoe)s$", "$1");
        addSingular("(cris|ax|test)es$", "$1is");
        addSingular("(octop|vir)i$", "$1us");
        addSingular("(alias|status)es$", "$1");
        addSingular("^(ox)en", "$1");
        addSingular("(vert|ind)ices$", "$1ex");
        addSingular("(matr)ices$", "$1ix");
        addSingular("(quiz)zes$", "$1");
        addSingular("(database)s$", "$1");

        addIrregular("person", "people");
        addIrregular("man", "men");
        addIrregular("child", "children");
        addIrregular("sex", "sexes");
        addIrregular("move", "moves");
        addIrregular("zombie", "zombies");

        uncountables = Arrays.asList("equipment", "information", "rice", "money", "species", "series", "fish", "sheep", "jeans", "police");
    }

    private Inflector() {

    }

    public static void addPlural(String rule, String replacement) {
        plurals.add(0, new String[]{rule, replacement});
    }

    public static void addSingular(String rule, String replacement) {
        singulars.add(0, new String[]{rule, replacement});
    }

    public static void addIrregular(String rule, String replacement) {
        irregulars.add(new String[]{rule, replacement});
    }


    /**
     *  匹配正则的内容并进行替换.
     * @param word
     * @param rule
     * @param replacement
     * @return 替换后的内容，如果匹配失败，则返回 null
     */
    public static String gsub(String word, String rule, String replacement) {
        Pattern pattern = Pattern.compile(rule, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(word);
        return matcher.find() ? matcher.replaceFirst(replacement) : null;
    }

    public static String pluralize(String word) {

        if (uncountables.contains(word)) {
            return word;
        }

        for (String[] irregular : irregulars) {
            if (irregular[0].equalsIgnoreCase(word)) {
                return irregular[1];
            }
        }

        for (String[] pair : plurals) {
            String plural = gsub(word, pair[0], pair[1]);
            if (plural != null) {
                return plural;
            }
        }

        return word;
    }


    public static String singularize(String word) {

        if (uncountables.contains(word)) {
            return word;
        }

        for (String[] irregular : irregulars) {
            if (irregular[1].equalsIgnoreCase(word)) {
                return irregular[0];
            }
        }

        for (String[] pair : singulars) {
            String singular = gsub(word, pair[0], pair[1]);
            if (singular != null) {
                return singular;
            }
        }

        return word;
    }

    /**
     * 将驼峰命名转换为小写下划线的复数名称
     *
     * Example: "GrayDuck" 会被转换为 "gray_ducks".
     *
     * @param camelCase 任意驼峰格式的名称 .
     * @return 复数形式的小写下划线名称.
     */
    public static String tableize(String camelCase) {
        return pluralize(underscore(camelCase));
    }

    public static String recordlize(String tableName){
        return camelize(singularize(tableName));
    }

    /**
     * Converts a CamelCase string to underscores: "AliceInWonderLand" becomes:
     * "alice_in_wonderland"
     *
     * @param camel camel case input
     * @return result converted to underscores.
     */
    public static String underscore(String camel) {

        List<Integer> upper = new ArrayList<>();
        byte[] bytes = camel.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b < 97 || b > 122) {
                upper.add(i);
            }
        }

        StringBuilder b = new StringBuilder(camel);
        for (int i = upper.size() - 1; i >= 0; i--) {
            Integer index = upper.get(i);
            if (index != 0) {
                b.insert(index, "_");
            }
        }

        return b.toString().toLowerCase();
    }


    /**
     * Generates a camel case version of a phrase from underscore.
     *
     * @param underscore underscore version of a word to converted to camel case.
     * @return camel case version of underscore.
     */
    public static String camelize(String underscore) {
        return camelize(underscore, true);
    }


    /**
     * Generates a camel case version of a phrase from underscore.
     *
     * @param underscore underscore version of a word to converted to camel case.
     * @param capitalizeFirstChar set to true if first character needs to be capitalized, false if not.
     * @return camel case version of underscore.
     */
    public static String camelize(String underscore, boolean capitalizeFirstChar) {
        StringBuilder result = new StringBuilder();
        StringTokenizer st = new StringTokenizer(underscore, "_");
        while (st.hasMoreTokens()) {
            result.append(capitalize(st.nextToken()));
        }
        return capitalizeFirstChar ? result.toString() : result.substring(0, 1).toLowerCase() + result.substring(1);
    }


    /**
     * Will camelize the keys of the map and will return a new map with the same values, but CamelCase keys.
     *
     * @param input map whose keys to conver to to CamelCase
     * @param capitalizeFirstChar tru to capitalize a first character.
     * @return new map with the same values, but CamelCase keys
     */
    public static Map<String, Object> camelize(Map<?, Object> input, boolean capitalizeFirstChar) {
        Map<String, Object> retVal = new HashMap<>();
        for (Map.Entry<?, Object> entry : input.entrySet()) {
            String key = camelize(entry.getKey().toString(), capitalizeFirstChar);
            retVal.put(key, entry.getValue());
        }
        return retVal;
    }


    /**
     * Will underscore the keys of the map and will return a new map with the same values, but under_score keys.
     *
     * @param input map whose keys to convert to to under_score
     * @return new map with the same values, but underscore keys
     */

    public static Map<String, Object> underscore(Map<?, Object> input) {
        Map<String, Object> retVal = new HashMap<>();
        for (Map.Entry<?, Object> entry : input.entrySet()) {
            String key = underscore(entry.getKey().toString());
            retVal.put(key, entry.getValue());
        }
        return retVal;
    }

    /**
     * Capitalizes a word  - only a first character is converted to upper case.
     *
     * @param word word/phrase to capitalize.
     * @return same as input argument, but the first character is capitalized.
     */
    public static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
