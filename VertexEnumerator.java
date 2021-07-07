public class VertexEnumerator {
    private Character prefix;
    private String currentString;
    private Character alphabetPosition;

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

}
