/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mightyteegar.MinecraftBackup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author dave baker
 */
public final class BackupJob {
    
    private Path savesLocation;
    private String backupFilePath;
    private int fileScheme;
    private String compressMethod;
    private String fileExtension;
    
    public BackupJob(Path savesLocation, String backupFilePath) {
        this.setSavesLocation(savesLocation);
        this.setBackupFilePath(backupFilePath);
        this.setFileScheme(1);
        this.setCompressMethod("zip");
        
    }
    
    public BackupJob(Path savesLocation, String backupFilePath, int fileScheme) {
        this.setSavesLocation(savesLocation);
        this.setBackupFilePath(backupFilePath);
        this.setFileScheme(fileScheme);
        this.setCompressMethod("zip");
    }
    
    public BackupJob(Path savesLocation, String backupFilePath, int fileScheme, String compressMethod) {
        this.setSavesLocation(savesLocation);
        this.setBackupFilePath(backupFilePath);
        this.setFileScheme(fileScheme);
        this.setCompressMethod(compressMethod);
    }
    
    public String getBackupFilePath() {
        return backupFilePath;
    }

    public void setBackupFilePath(String backupFilePath) {
        this.backupFilePath = backupFilePath;
    }    

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    public Path getSavesLocation() {
        return savesLocation;
    }

    public void setSavesLocation(Path savesLocation) {
        this.savesLocation = savesLocation;
    }

    public int getFileScheme() {
        return fileScheme;
    }

    public void setFileScheme(int fileScheme) {
        this.fileScheme = fileScheme;
    }

    public String getCompressMethod() {
        return compressMethod;
    }

    public void setCompressMethod(String compressMethod) {
        this.compressMethod = compressMethod;
    }
    
    public boolean exeBackupSaves() throws FileNotFoundException { 
        System.out.println("ZIPPATH: " + this.getBackupFilePath());
        if (this.getFileScheme() == 1) {
            
            // 1 = single archive backup
            
            switch (this.getCompressMethod().toLowerCase()) {
                case "zip": 
                    this.setFileExtension("zip");
                    break;
                case "gzip":
                    this.setFileExtension("gz");
                    break;
                default:
                    this.setFileExtension("zip");
                    break;
            }
            
        }
        /**
         * check backFileName:
         * if backupScheme = 1
         *      
         *      create a Path from it
         *      delete any existing file with the same name
         *      create a new archive file named backupFilePath+extension
         *      add the saves to the archive file
         *      close the archive file
         *      verify the contents by trying to read the file and contents
         *      
         * 
         * */
        return true;
        
    }
    
}
