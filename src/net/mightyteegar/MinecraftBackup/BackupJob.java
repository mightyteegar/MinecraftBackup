/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mightyteegar.MinecraftBackup;

import java.nio.file.Path;

/**
 *
 * @author dave baker
 */
public class BackupJob {
    
    Path savesLocation;
    String backupFileName;
    int fileScheme;
    String compressMethod;
    
    public BackupJob(String savesLocation, String backupFileName, int fileScheme, String compressMethod) {
        
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
    
    public void exeBackupSaves() {
        
        if (this.fileScheme == 1) {
            // 1 = single archive backup
            
        }
        /**
         * check backFileName:
         * if backupScheme = 0
         *      add file extension to backupFileName
         *      create a Path from it
         *      delete any existing file with the same name
         *      create a new archive file named backupFileName+extension
         *      add the saves to the archive file
         *      close the archive file
         *      verify the contents by trying to read the file and contents
         *      
         * 
         * */
        
        
    }
}
