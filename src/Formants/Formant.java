/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formants;
import Formants.Determine;
import Formants.Analysis;
import Formants.DataPoint;
import Formants.Formant;
import Formants.POA;
        
/**
 *
 * @author Michael
 */
public class Formant {
    boolean isFormant = false;
    double freq = 0;
    double jk = 0;
    Determine d = new Determine(freq,freq,freq);
  
    
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
                 for(int k : d.first){
//         for(int i = 0; 
//                 i 
//                 < 
//                 d.first.length; 
//                 i ++){
//            int k = d.first[i];
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
