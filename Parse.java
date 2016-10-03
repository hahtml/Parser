
import java.io.InputStream;
import java.util.*;

/**
 * Created by tangmaolei on 9/26/16.
 */


//all terminal symbols: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, +, -, (, ), $, ++, --}
enum TokenType {
    DIGIT,
    PLUS,
    MINUS,
    LEFTPAREN,
    RIGHTPAREN,
    REFERENCE,
    INCREMENT,
    DECREMENT
}

class Token {
    private TokenType mType;
    private int mValue;
    private int mLineNum;

    public Token(TokenType type, int line) {
        this.mType = type;
        this.mLineNum = line;
    }

    public Token(TokenType type, int value, int line) {
        this.mType = type;
        this.mValue = value;
        this.mLineNum = line;
    }

    public int getLineNum() { return mLineNum; }

    public TokenType getType() {
        return mType;
    }

    public int getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return mType.name();
    }
}


public class Parse {

//    convert a file stream into a string
    public static String convertStreamToString(InputStream is) {
        Scanner myScanner = new Scanner(is);
        myScanner.useDelimiter("\\A");
        if (myScanner.hasNext()) {
            return myScanner.next();
        }
        else
            return "";
    }

    public static void printErrorInfo(int lineNum) {
        System.out.printf("Parse error in line <%d>\n", lineNum);
    }

    //all terminal symbols: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, +, -, (, ), $, ++, --}
    public static List<Token> scanner(String input) {
        List<Token> tokenList = new ArrayList<Token>();
        int index = 0;
        int range = input.length();
        int line = 1;
        while (index < range) {
            char pattern = input.charAt(index);
            switch (pattern) {
                case '+':
//                    INCREMENT
                    if (input.charAt(index+1) == '+') {
                        tokenList.add(new Token(TokenType.INCREMENT, line));
                        index = index + 2;
                    }
//                    PLUS
                    else {
                        tokenList.add(new Token(TokenType.PLUS, line));
                        index++;
                    }
                    break;
                case '-':
//                    DECREMENT
                    if (input.charAt(index+1) == '-') {
                        tokenList.add(new Token(TokenType.DECREMENT, line));
                        index = index + 2;
                    }
//                    MINUS
                    else {
                        tokenList.add(new Token(TokenType.MINUS, line));
                        index++;
                    }
                    break;
//                LEFT-PARENTHESES
                case '(':
                    tokenList.add(new Token(TokenType.LEFTPAREN, line));
                    index++;
                    break;
//                RIGHT-PARENTHESES
                case ')':
                    tokenList.add(new Token(TokenType.RIGHTPAREN, line));
                    index++;
                    break;
//                DIGIT
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    tokenList.add(new Token(TokenType.DIGIT, Character.getNumericValue(pattern), line));
                    index++;
                    break;
//                REFERENCE SIGN
                case '$':
                    tokenList.add(new Token(TokenType.REFERENCE, line));
                    index++;
                    break;
//                COMMENTS
                case '#':
                    while (pattern != '\n') {
                        index++;
                        pattern = input.charAt(index);
                    }
                    break;
                case '\t':
                case ' ':
                    index++;
                    break;
                case '\r':
                case '\n':
                    index++;
                    line++;
                    break;
                default:
                    printErrorInfo(line);
//                    FOR TEST
                    // for ( Token token: tokenList.toArray(new Token[0])) {
                    //     System.out.println(token);
                    // }
                    System.exit(1);
            }
        }
//        FOR TEST
//        for ( hw1.Token token: tokenList.toArray(new hw1.Token[0])) {
//            System.out.println(token);
//        }
        return tokenList;
    }


    public static String paser(Queue<Token> tokenQueue) {
        String result = goal(tokenQueue);
        System.out.print(result);
        System.out.println("\nExpression parsed successfully");
        return result;
    }


