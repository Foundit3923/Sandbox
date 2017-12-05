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
public class Formant {
    boolean isFormant = false;
    double freq = 0;
    Determine d = new Determine(0,0,0);
    
    public Formant(double i){
        freq = i;
        isFormant = isFormant(freq);
    }
    
    private boolean isFormant(double i){
        boolean result = false;
        if(isFormant1()){
            result = true;
        }else if(isFormant2()){
            result = true;
        }else if(isFormant3()){
            result = true;
        }
        return result;
    }
    
    public double determineFormant(){
        double result = 0;
        return result;
    }
    
    public boolean isFormant1(){
        boolean result = false;
        for(int k : d.getFirst()){
            if(freq <= (k + 150) && freq >= (k - 150)){
                result = true;
            }
        }
            
        return result;
    }
    
    public boolean isFormant2(){
        boolean result = false;
        for(int k : d.getSecond()){
            if(freq <= (k + 150) && freq >= (k - 150)){
                result = true;
            }
        }
        return result;
    }
    
    public boolean isFormant3(){
        boolean result = false;
        for(int k : d.getThird()){
            if(freq <= (k + 150) && freq >= (k - 150)){
                result = true;
            }
        }
        return result;
    }
    
    
    
}
