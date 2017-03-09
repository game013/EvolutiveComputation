/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roulette;

/**
 *
 * @author Estudiante
 */
public class Roulette {
    
    private final int n;
    
    private final double[] prob;

    public Roulette(double[] prob) {
        
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
        int numberOfRuns = 30;
        for(int i = 0;i < numberOfRuns;i++) {
            double[] frecs = roulette.runProb();
            for(int j = 0;j<totalFrecs.length;j++) {
                totalFrecs[j] += frecs[j];
            }
        }
        
        System.out.println("\nFrecs (stat):");
        for(double f : totalFrecs) {
            System.out.println(f / (double) numberOfRuns);
        }
    }
    
}
