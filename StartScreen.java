
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author damianchng
 */
public class StartScreen extends javax.swing.JFrame {

    private Scanner in;
    private PrintWriter out;
    
    /**
     * Creates new form UI
     */
    public StartScreen(){//initalize scanner and printwriter
        try {
            this.out = new PrintWriter(new BufferedWriter(new FileWriter("Accounts.txt", true)));
        } catch (IOException ex) {
            Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.in = new Scanner(new File("Accounts.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        btnSignIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Tennis Matcher");

        btnCreate.setLabel("Create Account");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(title)
                .addGap(59, 59, 59)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        // if player exists can sign in -> passes user to mainscreen
        TennisPlayer player = newPlayer(true);
        boolean exists = false;
        while(in.hasNextLine()){
            String input = in.nextLine();
            if(player.toString().equals(input)){
                exists = true;
            }
        }
        if(!exists){
            JOptionPane.showMessageDialog(this, "No player exists");
        }else{
            this.setVisible(false);
            new MainScreen(player).setVisible(true);
        }
        out.close();
    }//GEN-LAST:event_btnSignInActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // if player doesn't exist can sign up/create account -> passes user to mainscreen
        TennisPlayer player = newPlayer(false);
        boolean exists = false;
        while(in.hasNextLine()){
            String input = in.nextLine();
            if(player.toString().equals(input)){
                exists = true;
            }
        }
        if(!exists){
            out.println(player);
        }
        out.close();
        this.setVisible(false);
        new MainScreen(player).setVisible(true);
    }//GEN-LAST:event_btnCreateActionPerformed

    private TennisPlayer newPlayer(boolean getLevel){
        //Gets all info to create user
        String name = JOptionPane.showInputDialog("Enter name:");
        int age = 0;
        while (true) {
            try {
                age = Integer.parseInt(JOptionPane.showInputDialog("Enter age:"));
                if (age > 0) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, "You didn't type a real age");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "You didn't type a real age");
            }
        }
        Sex sex = null;
        while (true) {
            String input = JOptionPane.showInputDialog("Enter sex (Male-Female):");
            if (input.equals("Male") || input.equals("Female")) {
                sex = Sex.getSex(input);
                break;
            } else {
                JOptionPane.showMessageDialog(this, "You didn't type a real sex");
            }
        }
        Location location = null;
        while (true) {
            String input = JOptionPane.showInputDialog("Enter location (WEST-EAST):");
            if (input.equals("EAST") || input.equals("WEST")) {
                location = Location.getLocation(input);
                break;
            }else{
                JOptionPane.showMessageDialog(this, "You didn't type a real location");
            }
        }
        if(getLevel){
            int level = 0;
            while (true) {
                try {
                    level = Integer.parseInt(JOptionPane.showInputDialog("Enter level:"));
                    if (level > 0 && level < 11) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "You didn't type a real level");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "You didn't type a real level");
                }
            }
            return new TennisPlayer(name, location, sex, age, level);
        }
        return new TennisPlayer(name, location, sex, age);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
