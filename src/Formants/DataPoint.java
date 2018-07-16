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
public class DataPoint {

    double amplitude;
    double frequency;
    double freqMulti = 5.2083333333; //137.5
    double pixelHeight;
    double maxAmp;
    double fill = 0;
    long nanoTime;
    int grayValue;

    double[] amps;

    public DataPoint(/*double ph*/ double f, double ma, long nt, int gv) {
        //pixelHeight = ph;
        maxAmp = ma;
        nanoTime = nt;
        grayValue = gv;
        frequency = /*pixelHeight * freqMulti*/ f;
        //set size of array to amount of colors
        amps = new double[255];
        
        //for loop fills, first position with 20, then the rest in incriments of 78ish
        for (int i = 0; i < amps.length; i++) {
            if (fill < 20000) {
                amps[i] = fill;
                if (fill < 20) {
                    fill = fill + 20;
                } else if (fill >= 20) {
                    fill = fill + 78.431372549;
                } else if (fill > 20000) {
                    break;
                }
            }

        }
        
        //the relative amplitude of a gray value is stored at the corresponding array position
        amplitude = amps[grayValue];
    }
    
   
    public long getNanoTime(){
        return nanoTime;
    }
    public double returnAmp() {
        return amplitude;
    }

    public double returnFreq() {
        return frequency;
    }

}
