/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mightyteegar.MinecraftBackup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import net.mightyteegar.MinecraftBackup.BackupForm;
import net.mightyteegar.MinecraftBackup.HomeForm;

/**
 *
 * @author dave
 */
public class MinecraftBackup  {
    
    private final String SYSTEM_OS = System.getProperty("os.name");
    private String mcSavePath = new String();

    public String getMcSavePath() {
        return mcSavePath;
    }

    public void setMcSavePath(String mcSavePath) {
        this.mcSavePath = mcSavePath;
    }
    
    public void showBackupForm(MinecraftBackup mb) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BackupForm bf = new BackupForm();
                
                bf.setVisible(true);
            }
        });
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
    
    public void printSysvars() {
        System.out.println(this.SYSTEM_OS);
        System.out.println(this.mcSavePath);
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
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        
        
        MinecraftBackup mb = new MinecraftBackup();
        HomeForm hf = new HomeForm();
        
        hf.setVisible(true);
        
        /**
        
        */
        
        
    }
    
}
