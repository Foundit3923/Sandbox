/*
*      _______                       _____   _____ _____  
*     |__   __|                     |  __ \ / ____|  __ \ 
*        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
*        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
*        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
*        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
*                                                         
* -------------------------------------------------------------
*
* TarsosDSP is developed by Joren Six at IPEM, University Ghent
*  
* -------------------------------------------------------------
*
*  Info: http://0110.be/tag/TarsosDSP
*  Github: https://github.com/JorenSix/TarsosDSP
*  Releases: http://0110.be/releases/TarsosDSP/
*  
*  TarsosDSP includes modified source code by various authors,
*  for credits and info, see README.
* 
*/


package Spectrogram;

import Formants.Analysis;
import Formants.DataPoint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

import be.tarsos.dsp.util.PitchConverter;
import be.tarsos.dsp.util.fft.FFT;

public class SpectrogramPanel extends JComponent implements ComponentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3729805747119272534L;
	
	private BufferedImage bufferedImage;
	private Graphics2D bufferedGraphics;
	
	private int position;
	ArrayList<DataPoint> dpa1 = new ArrayList<>();
	ArrayList<DataPoint> dpa2 = new ArrayList<>();
	ArrayList<DataPoint> overflowBuffer = new ArrayList<>();
	boolean isFull = false;
	    
	public SpectrogramPanel(){
		bufferedImage = new BufferedImage(640*4,480*4, BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = bufferedImage.createGraphics();
		this.addComponentListener(this);
	}
	
	 private int frequencyToBin(final double frequency) {
	        final double minFrequency = 50; // Hz
	        final double maxFrequency = 11000; // Hz
	        int bin = 0;
	        final boolean logaritmic = true;
	        if (frequency != 0 && frequency > minFrequency && frequency < maxFrequency) {
	            double binEstimate = 0;
	            if (logaritmic) {
	                final double minCent = PitchConverter.hertzToAbsoluteCent(minFrequency);
	                final double maxCent = PitchConverter.hertzToAbsoluteCent(maxFrequency);
	                final double absCent = PitchConverter.hertzToAbsoluteCent(frequency * 2);
	                binEstimate = (absCent - minCent) / maxCent * getHeight();
	            } else {
	                binEstimate = (frequency - minFrequency) / maxFrequency * getHeight();
	            }
	            if (binEstimate > 700) {
	                System.out.println(binEstimate + "");
	            }
	            bin = getHeight() - 1 - (int) binEstimate;
	        }
                
	        return bin;
	    }
	
	public void paintComponent(final Graphics g) {
        g.drawImage(bufferedImage, 0, 0, null);
    }


	String currentPitch = "";
	
// extract info from drawFFT?
	public Analysis drawFFT(double pitch,float[] amplitudes,FFT fft, Analysis a){
            DataPoint dp;
            
            int gv = 0;
		double maxAmplitude=0;
		//for every pixel calculate an amplitude
		float[] pixeledAmplitudes = new float[getHeight()];
		//iterate the large array and map to pixels
		 for (int i = amplitudes.length/800; i < amplitudes.length; i++) {
             int pixelY = frequencyToBin(i * 44100 / (amplitudes.length * 8));
             pixeledAmplitudes[pixelY] += amplitudes[i];
             maxAmplitude = Math.max(pixeledAmplitudes[pixelY], maxAmplitude);
                     //System.out.println("Max Amplitude: " + maxAmplitude);
         }
		 
		 //draw the pixels 
		 for (int i = 0; i < pixeledAmplitudes.length; i++) {
    		 Color color = Color.black;
                 
             if (maxAmplitude != 0) {
                 
            	  
            	 final int greyValue = (int) (Math.log1p(pixeledAmplitudes[i] / maxAmplitude) / Math.log1p(1.0000001) * 255);
                 //System.out.println("Pixel Height: " + pixeledAmplitudes[i]);
                 //System.out.println("Grey Value: " + greyValue);
             	 color = new Color(greyValue, greyValue, greyValue);
                 gv = greyValue;
             }
             bufferedGraphics.setColor(color);
        	 bufferedGraphics.fillRect(position, i, 3, 1);
                 long printTime = System.nanoTime();
                     //System.out.println("Print time: " + printTime);
                     dp = new DataPoint(pixeledAmplitudes[i], maxAmplitude, printTime, gv);
                     /*Initialize the first time*/
                     //if(i == 0) {
					//	 a = new Analysis(i, dp, pixeledAmplitudes.length);
					// }
					 /*Add more values afterwards*/
					// else{

        /*If the for loop is still active*/
				 if(i <= pixeledAmplitudes.length) {
            /*If the first arraylist is not full*/
					 if (dpa1.size() <= 2048) {
						 //dpa1.add(i, dp);
						 dpa1.add(dp);
					 }
            /*If the second arraylist is not full*/
					 else if (dpa2.size() <= 2048) {
						 //dpa2.add(i, dp);
						 dpa2.add(dp);
					 }
            /*If both are full*/
					 else{
					 	overflowBuffer.add(dp);
					 	a.evaluate(dpa1, dpa2);
					 	dpa1.clear();
					 	dpa1.addAll(overflowBuffer);
					 	dpa1.addAll(dpa2);
					 	dpa2.clear();
				 }
			 }
         }
		if(dpa2.size() != 0){
			a.evaluate(dpa1, dpa2);
			dpa1.clear();
			dpa1.addAll(overflowBuffer);
			dpa1.addAll(dpa2);
			dpa2.clear();
		}



	
		 
		if (pitch != -1) {
              int pitchIndex = frequencyToBin(pitch);
              bufferedGraphics.setColor(Color.RED);
              bufferedGraphics.fillRect(position, pitchIndex, 1, 1);
              currentPitch = new StringBuilder("Current frequency: ").append((int) pitch).append("Hz").toString();
        }
		
		
		
		bufferedGraphics.clearRect(0,0, 190,30);
        bufferedGraphics.setColor(Color.WHITE);
        bufferedGraphics.drawString(currentPitch, 20, 20);
        
        for(int i = 100 ; i < 500; i += 100){
        	int bin = frequencyToBin(i);
			bufferedGraphics.drawLine(0, bin, 5, bin);
		}
		
        for(int i = 500 ; i <= 20000; i += 500){
			int bin = frequencyToBin(i);
			bufferedGraphics.drawLine(0, bin, 5, bin);
		}
        
        for(int i = 100 ; i <= 20000; i*=10){
			int bin = frequencyToBin(i);
			bufferedGraphics.drawString(String.valueOf(i), 10, bin);
		}
        //where is this method from?
		repaint();
		position+=3;
		position = position % getWidth();
		return a;
	}

       

	@Override
	public void componentHidden(ComponentEvent e) {		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {	
		bufferedImage = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = bufferedImage.createGraphics();
		position = 0;
	}

	@Override
	public void componentShown(ComponentEvent e) {		
	}

}
