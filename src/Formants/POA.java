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
public class POA {
    private String symbol = "";
    private double f1 = 0;
    private double f2 = 0;
    private double f3 = 0;
    
    public POA(String s, double fm1, double fm2, double fm3){
        symbol = s;
        f1 = fm1;
        f2 = fm2;
        f3 = fm3;
        
    }
    public double getF1(){
        return f1;
    }
    public double getF2(){
        return f2;
    }
    public double getF3(){
        return f3;
    }
}
