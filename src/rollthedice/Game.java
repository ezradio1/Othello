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
public class Game {
    private Dice fDice = new Dice();
    private Dice sDice = new Dice();

    public void play() {
        this.fDice.rollTheDice();
        this.sDice.rollTheDice();
    }

    public void showResultPerGame() {
        System.out.println("Hasil lemparan dadu pertama : " + this.fDice.getResult());
        System.out.println("Hasil lemparan dadu kedua : " + this.sDice.getResult());
        System.out.println("Total   : " + getTotalValue());
    }

    public int getResultFirstDice() {
        return this.fDice.getResult();
    }

    public int getResultSecondDice() {
        return this.sDice.getResult();
    }

    public int getTotalValue() {
        return this.fDice.getResult() + this.sDice.getResult();
    }

}

