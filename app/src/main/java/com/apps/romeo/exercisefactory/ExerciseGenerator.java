/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apps.romeo.exercisefactory;

import java.security.SecureRandom;

/**
 *
 * @author Rustem Azimov
 */
public class ExerciseGenerator {
    private int numLength;
    private final char[] signs = new char[4];
    private SecureRandom rd = new SecureRandom();
    private int genCount;
    public ExerciseGenerator(int numLength) {
        this.numLength = numLength;
        signs[0] = '+';
	signs[1] = '-';
        signs[2] = '*';
	signs[3] = '/';
    }
    private boolean isDiv(int num1, int num2){
		return num1 % num2 == 0;
	}
	private byte rdIndex(){
		return (byte) (this.rd.nextInt(1_000_000) % 4);
	}
	private int rdDiv(int num1) throws Exception{
                this.genCount++;
                if(this.genCount == 10)
                {
                    this.genCount = 0;
                    throw new Exception();
                }
		int num2 = this.rd.nextInt(num1);
		return ( ((num2 != 0) && isDiv(num1, num2)) ? num2 : rdDiv(num1));
	}
	private int rdDec(int num1) throws Exception{
                this.genCount++;
                if(this.genCount == 10)
                {
                    this.genCount = 0;
                    throw new Exception();
                }
		int num2 = this.rd.nextInt(num1);
		return ((num1 >= num2) ? num2 : rdDec(num1));
	}
	public String[] genExercise() throws Exception{
		int num1 = this.rd.nextInt(numLength);
		int num2 = 0;
		byte index = rdIndex();
		num2 = ((index == 0 || index == 2) ? this.rd.nextInt(numLength) : ((index == 3) ? rdDiv(num1) : rdDec(num1)));
		return new String[]{
                            (num1 + ""),
                            (this.signs[index] + ""), 
                            (num2 + "")
                           };
	}
}
