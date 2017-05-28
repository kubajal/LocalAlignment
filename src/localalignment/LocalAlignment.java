/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localalignment;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Kuba
 */
public class LocalAlignment {
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        if(args.length != 2){
            System.out.printf("Wrong call format.\n");
            return;
        }
        
        Interface i = new Interface();
        try {
            i.getPairs(args[0]);
            i.getMatrix(args[1]);
        } catch (IOException ex) {
            System.out.printf("Could not read data from the given files.\n");
            Logger.getLogger(LocalAlignment.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        System.out.printf("\nmatrix.txt:\n");
        for(int j = 0; j < 4; j++){
            for(int k = 0; k < 4; k++){
                System.out.printf("%d ", i.score[j][k]);
            }
            System.out.printf("\n");
        }
        
        SmithWaterman solution = new SmithWaterman(i.first, i.second, i.score);
        solution.align();
        solution.backtrack();
    }
}
