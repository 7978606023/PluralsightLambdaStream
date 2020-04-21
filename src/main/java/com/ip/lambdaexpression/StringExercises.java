package com.ip.lambdaexpression;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author PRATAP
 *
 */
final class StringExercises {
/**
 * This prevents the default parameter-less constructor
 * from being used elsewhere in your code.
 */
private StringExercises() {
throw new AssertionError("Instantiating utility class.");
}


/**
* @param string string value
* @return values value
*/
public static int countLowercaseLetters(final String string) {
//int a = 1;
return (int) string.chars().filter(Character::isLowerCase).count();
//string.chars().filter(Character :: isLo)
}

/**
* @param strings nullstring
* @return values nullvalue
*/
public static Optional<String> mostLowercaseString(final List<String> strings) {
return strings.stream()
.max(Comparator.comparing(StringExercises::countLowercaseLetters));
}
}
