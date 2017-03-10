/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimization.distribution;

import java.util.Random;

/**
 *
 * @author Estudiante
 */
public class NormalDistribution implements Distribution {
    
    private final double sigma;
    
    private final Random random;
    
    public NormalDistribution(double sigma) {
        this.sigma = sigma;
        this.random = new Random();
    }

    @Override
    public double nextRandom() {
        
        return random.nextGaussian() * this.sigma;
    }
    
}
