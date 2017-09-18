/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formants;

/**
 *
 * @author Michael
 */
public class Determine {

    double f1 = 0;
    double f2 = 0;
    double f3 = 0;
    String symbol = "";
    boolean ispoa = false;

    private POA[] poa = new POA[8];
    private int[] first = new int[8];
    private int[] second = new int[8];
    private int[] third = new int[8];
    private int[] subtract = new int[8];
    private String[] ipa = new String[8];

    private POA poaOb;

    public Determine(double fm1, double fm2, double fm3) {
        f1 = fm1;
        f2 = fm2;
        f3 = fm3;
        fillIPA();
        fillFirst();
        fillSecond();
        fillThird();
        fillPOA();
        fillSubtract();
        symbol = getPOA(f1, f2, f3);
        ispoa = isPOA(f1, f2, f3);
    }

    

    public int[] getFirst(){
        return first;
    }
    public int[] getSecond(){
        return second;
    }
    public int[] getThird(){
        return third;
    }
    private void fillPOA() {
        for (int i = 0; i < poa.length; i++) {
            poaOb = new POA(ipa[i], first[i], second[i], third[i]);
            poa[i] = poaOb;
        }
    }

    private void fillIPA() {

        for (int i = 0; i < ipa.length; i++) {
            if (i == 0) {
                ipa[i] = "i";
            }
            if (i == 1) {
                ipa[i] = "ɪ";
            }
            if (i == 2) {
                ipa[i] = "ɛ";
            }
            if (i == 3) {
                ipa[i] = "æ";
            }
            if (i == 4) {
                ipa[i] = "a";
            }
            if (i == 5) {
                ipa[i] = "ɔ";
            }
            if (i == 6) {
                ipa[i] = "ʊ";
            }
            if (i == 7) {
                ipa[i] = "u";
            }
        }
    }

    private void fillFirst() {

        for (int i = 0; i < first.length; i++) {
            if (i == 0) {
                first[i] = 280;
            }
            if (i == 1) {
                first[i] = 400;
            }
            if (i == 2) {
                first[i] = 550;
            }
            if (i == 3) {
                first[i] = 690;
            }
            if (i == 4) {
                first[i] = 710;
            }
            if (i == 5) {
                first[i] = 590;
            }
            if (i == 6) {
                first[i] = 450;
            }
            if (i == 7) {
                first[i] = 310;
            }
        }
    }

    private void fillSecond() {
        for (int i = 0; i < second.length; i++) {
            if (i == 0) {
                second[i] = 2250;
            }
            if (i == 1) {
                second[i] = 1920;
            }
            if (i == 2) {
                second[i] = 1770;
            }
            if (i == 3) {
                second[i] = 1660;
            }
            if (i == 4) {
                second[i] = 1100;
            }
            if (i == 5) {
                second[i] = 880;
            }
            if (i == 6) {
                second[i] = 1030;
            }
            if (i == 7) {
                second[i] = 870;
            }
        }
    }

    private void fillThird() {
        for (int i = 0; i < third.length; i++) {
            if (i == 0) {
                third[i] = 2890;
            }
            if (i == 1) {
                third[i] = 2560;
            }
            if (i == 2) {
                third[i] = 2490;
            }
            if (i == 3) {
                third[i] = 2490;
            }
            if (i == 4) {
                third[i] = 2540;
            }
            if (i == 5) {
                third[i] = 2540;
            }
            if (i == 6) {
                third[i] = 2380;
            }
            if (i == 7) {
                third[i] = 2250;
            }
        }
    }

    private String getPOA(double f1, double f2, double f3) {
        String result = "";
        for (int i = 0; i < subtract.length; i++) {
            if ((f2 - f1) == subtract[i]) {
                result = ipa[i];
            }
        }
        return result;
    }
    
    private boolean isPOA(double f1, double f2, double f3) {
        boolean result = false;
        for (int i = 0; i < subtract.length; i++) {
            if ((f2 - f1) == subtract[i]) {
                result = true;
            }
        }
        return result;
    }

    private void fillSubtract() {
        for (int i = 0; i < subtract.length; i++) {
            if (i == 0) {
                subtract[i] = 1970;
            }
            if (i == 1) {
                subtract[i] = 1480;
            }
            if (i == 2) {
                subtract[i] = 1220;
            }
            if (i == 3) {
                subtract[i] = 970;
            }
            if (i == 4) {
                subtract[i] = 390;
            }
            if (i == 5) {
                subtract[i] = 290;
            }
            if (i == 6) {
                subtract[i] = 580;
            }
            if (i == 7) {
                subtract[i] = 560;
            }
        }
    }

}
