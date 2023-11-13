package chattingApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.text.*;
import java.util.*;

public class Client implements ActionListener {
    private Menu menu;
    JTextField text;
    private static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    DataOutputStream oust;
    private boolean isConnected = false;

    // new
    private TrayIcon trayIcon;

    // JFileChooser for file selection
    JFileChooser fileChooser = new JFileChooser();
    // end

    private ImageIcon createImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }

    private JButton createLabel(ImageIcon icon, int x, int y, int width, int height) {
        JButton label = new JButton(icon);
        label.setBounds(x, y, width, height);
        return label;
    }

    Client() {
        menu = new Menu();
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        JButton back = createLabel(createImageIcon("icons/3icon.png", 25, 25), 5, 20, 25, 25);
        p1.add(back);

        // Configure the "profile" icon
        JButton profile = createLabel(createImageIcon("icons/female.jpg", 50, 50), 40, 10, 50, 50);
        p1.add(profile);

        // Configure the "video" icon
        JButton video = createLabel(createImageIcon("icons/video.png", 30, 30), 300, 20, 30, 30);
   
        p1.add(video);

        // Configure the "phone" icon
        JButton phone = createLabel(createImageIcon("icons/phone.png", 35, 30), 360, 20, 35, 30);
        p1.add(phone);

        // new
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image trayImage = Toolkit.getDefaultToolkit().getImage("icon.png");
            PopupMenu popup = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(e -> System.exit(0));
            popup.add(exitItem);
            trayIcon = new TrayIcon(trayImage, "Chat App", popup);
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
            }
        } else {
            System.out.println("SystemTray is not supported.");
        }
        // end

        // Configure the "morevert" icon and its right-click event
        ImageIcon morevertIcon = createImageIcon("icons/3icon.png", 10, 25);
        JButton morevert = createLabel(morevertIcon, 420, 20, 10, 25);
        morevert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    menu.show(morevert, e.getX(), e.getY());
                }
            }
        });
        p1.add(morevert);

        JLabel name = new JLabel("Mr Grey");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel(isConnected ? "Online" : "Offline");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);

        a1 = new JPanel();
        a1.setLayout(null);
        a1.setBounds(5, 75, 425, 570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 475, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        a1.add(text);

       JButton sendButton = new JButton("Send");
        sendButton.setBounds(320, 475, 100, 40);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        sendButton.addActionListener(this);
        a1.add(sendButton);

        /*f.setSize(450, 700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
       */
        // new
        // Send File Button
        JButton sendFileButton = new JButton("Send File");
        sendFileButton.setBounds(220, 475, 100, 40);
        sendFileButton.setBackground(new Color(7, 94, 84));
        sendFileButton.setForeground(Color.WHITE);
        sendFileButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        sendFileButton.setActionCommand("Send File");
        sendFileButton.addActionListener(this);
        a1.add(sendFileButton);
        // end

        f.setSize(450, 700);
        f.setLocation(800, 50);
      //  f.setUndecorated(true);
        f.getContentPane().setBackground(Color.orange);
        f.setVisible(true);
    }

    // ActionListener method for handling the "Send" button click
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            JPanel p2 = formatLabel(out, "Client");

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());

            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            oust.writeUTF(out);
            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // new
    public void sendFile(ActionEvent ae) {
        try {
            String out1 = text.getText();
            JPanel q2 = formatLabel(out1, "Client");

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());

            right.add(q2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            oust.writeUTF(out1);
            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();

            /**
             * FileInputStream fileInputStream = new FileInputStream(file);
             * byte[] buffer = new byte[1024];
             * int bytesRead;
             * oust.writeUTF("FILE");
             * oust.writeUTF(file.getName());
             * while ((bytesRead = fileInputStream.read(buffer)) != -1) {
             * oust.write(buffer, 0, bytesRead);
             * }
             * fileInputStream.close();
             * JPanel p2 = formatLabel("File sent: " + file.getName(), "Client");
             * a1.setLayout(new BorderLayout());
             * JPanel right = new JPanel(new BorderLayout());
             * right.add(p2, BorderLayout.LINE_END);
             * vertical.add(right);
             * vertical.add(Box.createVerticalStrut(15));
             * a1.add(vertical, BorderLayout.PAGE_START);
             * f.repaint();
             * f.invalidate();
             * f.validate();
             * } catch (Exception e) {
             * e.printStackTrace();
             **/
        }
    }
    // end

    // Method for formatting chat messages with a sender label
    public static JPanel formatLabel(String out, String sender) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // Set the background color based on the sender
        if (sender.equals("Client")) {
            output.setBackground(new Color(37, 211, 102));
        } else {
            output.setBackground(Color.WHITE);
        }

        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);
        // make sure that sent messages have a time stamp on it
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    // Main method for starting the client
    /**
     * @param args
     */
    public static void main(String args[]) {
        Client client = new Client();
      

        // Connect to the server and set up I/O streams
        try(Socket s = new Socket("127.0.0.1", 8080)) { // make sure this port is available for use
            DataInputStream din = new DataInputStream(s.getInputStream());
            client.oust = new DataOutputStream(s.getOutputStream());
            client.isConnected = true;
            while (true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                // new
                if (msg.equals("FILE")) {
                    receiveFile(din);
                } else {
                    // end
                    JPanel panel = formatLabel(msg, "Server");

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);

                    f.validate();
                    f.validate();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            client.isConnected = false;
        }
    }
    
    // new
    static void receiveFile(DataInputStream din) {
        try {
            String fileName = din.readUTF();
            long fileSize = din.readLong();
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while (fileSize > 0 && (bytesRead = din.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                fileSize -= bytesRead;
            }
            fileOutputStream.close();

            JPanel panel = formatLabel("File received: " + fileName, "Server");
            JPanel left = new JPanel(new BorderLayout());
            left.add(panel, BorderLayout.LINE_START);
            vertical.add(left);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


