/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mightyteegar.MinecraftBackup;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

        

/**
 *
 * @author dave
 */
public class BackupForm extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
    List<JCheckBox> listOfSaves = new ArrayList<JCheckBox>();
    
    public BackupForm() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BackupForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BackupForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BackupForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BackupForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initComponents();
        jprgBackupProgress.setVisible(false);
        
        MinecraftBackup mb = new MinecraftBackup();
        mb.jframeInit(this);
        setTxfSavesPathText(mb.getMcSavePath());
        setSavePathInfo(1);
        setSaveCheckboxes(mb.readSavePath());
        
        pack();
        setLocationRelativeTo(null);  // *** this will center your app ***
        
        setVisible(true);
        
        
    }

    public void setTxfSavesPathText(String text) {
        this.txfSavesPath.setText(text);
    }
    
    public void setSavePathInfo(int i) {
        String savePathInfo = new String();
        switch (i) {
            case 1: savePathInfo = "There appear to be Minecraft save files at the location "
                    + "shown above.  Click the \"Change Savepath\" button to "
                    + "select a different location.";
                    break;
            case 2: savePathInfo = "Could not find a Minecraft backup folder "
                    + "in the default location.  Please tell me where your"
                    + "Minecraft saves folder is.";
                    break;
            case 3: savePathInfo = "Saves path cannot be blank";
                    this.lblSavePathInfo.setForeground(Color.red);
                    break;
            case 4: savePathInfo = "Path does not exist, choose a different path";
                    this.lblSavePathInfo.setForeground(Color.red);
                    break;
            default: savePathInfo = "Something went wrong";
                    
        }
        this.lblSavePathInfo.setText(savePathInfo);
    }
    
    public void setSaveCheckboxes(ArrayList<String> saves) {
       int i = 0; 
       List<JCheckBox> listOfSaves = new ArrayList<JCheckBox>();
       for (String save : saves) {
            JCheckBox jcbx = new JCheckBox(save);
            this.jpnlSubSelectSavefiles.add(jcbx);
            this.listOfSaves.add(jcbx);
            jcbx.setSelected(true);
            jcbx.setVisible(true);
        }
       
    }
    
    class BackupJobQueue extends LinkedBlockingQueue {
        private boolean isRunning = true;
        
        public boolean isRunning() {
            return isRunning;
        }
        
        public void stopRunning() {
            isRunning = false;
        }
        
    }
    
    class BackupJob extends SimpleFileVisitor<Path> implements Runnable {
        
        private Path savesLocation;
        private String backupFilePath;
        private int fileScheme;
        private String compressMethod;
        private String fileExtension;
        public BackupMonitor backupMonitor;
        public BackupJobQueue jobQueue;
        
        public BackupJob(
                Path savesLocation, 
                String backupFilePath,
                BackupMonitor bm) {
                    this.setSavesLocation(savesLocation);
                    this.setBackupFilePath(backupFilePath);
                    this.setFileScheme(1);
                    this.setCompressMethod("zip");
                    this.setBackupMonitor(bm);
                    this.jobQueue = bm.jobQueue;
                }

        public BackupJob(
                Path savesLocation, 
                String backupFilePath, 
                int fileScheme,
                BackupMonitor bm) {
                    this.setSavesLocation(savesLocation);
                    this.setBackupFilePath(backupFilePath);
                    this.setFileScheme(fileScheme);
                    this.setCompressMethod("zip");
                    this.setBackupMonitor(bm);
                    this.jobQueue = bm.jobQueue;
                }

        public BackupJob(
                Path savesLocation, 
                String backupFilePath, 
                int fileScheme, 
                String compressMethod,
                BackupMonitor bm) {
                    this.setSavesLocation(savesLocation);
                    this.setBackupFilePath(backupFilePath);
                    this.setFileScheme(fileScheme);
                    this.setCompressMethod(compressMethod);
                    this.setBackupMonitor(bm);
                    this.jobQueue = bm.jobQueue;
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

        public BackupMonitor getBackupMonitor() {
            return backupMonitor;
        }

        public void setBackupMonitor(BackupMonitor backupMonitor) {
            this.backupMonitor = backupMonitor;
        }
        
          
        public void kill() {
            jobQueue.stopRunning();
        }
        
        public FileVisitResult preVisitDirectory(Path p, BasicFileAttributes attrs) throws IOException {

            Path zipFilePath = Paths.get(this.getBackupFilePath());
            URI zipUri = URI.create("jar:" + zipFilePath.toUri().toString());
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");

            if (p.equals(this.getSavesLocation())) {
                // don't do anything
            }
            else {
                String pathToAdd = p.toString().replace(this.getSavesLocation().toString(), "").replace("\\", "/");
                try (FileSystem zipfs = FileSystems.newFileSystem(zipUri, env)) {
                    Path p1 = zipfs.getPath(pathToAdd);
                    Files.createDirectories(p1);
                    
                }
                
            }

            return CONTINUE;
        }

        public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) throws IOException {

            Path zipFilePath = Paths.get(this.getBackupFilePath());
            URI zipUri = URI.create("jar:" + zipFilePath.toUri().toString());
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            
            System.out.println("SOURCE DIR: " + p.toString());

            if (p.equals(this.getSavesLocation())) {
                // don't do anything
            }
            else {
                if (Files.isRegularFile(p)) {
                    String pathToAdd = p.toString().replace(this.getSavesLocation().toString(), "").replace("\\", "/");
                    try (FileSystem zipfs = FileSystems.newFileSystem(zipUri, env)) {
                        Path p1 = zipfs.getPath(pathToAdd);
                        if (Files.exists(p1)) {
                            Files.delete(p1);
                        }
                        Files.copy(p,p1);
                        backupMonitor.setRunningFileCount(1);
                        
                        try {
                            this.jobQueue.put(1);
                        } catch (InterruptedException ex) {}

                    }
                }


            }

            return CONTINUE;
        }

        public void run() {
            
            backupMonitor.preCountFiles(this.getSavesLocation().toFile());
            // pass total files to jobQueue
            
            while (jobQueue.isRunning()) {
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

                    try {
                        synchronized (backupMonitor) {
                            Files.walkFileTree(this.getSavesLocation(), this);
                        }
                        

                    } catch (IOException ex) {} 
                    jobQueue.stopRunning();

                }
                
            }
            System.out.println("Backup job is finished");
            btnStartBackup.setEnabled(true);
            btnStartBackup.setText("Start Backup");
            backupMonitor.progressBar.setVisible(false);
            backupMonitor.progressBar.setValue(0);
            
        }
    }
    
    class BackupMonitor implements Runnable {
        
        private final BackupJobQueue jobQueue;
        private int totalFiles;
        private int runningFileCount;
        private final JProgressBar progressBar;
        
        // constructor
        
        public BackupMonitor(BackupJobQueue jobQueue,
                             JProgressBar progressBar) {
            this.jobQueue = jobQueue;
            this.progressBar = progressBar;
            this.runningFileCount = 0;
        }

        public int getTotalFiles() {
            return totalFiles;
        }

        public void setTotalFiles(int totalFiles) {
            this.totalFiles = totalFiles;
        }

        public int getRunningFileCount() {
            return runningFileCount;
        }

        public void setRunningFileCount(int increment) {
            this.runningFileCount += increment;
        }
        
        public int preCountFiles(File fileStartPath) {
            
            int count = 0;
            for (File file : fileStartPath.listFiles()) {

                if (file.isFile()) {
                    count++;
                }
                if (file.isDirectory()) {
                    if (!file.canExecute()) {
                      System.out.println(file.getAbsolutePath() + ": Error, access denied to this directory");
                      // skip it
                    }
                    else {
                        count += preCountFiles(file);
                    }
                }
            }
                        
            progressBar.setIndeterminate(false);
            progressBar.setMaximum(count);
            progressBar.setStringPainted(true);
            this.setTotalFiles(count);
            System.out.println("Expecting " + count + " total files to be backed up");
            return count;
            
        }
        
        public void run() {
            while (jobQueue.isRunning()) {
                try {
                    jobQueue.take();
                    progressBar.setValue(getRunningFileCount());
                    String progressString = "Backed up " + getRunningFileCount() + " of " + getTotalFiles() + " files ";
                    progressBar.setString(progressString);
                }
                catch (InterruptedException ix) {}
            }
        }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgrArchiveHandling = new javax.swing.ButtonGroup();
        lblSavesPath = new javax.swing.JLabel();
        txfSavesPath = new javax.swing.JTextField();
        btnChangeSavepath = new javax.swing.JButton();
        lblSavePathInfo = new javax.swing.JLabel();
        lblSavefileSelection = new javax.swing.JLabel();
        lblSavefileSelectInfo = new javax.swing.JLabel();
        lblCompressMethod = new javax.swing.JLabel();
        cbxCompressMethod = new javax.swing.JComboBox();
        lblCompressMethodInfo = new javax.swing.JLabel();
        lblArchiveOpts = new javax.swing.JLabel();
        lblBackupLocation = new javax.swing.JLabel();
        txfBackupLocation = new javax.swing.JTextField();
        btnBackupLocation = new javax.swing.JButton();
        btnStartBackup = new javax.swing.JButton();
        rdoArchCompressAll = new javax.swing.JRadioButton();
        rdoArchCompressSep = new javax.swing.JRadioButton();
        jscpSelectSavefiles = new javax.swing.JScrollPane();
        jpnlSubSelectSavefiles = new javax.swing.JPanel();
        mbTitleImage = new javax.swing.JLabel();
        chkSelectAll = new javax.swing.JCheckBox();
        lblBackupFileInfo = new javax.swing.JLabel();
        jprgBackupProgress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minecraft Backup v0.1");
        setBackground(new java.awt.Color(252, 252, 252));
        setName("backupFrame"); // NOI18N

        lblSavesPath.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblSavesPath.setText("Minecraft Saves Path");

        txfSavesPath.setText("minecraft backup path");
        txfSavesPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txfSavesPathFocusGained(evt);
            }
        });
        txfSavesPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfSavesPathActionPerformed(evt);
            }
        });

        btnChangeSavepath.setLabel("Change Savepath");
        btnChangeSavepath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangeSavepathMouseClicked(evt);
            }
        });
        btnChangeSavepath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeSavepathActionPerformed(evt);
            }
        });

        lblSavePathInfo.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblSavePathInfo.setForeground(new java.awt.Color(40, 160, 40));
        lblSavePathInfo.setToolTipText("");

        lblSavefileSelection.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblSavefileSelection.setText("Savefile Backup Selection");

        lblSavefileSelectInfo.setText("<html>Select the savefiles you wish to back up.  By default all files are backed up.</html>");
        lblSavefileSelectInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblSavefileSelectInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblSavefileSelectInfo.setFocusable(false);
        lblSavefileSelectInfo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        lblCompressMethod.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblCompressMethod.setText("Compression Method");

        cbxCompressMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ZIP", "gzip" }));

        lblCompressMethodInfo.setText("ZIP is recommended unless you know what you're doing.");

        lblArchiveOpts.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblArchiveOpts.setText("Archive Options");

        lblBackupLocation.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblBackupLocation.setText("Backup file(s) location");

        txfBackupLocation.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txfBackupLocationFocusGained(evt);
            }
        });

        btnBackupLocation.setText("Change");
        btnBackupLocation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackupLocationMouseClicked(evt);
            }
        });
        btnBackupLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupLocationActionPerformed(evt);
            }
        });

        btnStartBackup.setText("Start backup");
        btnStartBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartBackupActionPerformed(evt);
            }
        });

        btgrArchiveHandling.add(rdoArchCompressAll);
        rdoArchCompressAll.setSelected(true);
        rdoArchCompressAll.setText("Compress all savefiles into one archive");
        rdoArchCompressAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoArchCompressAllActionPerformed(evt);
            }
        });

        btgrArchiveHandling.add(rdoArchCompressSep);
        rdoArchCompressSep.setText("Create a separate archive for each savefile");

        jpnlSubSelectSavefiles.setLayout(new javax.swing.BoxLayout(jpnlSubSelectSavefiles, javax.swing.BoxLayout.PAGE_AXIS));
        jscpSelectSavefiles.setViewportView(jpnlSubSelectSavefiles);

        mbTitleImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mightyteegar/MinecraftBackup/images/mcbackup_header.png"))); // NOI18N

        chkSelectAll.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        chkSelectAll.setSelected(true);
        chkSelectAll.setText("Select/Deselect All");
        chkSelectAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSelectAllItemStateChanged(evt);
            }
        });

        lblBackupFileInfo.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblBackupFileInfo.setText(" ");
        lblBackupFileInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblBackupFileInfo.setFocusable(false);
        lblBackupFileInfo.setMinimumSize(new java.awt.Dimension(30, 0));
        lblBackupFileInfo.setRequestFocusEnabled(false);

        jprgBackupProgress.setEnabled(false);
        jprgBackupProgress.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBackupFileInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnStartBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jprgBackupProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblSavesPath)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txfSavesPath, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnChangeSavepath))
                            .addComponent(lblSavePathInfo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSavefileSelection)
                                    .addComponent(lblSavefileSelectInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jscpSelectSavefiles, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkSelectAll))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxCompressMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCompressMethod)
                                    .addComponent(lblCompressMethodInfo)
                                    .addComponent(lblArchiveOpts)
                                    .addComponent(rdoArchCompressAll)
                                    .addComponent(rdoArchCompressSep)))
                            .addComponent(lblBackupLocation)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txfBackupLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBackupLocation))
                            .addComponent(mbTitleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 185, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(mbTitleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSavesPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfSavesPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeSavepath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSavePathInfo)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSavefileSelection)
                    .addComponent(lblCompressMethod))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxCompressMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCompressMethodInfo)
                        .addGap(18, 18, 18)
                        .addComponent(lblArchiveOpts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoArchCompressAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoArchCompressSep))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSavefileSelectInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkSelectAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jscpSelectSavefiles, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblBackupLocation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfBackupLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBackupLocation))
                .addGap(5, 5, 5)
                .addComponent(lblBackupFileInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStartBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jprgBackupProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangeSavepathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeSavepathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChangeSavepathActionPerformed

    private void txfSavesPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfSavesPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfSavesPathActionPerformed

    private void rdoArchCompressAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoArchCompressAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoArchCompressAllActionPerformed

    private void btnBackupLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackupLocationActionPerformed

    private void txfBackupLocationFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txfBackupLocationFocusGained
        // TODO add your handling code here:
        this.txfBackupLocation.setBackground(Color.WHITE);
        this.lblBackupFileInfo.setText(null);
    }//GEN-LAST:event_txfBackupLocationFocusGained

    private void txfSavesPathFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txfSavesPathFocusGained
        // TODO add your handling code here:
        this.txfSavesPath.setBackground(Color.white);
        this.lblSavePathInfo.setText(null);
    }//GEN-LAST:event_txfSavesPathFocusGained

    private void chkSelectAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSelectAllItemStateChanged
        // TODO add your handling code here:
        if (this.chkSelectAll.isSelected()) {
            for (JCheckBox jc : this.listOfSaves) {
                jc.setSelected(true);
            }
        }
        else {
            for (JCheckBox jc : this.listOfSaves) {
                jc.setSelected(false);
            }
        }
    }//GEN-LAST:event_chkSelectAllItemStateChanged

    private void btnChangeSavepathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangeSavepathMouseClicked
        // TODO add your handling code here:
        
        JFileChooser jc = new JFileChooser();
        jc.setPreferredSize(new Dimension(700,400));
        jc.setDialogTitle("Locate your Minecraft save file folder");
        jc.setDialogType(JFileChooser.OPEN_DIALOG);
        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int returnVal = jc.showOpenDialog(BackupForm.this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.txfSavesPath.setText(jc.getSelectedFile().toPath().toString());
        }
        
        
    }//GEN-LAST:event_btnChangeSavepathMouseClicked

    private void btnBackupLocationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackupLocationMouseClicked
        // TODO add your handling code here:
        JFileChooser jc = new JFileChooser();
        jc.setPreferredSize(new Dimension(700,400));
        jc.setDialogTitle("Minecraft backup archive location");
        jc.setDialogType(JFileChooser.SAVE_DIALOG);
        jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int returnVal = jc.showSaveDialog(BackupForm.this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.txfBackupLocation.setText(jc.getSelectedFile().toPath().toString());
        }
    }//GEN-LAST:event_btnBackupLocationMouseClicked

    private void btnStartBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartBackupActionPerformed
        boolean formChecksCleared = true;
        
        if (this.txfSavesPath.getText().replace(" ", "").isEmpty()) {
            this.txfSavesPath.setBackground(Color.PINK);
            this.setSavePathInfo(3);
            formChecksCleared = false;
        }
        if (this.txfBackupLocation.getText().isEmpty()) {
            this.txfBackupLocation.setBackground(Color.PINK);
            this.lblBackupFileInfo.setForeground(Color.red);
            this.lblBackupFileInfo.setText("Backup file location cannot be blank");
            formChecksCleared = false;
        }
        
        Path sp = Paths.get(this.txfSavesPath.getText());
        if (!Files.exists(sp)) {
            this.txfSavesPath.setBackground(Color.PINK);
            this.setSavePathInfo(4);
            formChecksCleared = false;
        }

        if (formChecksCleared) {
            
            
            btnStartBackup.setEnabled(false);
            btnStartBackup.setText("Backup in progress");
            

            try {
                
                Path bp = Paths.get(this.txfBackupLocation.getText());
                
                BackupMonitor backupMonitor = new BackupMonitor(new BackupJobQueue(),jprgBackupProgress);
                BackupJob backupJob = new BackupJob(Paths.get(this.txfSavesPath.getText()),this.txfBackupLocation.getText(),backupMonitor);
                
                Thread bjThread = new Thread(backupJob, "backupJob");
                Thread bmThread = new Thread(backupMonitor, "backupMonitor");
                
                backupMonitor.progressBar.setVisible(true);
                
                bmThread.start();
                bjThread.start();
                
            }

            catch (InvalidPathException pe) {
                
                String err = "Invalid backup file path, make sure the path doesn't contain illegal characters";
                new MinecraftBackup().textFieldHandleError(this.txfBackupLocation,this.lblBackupFileInfo,err);

            }
            
            
            
            
        }
    }//GEN-LAST:event_btnStartBackupActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgrArchiveHandling;
    private javax.swing.JButton btnBackupLocation;
    private javax.swing.JButton btnChangeSavepath;
    private javax.swing.JButton btnStartBackup;
    private javax.swing.JComboBox cbxCompressMethod;
    private javax.swing.JCheckBox chkSelectAll;
    private javax.swing.JPanel jpnlSubSelectSavefiles;
    private javax.swing.JProgressBar jprgBackupProgress;
    private javax.swing.JScrollPane jscpSelectSavefiles;
    private javax.swing.JLabel lblArchiveOpts;
    private javax.swing.JLabel lblBackupFileInfo;
    private javax.swing.JLabel lblBackupLocation;
    private javax.swing.JLabel lblCompressMethod;
    private javax.swing.JLabel lblCompressMethodInfo;
    private javax.swing.JLabel lblSavePathInfo;
    private javax.swing.JLabel lblSavefileSelectInfo;
    private javax.swing.JLabel lblSavefileSelection;
    private javax.swing.JLabel lblSavesPath;
    private javax.swing.JLabel mbTitleImage;
    private javax.swing.JRadioButton rdoArchCompressAll;
    private javax.swing.JRadioButton rdoArchCompressSep;
    private javax.swing.JTextField txfBackupLocation;
    private javax.swing.JTextField txfSavesPath;
    // End of variables declaration//GEN-END:variables
}
