/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roulette;

/**
 *
 * @author oscar.garavito
 */
public class RouletteAcumulated {
    
    private final int n;
    
    private final double[] prob;
    
    public RouletteAcumulated(double[] prob) {
        
        this.n = prob.length;
        this.prob = prob;
    }
    
     public int nextRandom() {
        
        double random = Math.random();
        double accum = 0;
        int found = this.n - 1;
        for(int i = 0;i < this.n;i++) {
            accum += this.prob[i];
            if(random < accum) {
                found = i;
                break;
            }
        }
        return found;
    }
    
    public int[] run() {
        
        int[] frecs = new int[this.n];
        for(int i = 0; i < 50; i++) {
            int item = this.nextRandom();
            frecs[item] += 1;
        }
        return frecs;
    }
    
    public double[] runProb() {
        
        double[] frecs = new double[this.n];
        int reps = 50;
        for(int i = 0; i < 50; i++) {
            int item = this.nextRandom();
            frecs[item] += 1;
        }
        for(int i = 0;i < this.n;i++) {
            frecs[i] = frecs[i] / ((double) reps);
        }
        return frecs;
    }
    
    public static void main(String ... args) {
        
        Roulette roulette = new Roulette(new double[]{0.5, 0.3, 0.2});
        double[] totalFrecs = new double[3];
        int numberOfRuns = 100000;
        for(int i = 0;i < numberOfRuns;i++) {
            double[] frecs = roulette.runProb();
            for(int j = 0;j<totalFrecs.length;j++) {
                totalFrecs[j] += frecs[j];
            }
            if(((i + 1 < 1000) && (i + 1) % 100 == 0) || 
                    ((i + 1 < 10000) && (i + 1) % 1000 == 0) || 
                    ((i + 1 < 100000) && (i + 1) % 10000 == 0)) {
                print(totalFrecs, i + 1);
            }
        }
        
    }
    
    public static void print(double[] totalFrecs, int numberOfRuns) {
        
        System.out.print("Frecs [" + numberOfRuns + "]: ");
        for(double f : totalFrecs) {
            System.out.print(f / (double) numberOfRuns);
            System.out.print(" ");
        }
        System.out.println();
    }
    
}
