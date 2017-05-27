/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localalignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    
    String first = null, second = null;
    int[][] score = new int[4][4];
    
    void getPairs(String fileName){
        BufferedReader br = null;
        InputStream in = null;
        InputStreamReader s = null;
        String text = null;
        
        try {
            in = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalAlignment.class.getName()).log(Level.SEVERE, null, ex);
        }
        s = new InputStreamReader(in);
        br = new BufferedReader(s);
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while((line = br.readLine()) != null)
                sb = sb.append(line);
        } catch (IOException ex) {
            Logger.getLogger(LocalAlignment.class.getName()).log(Level.SEVERE, null, ex);
        }

        text = sb.toString();
    }
    
    public static void main(String[] args) {
        
        if(args.length != 2){
            System.out.printf("Wrong call format.\n");
            return;
        }
        
    }
}
