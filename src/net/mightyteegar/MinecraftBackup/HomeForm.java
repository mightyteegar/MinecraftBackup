/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mightyteegar.MinecraftBackup;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 *
 * @author dave baker
 */
public class HomeForm extends javax.swing.JFrame {

    /**
     * Creates new form HomeForm
     */
    public HomeForm() {
        initComponents();
        
        new MinecraftBackup().jframeInit(this);
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        pack();
        setLocationRelativeTo(null);  // *** this will center your app ***
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mbTitleImage = new javax.swing.JLabel();
        jbtnBackupForm = new javax.swing.JButton();
        lblBackup = new javax.swing.JLabel();
        lblBackupDesc = new javax.swing.JLabel();
        jbtnRestoreForm = new javax.swing.JButton();
        lblRestore = new javax.swing.JLabel();
        lblRestoreDesc = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImages(null);

        mbTitleImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mightyteegar/MinecraftBackup/images/mcbackup_header.png"))); // NOI18N

        jbtnBackupForm.setBackground(new java.awt.Color(230, 245, 230));
        jbtnBackupForm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mightyteegar/MinecraftBackup/images/mc_backup_icon_master_H80.png"))); // NOI18N
        jbtnBackupForm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnBackupForm.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnBackupForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnBackupFormMouseClicked(evt);
            }
        });
        jbtnBackupForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBackupFormActionPerformed(evt);
            }
        });

        lblBackup.setFont(new java.awt.Font("StenbergITC TT", 0, 48)); // NOI18N
        lblBackup.setText("Backup");

        lblBackupDesc.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        lblBackupDesc.setText("Create a backup of your Minecraft save files");
        lblBackupDesc.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jbtnRestoreForm.setBackground(new java.awt.Color(230, 230, 245));
        jbtnRestoreForm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mightyteegar/MinecraftBackup/images/mc_restore_icon_master_H80.png"))); // NOI18N
        jbtnRestoreForm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnRestoreForm.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnRestoreForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnRestoreFormMouseClicked(evt);
            }
        });
        jbtnRestoreForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestoreFormActionPerformed(evt);
            }
        });

        lblRestore.setFont(new java.awt.Font("StenbergITC TT", 0, 48)); // NOI18N
        lblRestore.setText("Restore");

        lblRestoreDesc.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        lblRestoreDesc.setText("Restore Minecraft saves from a previous backup");
        lblRestoreDesc.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mbTitleImage)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnBackupForm, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnRestoreForm, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBackup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBackupDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRestore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRestoreDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mbTitleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnBackupForm, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBackupDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRestoreDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnRestoreForm, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(179, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnBackupFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBackupFormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnBackupFormActionPerformed

    private void jbtnRestoreFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestoreFormActionPerformed
        // TODO add your handling code here:       
    }//GEN-LAST:event_jbtnRestoreFormActionPerformed

    private void jbtnBackupFormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnBackupFormMouseClicked
        // TODO add your handling code here:
        new BackupForm().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jbtnBackupFormMouseClicked

    private void jbtnRestoreFormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnRestoreFormMouseClicked
        // TODO add your handling code here:
        new RestoreForm().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jbtnRestoreFormMouseClicked

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnBackupForm;
    private javax.swing.JButton jbtnRestoreForm;
    private javax.swing.JLabel lblBackup;
    private javax.swing.JLabel lblBackupDesc;
    private javax.swing.JLabel lblRestore;
    private javax.swing.JLabel lblRestoreDesc;
    private javax.swing.JLabel mbTitleImage;
    // End of variables declaration//GEN-END:variables
}
