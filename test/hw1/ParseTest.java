package hw1;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import static hw1.Parse.*;
import static org.junit.Assert.*;

/**
 * Created by tangmaolei on 10/1/16.
 */
public class ParseTest {

    /****************************************************
     * expr5 test
     *
     *
    ******************************************************/

//    @Test
//    public void expr5Test() throws Exception {
//        Queue<Token> queue = new LinkedList<Token>();
//        queue.add(new Token(TokenType.REFERENCE, 1));
//        queue.add(new Token(TokenType.REFERENCE, 1));
//        queue.add(new Token(TokenType.DIGIT, 1));
//        System.out.println(expr5(queue, 1));
//    }


    /****************************************************
     * expr4prime test
     *
     *
     ******************************************************/
//    @Test
//    public void expr5Test() throws Exception {
//        Queue<Token> queue = new LinkedList<Token>();
//        queue.add(new Token(TokenType.INCREMENT, 1));
//        queue.add(new Token(TokenType.DECREMENT, 1));
//        queue.add(new Token(TokenType.DECREMENT, 1));
//        System.out.println(expr4prime(queue));
//    }


    /****************************************************
     * expr4 test
     *
     *
     ******************************************************/
//    @Test
//    public void expr5Test() throws Exception {
//        Queue<Token> queue = new LinkedList<Token>();
//        queue.add(new Token(TokenType.REFERENCE, 1));
//        queue.add(new Token(TokenType.DIGIT, 1));
//        queue.add(new Token(TokenType.DECREMENT, 1));
//        queue.add(new Token(TokenType.INCREMENT, 1));
//        System.out.println(expr4(queue, 1));
//    }

    /****************************************************
     * expr3primeprime test
     *
     *
     ******************************************************/
//    @Test
//    public void expr5Test() throws Exception {
//        Queue<Token> queue = new LinkedList<Token>();
//        queue.add(new Token(TokenType.DECREMENT, 1));
//        queue.add(new Token(TokenType.INCREMENT, 1));
//        queue.add(new Token(TokenType.REFERENCE, 1));
//        queue.add(new Token(TokenType.DIGIT, 1));
//        queue.add(new Token(TokenType.DECREMENT, 1));
//        queue.add(new Token(TokenType.INCREMENT, 1));
//        System.out.println(expr3primeprime(queue, 1));
//    }


    /****************************************************
     * expr2 test
     *
     *
     ******************************************************/
//    @Test
//    public void expr5Test() throws Exception {
//        Queue<Token> queue = new LinkedList<Token>();
//        queue.add(new Token(TokenType.DIGIT, 1));
//        queue.add(new Token(TokenType.INCREMENT, 1));
//        queue.add(new Token(TokenType.PLUS, 1));
//        queue.add(new Token(TokenType.DIGIT, 1));
//        queue.add(new Token(TokenType.DECREMENT, 1));
//        queue.add(new Token(TokenType.PLUS, 1));
//        queue.add(new Token(TokenType.DIGIT, 1));
//        System.out.println(expr2primeprime(queue, 1));
//    }


        /****************************************************
         * expr1 test
         *
         *
         ******************************************************/
//        @Test
//        public void expr5Test() throws Exception {
//            Queue<Token> queue = new LinkedList<Token>();
////        queue.add(new Token(TokenType.DECREMENT, 1));
//            queue.add(new Token(TokenType.DIGIT, 1));
//            queue.add(new Token(TokenType.DIGIT, 1));
//            queue.add(new Token(TokenType.DIGIT, 1));
//            queue.add(new Token(TokenType.DIGIT, 1));
////        queue.add(new Token(TokenType.DIGIT, 1));
////        queue.add(new Token(TokenType.DECREMENT, 1));
////        queue.add(new Token(TokenType.INCREMENT, 1));
//            System.out.println(expr1(queue, 1));
//        }


    /****************************************************
     * goal test
     *
     *
     ******************************************************/
    @Test
    public void expr5Test() throws Exception {
        Queue<Token> queue = new LinkedList<Token>();
        queue.add(new Token(TokenType.DIGIT, 1));
//        queue.add(new Token(TokenType.INCREMENT, 1));
        queue.add(new Token(TokenType.PLUS, 1));
        queue.add(new Token(TokenType.DIGIT, 1));
        queue.add(new Token(TokenType.DIGIT, 1));
        queue.add(new Token(TokenType.PLUS, 1));
        queue.add(new Token(TokenType.DIGIT, 1));
        System.out.println(goal(queue));
    }

}