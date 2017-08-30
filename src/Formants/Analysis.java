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
    ArrayList<DataPoint> dpa1 = new ArrayList();
    ArrayList<DataPoint> dpa2 = new ArrayList();
    ArrayList<Formant> f1 = new ArrayList();
    ArrayList<Formant> f2 = new ArrayList();
    ArrayList<Formant> f3 = new ArrayList();
    ArrayList<Formant> fmt1 = new ArrayList();
    ArrayList<Formant> fmt2 = new ArrayList();
    ArrayList<Formant> fmt3 = new ArrayList();
    DataPoint empty;
    Formant f;
    

    String[] poa;
    int threshold = 3;
    

    //dpa1 and dpa2 fill as the 'for loop' in SpectrogramPanel itterates
    public Analysis(ArrayList<DataPoint> dp) {
        
        if (!(dpa1.size() == 2048)) {
            dpa1 = dp;
        } else {
            dpa2 = dp;
        }

//        if (!(dpa1[(dpa1.length - 1)] == null)) {
//
//            if (dpa1[(dpa1.length - 1)] == null && !(i == (dpa1.length - 1))) {
//                dpa1[i] = dp;
//            } else if (i == (dpa1.length - 1)) {
//                dpa1[i] = dp;
//            } else if (!(dpa1[0] == null)) {
//                dpa2[i] = dp;
//            }
//        } else {
//            dpa2[i] = dp;
//        }
//        fmt1 = analyzeBuffer(dpa1);
//        fmt2 = analyzeBuffer(dpa2);
//        fmt3 = compareBuffer(fmt1, fmt2);
//        poa = returnPOA();
//        dpa1.clear();
//        dpa1 = dpa2;
//        dpa2.clear();
    }

//    public void runAnalyzeBufferDpa1(){
//           fmt1 = analyzeBuffer(dpa1);
//    }
//    public void runAnalyzeBufferDpa2(){
//        fmt2 = analyzeBuffer(dpa2);
//    }
//    public void runCompareBuffer(){
//        fmt3 = compareBuffer(fmt1,fmt2);
//        poa = returnPOA();
//        dpa1 = dpa2;
//        dpa2.clear();
//    }
// analyze for the point of articulation
    public ArrayList<String> returnPOA() {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        String r = "";

        ArrayList<String> res = new ArrayList();

        ArrayList<String> result = new ArrayList() ;

        // for each element of fmt3 determine if it exists within the range of a formant
        for (Formant i : fmt3) {
            if (!(i == null)) {
                if (i.isFormant1()) {
                    f1.add(count1, i);
                    count1++;
                } else if (i.isFormant2()) {
                    f2.add(count2, i);
                    count2++;
                } else if (i.isFormant3()) {
                    f3.add(count3, i);
                    count3++;
                }
            }
        }

        //determine if any combination of formants results in a POA
        do {
            for (int i = 0; i < f1.size(); i++) {
                for (int k = 0; k < f2.size(); k++) {
                    for (int j = 0; j < f3.size(); j++) {
                        Determine d = new Determine(f1.get(i).determineFormant(), f2.get(k).determineFormant(), f3.get(j).determineFormant());
                        if (d.ispoa) {
                            res.add(count4, d.symbol);
                            count4++;
                        }
                    }
                }
            }
            for (int m = 0; m < res.size(); m++) {
                result.add(res.get(m));
            }

        } while (!(f1.isEmpty() || f2.isEmpty() || f3.isEmpty()));
        
        fmt1 = analyzeBuffer(dpa1);
        fmt2 = analyzeBuffer(dpa2);
        fmt3 = compareBuffer(fmt1, fmt2);
        dpa1.clear();
        dpa1 = dpa2;
        dpa2.clear();
        return result;

    }

    public ArrayList<Formant> analyzeBuffer(ArrayList<DataPoint> dp) {
        //declare variables
        int count1 = 0;
        int count2 = 0;
        ArrayList<DataPoint> stored = new ArrayList();
        ArrayList<Formant> result = new ArrayList();
        ArrayList<Formant> buffForm = new ArrayList();
        
        
        

        //do-while loop checks if datapoint is above threshhold, finds the largest number, and removes all datapoints within a given formant
        do {
            //checks if data point is above threshold, if yes:  stores data point in new arraylist
            for (int i = 0; i < dp.size(); i++) {
                if (dp.get(i).returnFreq() > threshold) {
                    stored.add(count1, dp.get(i));
                    count1++;
                    System.out.println("freq: " + dp.get(i).returnFreq());
                }
                //checks if data point is the largest number
                if (dp.get(i).returnFreq() > greatest) {
                    greatest = dp.get(i).returnFreq();
                }
            }
            
                
            
            f = new Formant(greatest);
            System.out.println("Greatest Freq: " + greatest);

            //if the greatest freqeuncy is part of a formant, remove all data points within given formant
            
            if (f.isFormant) {
                buffForm.add(f);
                for (int k = 0; k < stored.size(); k++) {
                    if (stored.get(k).returnFreq() <= (f.determineFormant() + 150) && stored.get(k).returnFreq() >= (f.determineFormant() - 150)) {
                        stored.remove(k);

                    }
                }

            } //if the greatest frequency is not part of a formant, remove all instances of frequency
            else if (!(f.isFormant)) {
                for (int j = 0; j < stored.size(); j++) {
                    if (stored.get(j).returnFreq() == greatest) {
                        stored.remove(j);
                    }
                }
            }
//            System.out.println(stored.size());
            
           
        } while (!(stored.isEmpty()));
         return result;
    }

// Compare the discovered formats from each buffer fill. If they appear across both buffer fills, a sound has been expressed intentionally.
    private ArrayList<Formant> compareBuffer(ArrayList<Formant> fmt1, ArrayList<Formant> fmt2) {
        ArrayList<Formant> result = new ArrayList();
        
        int count = 0;
        for (int i = 0; i < fmt1.size(); i++) {
            
            for (int k = 0; k < fmt2.size(); k++) {
                
                if (fmt1.get(i) == fmt2.get(k)) {
                    if (!(fmt1 == null)) {
                        result.add(fmt1.get(i));

                    }
                }
            }
            count++;
        }
          
        return result;
    }
    
    public ArrayList<DataPoint> getDPA1(){
        return dpa1;
    }
    
    public ArrayList<DataPoint> getDPA2(){
        return dpa2;
    }

    public boolean isReady(){
        boolean result = false;
        if(!(dpa1.isEmpty() && dpa2.isEmpty())){
            result = true;
        }
        return result;
    }
}
