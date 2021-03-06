/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formants;

import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class Analysis {

    double greatest = 0;
    ArrayList<DataPoint> dpa1 = new ArrayList<>();
    ArrayList<DataPoint> dpa2 = new ArrayList<>();
    DataPoint empty;
    Formant f;
    Formant[] fmt1;
    Formant[] fmt2;
    Formant[] fmt3;
    Formant[] f1;
    Formant[] f2;
    Formant[] f3;
    String[] poa;
    int threshold = 3;

    public Analysis(int i, DataPoint dp, int size) {
        store(i, dp, size);
    }

    public void store(int i, DataPoint dp, int size){
        /*If the for loop is still active*/
        if(i <= size) {
            /*If the first arraylist is not full*/
            if (dpa1.size() <= 2048) {
                dpa1.add(i, dp);
            }
            /*If the second arraylist is not full*/
            else if (dpa2.size() <= 2048) {
                dpa2.add(i, dp);
            }
            /*If both are full*/
            else{
                /*Evaluate*/
                fmt1 = analyzeBuffer(dpa1);
                fmt2 = analyzeBuffer(dpa2);
                fmt3 = compareBuffer(fmt1, fmt2);
                returnPOA();
            }
        }
        /*If the for loop is done*/
        else{
            /*evaluate remaining data*/
            fmt1 = analyzeBuffer(dpa1);
            fmt2 = analyzeBuffer(dpa2);
            fmt3 = compareBuffer(fmt1, fmt2);
            returnPOA();
        }
    }

    //public String[] returnPOA() {
    public void returnPOA(){
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        String r = "";
        
        
        ArrayList <String> res = new ArrayList();
        
        String[] result = new String[res.size()];
        
        
       for(Formant i : fmt3){
           if(i.isFormant1()){
               f1[count1] = i;
               count1++;
           }else if( i.isFormant2()){
               f2[count2] = i;
               count2++;
           }else if( i.isFormant3()){
               f3[count3] = i;
               count3++;
           }
       }
       for(int i = 0; i < f1.length; i++){
           for(int k = 0; k < f2.length; k++){
               for(int j = 0; j< f3.length; j++){
                  Determine d = new Determine(f1[i].determineFormant(), f2[k].determineFormant(), f3[j].determineFormant());
                  if(d.ispoa){
                     res.add(count4, d.symbol);
                     count4++;
                  }
               }
           }
       }
       for(int m = 0; m < res.size(); m++){
           System.out.println("POA: " + res.get(m));
       }
    }

    public Formant[] analyzeBuffer(ArrayList<DataPoint> dp) {
        //declare variables
        int count1 = 0;
        int count2 = 0;
        ArrayList<DataPoint> stored = new ArrayList();
        Formant[] buffForm = new Formant[12];
        f = new Formant(greatest);

        //while loop checks if datapoint is above threshhold, finds the largest number, and removes all datapoints within a given formant
        while (!(dp.isEmpty())) {

            //checks if data point is above threshold, if yes:  stores datat point in new arraylist
            for (int i = 0; i < dp.size(); i++) {
                if (dp.get(i).returnFreq() > threshold) {
                    stored.add(count1, dp.get(i));
                    count1++;

                }
                //checks if data point is the largest number
                if (dp.get(i).returnFreq() > greatest) {
                    greatest = dp.get(i).returnFreq();
                }
            }
            //removes all data points within a given formant
            if (f.isFormant) {
                buffForm[count2] = f;
                count2++;
                for (int k = 0; k < stored.size(); k++) {
                    if (stored.get(k).returnFreq() <= (f.determineFormant() + 150) && stored.get(k).returnFreq() >= (f.determineFormant() - 150)) {
                        stored.remove(k);

                    }
                }

            }
        }
        return buffForm;
    }

    private Formant[] compareBuffer(Formant[] fmt1, Formant[] fmt2) {
        Formant[] result = new Formant[fmt1.length + fmt2.length];
        int count = 0;
        for(int i = 0; i < fmt1.length; i++){
            for(int k = 0; k < fmt2.length; i++){
                if(fmt1[i] == fmt2[k]){
                    result[count] = fmt1[i];
                    count++;
                }
            }
        }
        return result;
    }
    

}
