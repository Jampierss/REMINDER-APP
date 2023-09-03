import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import controller.SendEmail;

public class GmailAppInterface {
    public static void main(String[] args) {
        // Set FlatDarkLaf as the Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gmail App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(GmailAppInterface.class.getResource("/images/icono.png"));
        frame.setIconImage(icon.getImage());

        // Calendar
        JCalendar calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(300, 250));
        calendar.setDecorationBackgroundColor(Color.GRAY);
        calendar.setForeground(Color.WHITE);
        calendar.setWeekdayForeground(Color.WHITE);
        calendar.setWeekOfYearVisible(false);
        frame.add(calendar, BorderLayout.NORTH);

        // Gmail Entry Fields
        //JPanel fieldsPanel = new JPanel(new GridLayout(2, 2));
        //fieldsPanel.setBackground(Color.DARK_GRAY);
        //fieldsPanel.setBorder(new EmptyBorder(20, 20, 20, 0));

        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldsPanel.setBackground(Color.DARK_GRAY);
        fieldsPanel.setBorder(new EmptyBorder(0, 20, 20, 20)); // Ajusta el relleno

        // Etiqueta y campo de texto para el remitente
        JLabel senderLabel = new JLabel("Gmail:       ");
        senderLabel.setForeground(Color.WHITE);
        JTextField senderField = new JTextField(23); // Establece el ancho deseado

        fieldsPanel.add(senderLabel);
        fieldsPanel.add(senderField);

        // Etiqueta y campo de texto para el destinatario
        JLabel receiverLabel = new JLabel("Reminder: ");
        receiverLabel.setForeground(Color.WHITE);
        JTextField receiverField = new JTextField(23); // Establece el ancho deseado

        fieldsPanel.add(receiverLabel);
        fieldsPanel.add(receiverField);

        frame.add(fieldsPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.BLACK);
        JButton acceptButton = new JButton("SEND");
        JButton cancelButton = new JButton("CANCEL");
        buttonsPanel.add(acceptButton);
        buttonsPanel.add(cancelButton);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senderEmail = senderField.getText();
                String receiverEmail = receiverField.getText();
                String selectedDate = calendar.getDate().toString();

                JDialog progressDialog = new JDialog(frame, "Sending...", Dialog.ModalityType.APPLICATION_MODAL);
                JProgressBar progressBar = new JProgressBar();
                progressBar.setIndeterminate(true);
                progressBar.setPreferredSize(new Dimension(200, 20));
                progressDialog.add(progressBar);
                progressDialog.pack();
                progressDialog.setLocationRelativeTo(frame);
                progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                Thread sendThread = new Thread(() -> {
                    SendEmail sendEmail = new SendEmail(senderEmail, receiverEmail, selectedDate);



                    if (sendEmail.Send()) {
                        JOptionPane.showMessageDialog(frame, "Email was sent correctly!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Something went wrong!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                    progressDialog.dispose();
                });

                sendThread.start();
                progressDialog.setVisible(true);

                System.out.println("Fecha seleccionada: " + selectedDate);
                System.out.println("Remitente: " + senderEmail);
                System.out.println("Destinatario: " + receiverEmail);
            }
        });

        cancelButton.addActionListener(e -> {
            System.exit(0);
        });

        frame.setVisible(true);
    }
}