    public static void eatAndCheck(Queue<Token> tokenQueue, TokenType type, int line) {
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.poll();
            if (currentToken.getType() != type) {
                printErrorInfo(line);
                System.exit(1);
            }
        }
        else {
            printErrorInfo(line);
            System.exit(1);
        }
    }

    public static String goal(Queue<Token> tokenQueue){
        String result="";
        if (tokenQueue.peek()!=null) {
            int currentLineNum = tokenQueue.peek().getLineNum();
            result = expr1(tokenQueue, currentLineNum);
            if (tokenQueue.peek()!=null) {
                currentLineNum = tokenQueue.peek().getLineNum();
                printErrorInfo(currentLineNum);
                System.exit(1);
            }
            else {
                result = " " + result;
            }
        }
        else {
            printErrorInfo(1);
            System.exit(1);
        }
        return result.replaceAll("( )+", " ");
    }

    public static String expr1(Queue<Token> tokenQueue, int line) {
        String result="";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine =currentToken.getLineNum();
            if (currentTokenType == TokenType.DIGIT || currentTokenType == TokenType.LEFTPAREN
                    ||currentTokenType == TokenType.INCREMENT || currentTokenType == TokenType.DECREMENT
                    || currentTokenType == TokenType.REFERENCE) {
                String tmp = expr2(tokenQueue, currentLine);
                result = expr1prime(tokenQueue, tmp, currentLine);
            }
            else {
                printErrorInfo(line);
                System.exit(1);
            }
        }
        else {
            printErrorInfo(line);
            System.exit(1);
        }
        return result;
    }

    public static String expr1prime(Queue<Token> tokenQueue, String previousResult, int line) {
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.DIGIT || currentTokenType == TokenType.LEFTPAREN
                    || currentTokenType == TokenType.REFERENCE) {
                String tmp1 = expr2primeprime(tokenQueue, currentLine);
                String tmp2 = "";
                if (previousResult != "")
                    tmp2 = previousResult + " " + tmp1 + " " + "_";
                else
                    tmp2 = previousResult + tmp1 + " " + "_";
                return expr1prime(tokenQueue, tmp2, currentLine);
            }
            else {
                return previousResult;
            }
        }
        else {
            return previousResult;
        }
    }

    public static String expr2(Queue<Token> tokenQueue, int line) {
        String result = "";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.DIGIT || currentTokenType == TokenType.LEFTPAREN
                    || currentTokenType == TokenType.REFERENCE || currentTokenType == TokenType.INCREMENT
                    || currentTokenType == TokenType.DECREMENT) {
                result = expr3primeprime(tokenQueue, currentLine);
                if (!tokenQueue.isEmpty()) {
                    currentLine = tokenQueue.peek().getLineNum();
                    if (tokenQueue.peek().getType() == TokenType.PLUS || tokenQueue.peek().getType() == TokenType.MINUS) {
                        String tmp1 =expr2prime(tokenQueue, result, currentLine);
                        result = tmp1;
                    }
                }
            }
            else {
                printErrorInfo(currentLine);
            }
        }
        else
        {
            printErrorInfo(line);
            System.exit(1);
        }
        return result;
    }

    public static String expr2prime(Queue<Token> tokenQueue, String previousResult, int line) {
//        String result = "";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.PLUS) {
                eatAndCheck(tokenQueue, TokenType.PLUS, currentLine);

                //String tmp1 = expr3(tokenQueue, currentLine);
                String tmp1 = expr3primeprime(tokenQueue, currentLine);

                if (tokenQueue.isEmpty()){
                    if (previousResult != "")
                        return previousResult + " " + tmp1 + " " + "+";
                    else
                        return previousResult + tmp1 + " " + "+";
                }
                else if (tokenQueue.peek().getType()==TokenType.PLUS || tokenQueue.peek().getType()==TokenType.MINUS) {
                    currentLine = tokenQueue.peek().getLineNum();
                    String tmp2 = "";
                    if (previousResult != "")
                        tmp2 = previousResult + " " + tmp1 + " " + "+";
                    else
                        tmp2 = previousResult + tmp1 + " " + "+";
                    return expr2prime(tokenQueue, tmp2, currentLine);
                }
                if (previousResult != "")
                    return previousResult + " " + tmp1 + " " + "+";
                else
                    return previousResult + tmp1 + " " + "+";
            }
            else if (currentTokenType == TokenType.MINUS) {
                eatAndCheck(tokenQueue, TokenType.MINUS, currentLine);

                // String tmp1 = expr3prime(tokenQueue, currentLine);
                String tmp1 = expr3primeprime(tokenQueue, currentLine);

                if (tokenQueue.isEmpty()){
                    if (previousResult != "")
                        return previousResult + " " + tmp1 + " " + "-";
                    else
                        return previousResult + tmp1 + " " + "-";
                }
                else if (tokenQueue.peek().getType()==TokenType.PLUS || tokenQueue.peek().getType()==TokenType.MINUS) {
                    currentLine = tokenQueue.peek().getLineNum();
                    String tmp2 = "";
                    if (previousResult != "")
                        tmp2 = previousResult + " " + tmp1 + " " + "-";
                    else
                        tmp2 = previousResult + tmp1 + " " + "-";
                    return expr2prime(tokenQueue, tmp2, currentLine);
                }
                if (previousResult!="")
                    return previousResult + " " + tmp1 + " " + "-";
                else
                    return previousResult + tmp1 + " " + "-";
            }
            else
                return previousResult;
        }
        return previousResult;
    }

    public static String expr2primeprime(Queue<Token> tokenQueue, int line) {
        String result ="";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.DIGIT || currentTokenType == TokenType.LEFTPAREN
                    || currentTokenType == TokenType.REFERENCE) {
                result = expr4(tokenQueue, currentLine);
                if (!tokenQueue.isEmpty()) {
                    currentLine = currentToken.getLineNum();
                    if (tokenQueue.peek().getType() == TokenType.PLUS || tokenQueue.peek().getType() == TokenType.MINUS) {
                        String tmp1 =expr2prime(tokenQueue, result, currentLine);
                        result = tmp1;
                    }
                }
            }
            else {
                printErrorInfo(currentLine);
                System.exit(1);
            }
        }
        else {
            printErrorInfo(line);
            System.exit(1);
        }
        return result;
    }

    // public static String expr3(Queue<Token> tokenQueue, int line) {
    //     String result = "";
    //     if (tokenQueue.peek()!=null) {
    //         Token currentToken = tokenQueue.peek();
    //         TokenType currentTokenType = currentToken.getType();
    //         int currentLine = currentToken.getLineNum();
    //         if (currentTokenType == TokenType.DECREMENT) {
    //             eatAndCheck(tokenQueue, TokenType.DECREMENT, currentLine);
    //             result = expr3primeprime(tokenQueue, currentLine) + " " + "--_";
    //         }
    //         else if (currentTokenType == TokenType.INCREMENT) {
    //             printErrorInfo(currentLine);
    //             System.exit(1);
    //         }
    //         else
    //             result = expr4(tokenQueue, currentLine);
    //     }
    //     else {
    //         printErrorInfo(line);
    //         System.exit(1);
    //     }
    //     return result;
    // }


    // public static String expr3prime(Queue<Token> tokenQueue, int line) {
    //     String result = "";
    //     if (tokenQueue.peek()!=null) {
    //         Token currentToken = tokenQueue.peek();
    //         TokenType currentTokenType = currentToken.getType();
    //         int currentLine = currentToken.getLineNum();
    //         if (currentTokenType == TokenType.INCREMENT) {
    //             eatAndCheck(tokenQueue, TokenType.INCREMENT, currentLine);
    //             result = expr3primeprime(tokenQueue, currentLine) + " " + "++_";
    //         }
    //         else if (currentTokenType == TokenType.DECREMENT) {
    //             printErrorInfo(currentLine);
    //             System.exit(1);
    //         }
    //         else
    //             result = expr4(tokenQueue, currentLine);
    //     }
    //     else {
    //         printErrorInfo(line);
    //         System.exit(1);
    //     }
    //     return result;
    // }

    public static String expr3primeprime(Queue<Token> tokenQueue, int line) {
        String result = "";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.INCREMENT) {
                eatAndCheck(tokenQueue, TokenType.INCREMENT, currentLine);
                result = expr3primeprime(tokenQueue, currentLine) + " " + "++_";
            }
            else if (currentTokenType == TokenType.DECREMENT) {
                eatAndCheck(tokenQueue, TokenType.DECREMENT, currentLine);
                result = expr3primeprime(tokenQueue, currentLine) + " " + "--_";
            }
            else
                result = expr4(tokenQueue, currentLine);
        }
        else {
            printErrorInfo(line);
            System.exit(1);
        }
        return result;
    }

    public static String expr4(Queue<Token> tokenQueue, int line) {
        String result = "";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.DIGIT || currentTokenType ==TokenType.LEFTPAREN
                    || currentTokenType == TokenType.REFERENCE) {
                result = expr5(tokenQueue, currentLine) + " " + expr4prime(tokenQueue);
            }
            else {
                printErrorInfo(currentLine);
                System.exit(1);
            }
        }
        else {
            printErrorInfo(line);
            System.exit(1);
        }
        return result;
    }

    public static String expr4prime(Queue<Token> tokenQueue) {
        String result = "";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.INCREMENT) {
                tokenQueue.poll();
                String tmp = expr4prime(tokenQueue);
                if (tmp != "")
                    result = "_++" + " " + tmp;
                else
                    result = "_++";
            }
            else if (currentTokenType == TokenType.DECREMENT) {
                tokenQueue.poll();
                String tmp = expr4prime(tokenQueue);
                if (tmp != "")
                    result = "_--" + " " + tmp;
                else
                    result = "_--";
            }
        }
        return result;
    }

    public static String expr5(Queue<Token> tokenQueue, int line) {
        String result = "";
        if (tokenQueue.peek()!=null) {
            Token currentToken = tokenQueue.peek();
            TokenType currentTokenType = currentToken.getType();
            int currentLine = currentToken.getLineNum();
            if (currentTokenType == TokenType.DIGIT) {
                result = String.valueOf(currentToken.getValue());
                tokenQueue.poll();
            }
            else if (currentTokenType == TokenType.REFERENCE) {
                tokenQueue.poll();
                result = expr5(tokenQueue, currentLine) + " " + "$";
            }
            else if (currentTokenType == TokenType.LEFTPAREN) {
                tokenQueue.poll();
//                TODO: UNCOMMENT THIS AFTER FINISH expr1
                result = expr1(tokenQueue, currentLine);
                eatAndCheck(tokenQueue, TokenType.RIGHTPAREN, line);
            }
            else {
                printErrorInfo(currentLine);
                System.exit(1);
            }
        }
        else {
            printErrorInfo(line);
            System.exit(1);
        }
        return result;
    }

    public static void main(String[] args) {

//        convert file stream into a string
        String inputString = convertStreamToString(System.in);

//        Test: input
//        System.out.println(inputString);

        List<Token> list = scanner(inputString);
//        FOR TEST
//        for ( hw1.Token token: list.toArray(new hw1.Token[0])) {
//            System.out.println(token);
//        }
        Queue<Token> queue = new LinkedList<Token>();
        queue.addAll(list);
//        FOR TEST
//        while(!queue.isEmpty()) {
//            System.out.println(queue.poll());
//        }

        paser(queue);
        
    }
}
