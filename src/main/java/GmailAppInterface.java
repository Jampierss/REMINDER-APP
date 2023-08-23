import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

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
                // Acciones cuando se presiona el botón "Aceptar"
                String senderEmail = senderField.getText();
                String receiverEmail = receiverField.getText();
                java.util.Date selectedDate = calendar.getDate();

                SendEmail sendEmail = new SendEmail(senderEmail, receiverEmail);
                sendEmail.Send();

                // Ejemplo de salida en consola con los datos ingresados
                System.out.println("Fecha seleccionada: " + selectedDate);
                System.out.println("Remitente: " + senderEmail);
                System.out.println("Destinatario: " + receiverEmail);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones cuando se presiona el botón "Cancelar"
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
