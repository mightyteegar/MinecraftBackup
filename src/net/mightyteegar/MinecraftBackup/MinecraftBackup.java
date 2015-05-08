/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Special thanks to the breweires of Asheville for inspiration.  
 */
package net.mightyteegar.MinecraftBackup;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.mightyteegar.MinecraftBackup.BackupForm;
import net.mightyteegar.MinecraftBackup.HomeForm;

/**
 *
 * @author dave
 */
public class MinecraftBackup  {
    
    private final String SYSTEM_OS = System.getProperty("os.name");
    private final String MCBACKUP_VERSION_NUM = "0.3a";
    private final String MCBACKUP_TITLE = "Minecraft Backup v" + MCBACKUP_VERSION_NUM;
    
    private String mcSavePath = new String();
    
    public String getMcSavePath() {
        return mcSavePath;
    }

    public void setMcSavePath(String mcSavePath) {
        this.mcSavePath = mcSavePath;
    }
    
    public void jframeInit(JFrame jf) {
        BufferedImage bi = null;
        try {
            
            URL imgURL = getClass().getResource("/net/mightyteegar/MinecraftBackup/images/mc_backup_icon_master_H32.png");
            bi = ImageIO.read(imgURL);
        }
        
        catch (FileNotFoundException fe) {
            System.out.println("Icon file not found");
        }
        catch (IOException e) {
            // exceptions
        }
        
        jf.setIconImage(bi);
        jf.setTitle(this.MCBACKUP_TITLE);
    }
    
    public MinecraftBackup() {
        if (SYSTEM_OS.contains("Windows 7")) {
            this.setMcSavePath(System.getenv("APPDATA") + "\\.minecraft\\saves");
        }
        else if (SYSTEM_OS.contains("Linux")) {
            this.setMcSavePath(System.getenv("HOME") + "/.minecraft/saves");
        }
        else if (SYSTEM_OS.contains("Mac")) {
            this.setMcSavePath(System.getenv("HOME") + "/.minecraft/saves");
        }
        else {
            System.out.println("You're running an unsupported OS");
        }
    }
    
    
    public ArrayList<String> readSavePath() {
        ArrayList<String> saves = new ArrayList();
        try {
            Path p = FileSystems.getDefault().getPath(this.mcSavePath);
            if (Files.notExists(p)) {
                throw new FileNotFoundException();
            }
            else {
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(p)) {
                    
                    for (Path f: ds) {
                        if (Files.isDirectory(f)) {
                            saves.add(f.getFileName().toString());
                        }
                        //
                    }
                }
                catch (IOException e) {
                    // do some catching
                }
            }
            
        }
        catch (FileNotFoundException e){
            // do some catching
        }
        
        return saves;
    }
    
    public void textFieldHandleError(JTextField txf, JLabel lbl, String lblError) {
        txf.setBackground(Color.pink);
        lbl.setForeground(Color.red);
        lbl.setText(lblError);
        System.out.println(txf.getText() + ": " + lblError);
    }
    
    public boolean inspectMcSavePath(Path p) {
        return true;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HomeForm hf = new HomeForm();
                hf.setVisible(true);
            }
          });
  
        
       
        
        /**
        
        */
        
        
    }
    
}
