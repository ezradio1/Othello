/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rollthedice;

/**
 *
 * @author Ezra Audivano Dirfa
 */
import java.util.Random;

public class Dice {
    private int result;

    public void rollTheDice() {
        Random tn = new Random();
        this.result = tn.nextInt(6) + 1;
    }

    public int getResult() {
        return this.result;
    }

}

