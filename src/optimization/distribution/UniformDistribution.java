/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimization.distribution;

/**
 *
 * @author Estudiante
 */
public class UniformDistribution implements Distribution {

    @Override
    public double nextRandom() {
        
        return Math.random() * 2 - 1;
    }
    
}
