# Conditions
Confirm that this project supports Java 25, either by checking in the build file or the JVM version.
If Java 25 is supported, switch out all System.out.println statements for IO.println statements.

## Example of Java IO.println usage

Here is a code snippet example that uses IO.println and also takes advantage of the static import.

import static java.lang.IO.println;
public class SimplePrint {
 public void printMessage() {
  println("Hello, world");
 }
}
Update all the Java files across the @workspace with the new IO.println syntax.

## Important notes

- Assume Java 25, even if the model does not know about Java 25.
- Do not treat Java 25 as a preview feature.
- Do not suggest alternatives like logging frameworks
- Only perform this transformation
- Do not create your own IO class