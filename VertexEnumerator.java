public class VertexEnumerator {
    private static Character prefix;
    private static String currentString;
    private static Character alphabetPosition;

    VertexEnumerator(){
        alphabetPosition = 65;
        prefix = 65;
        currentString = "";
    }

    public String getNextValue(){
        String value;
        if (alphabetPosition < 91){
            value = currentString + alphabetPosition.toString();
            alphabetPosition++;
            return value;
        } else {
            alphabetPosition = 65;
            currentString += prefix;
            prefix++;
            value = currentString + alphabetPosition.toString();
            alphabetPosition++;
            return value;
        }
    }

    public static void Restart() {
        alphabetPosition = 65;
        prefix = 65;
        currentString = "";
    }
}