package localalignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kuba
 */
public class Interface {
    
    /**
     * A Java String representation of the first DNA sequence.
     */
    public String first = null;
    /**
     * A Java String representation of the second DNA sequence.
     */
    public String second = null;
    /**
     * A score matrix used during aligning two sequences: {@link localalignment.Interface#first} and {@link localalignment.Interface#second}.
     */
    public int[][] score;
    
    /**
     * Initializes first and second String objects that represent DNA sequences to align.
     * @param fileName the name of the file in FASTA format containing two sequences to align
     */
    
    public void getPairs(String fileName) throws IOException{
        BufferedReader br = null;
        InputStream in = null;
        InputStreamReader s = null;
        String text = null;
        
        in = new FileInputStream(fileName);
        s = new InputStreamReader(in);
        br = new BufferedReader(s);
        StringBuilder sb = new StringBuilder();
        String line = null;
        
        line = br.readLine();
        
        System.out.printf("FASTA description of the first sequence: %s\n", line);

        while((line = br.readLine()) != null){
            
            if(line.charAt(0) != '>')
                sb = sb.append(line);
            else
                break;
        }

        first = sb.toString();
        
        System.out.printf("first sequence: %s\n", first);
        
        System.out.printf("FASTA description of the second sequence: %s\n", line);
        
        sb = new StringBuilder();
        
        while((line = br.readLine()) != null)
            sb = sb.append(line);

        second = sb.toString();
        
        System.out.printf("second sequence: %s\n", second);
    }
    
    /**
     * Reads the score matrix {@link localalignment.Interface#score} with the values given in the matrix.txt file.
     * @param fileName the name of the file containing score matrix. Its format should be as follows:
     * 1) optional: a few first lines starting with # are comments,
     * 2) first non-comment line consists the alphabet (letters separated by blank spaces)
     * 3) further non-comment lines start with a letter followed by |sigma| integers, describing the line in the score matrix corresponding to the given letter.
     */
    
    public void getMatrix(String fileName) throws IOException{
        
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
        String line = null;
        
        while((line = br.readLine()) != null && line.charAt(0) == '#'); // comment lines
        
        String alphabet = line.replaceAll("\\s", "");  // ereases all whitespaces
        
        int c;
        score = new int[alphabet.length()][alphabet.length()];
        
        for(int i = 0; i < alphabet.length(); i++){
            
            c = br.read();
            if(c != alphabet.charAt(i))
                throw new IOException("Wrong matrix.txt format.\n");    // simple validation of matrix.txt: first letter of this row doesnt correspond to the expected letter
            
            line = br.readLine();
            line = line.substring(1);
            
            String[] weights = line.split("\\s"); // read line and split weights to Strings, erasing whitespaces
            for(int j = 0; j < weights.length; j++){
                
                score[i][j] = Integer.parseInt(weights[j]);
            }
        }
        return;
        
    }
}
