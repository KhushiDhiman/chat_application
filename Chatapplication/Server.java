package Chatapplication;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server  implements ActionListener{
    JTextField text ;
    JPanel a1; 
    static Box vertical = Box.createVerticalBox() ;
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7 , 94, 84));
        p1.setBounds(0,0,450,70);
        // to set the bounds  of image ,set the layout of panel to null
        p1.setLayout(null);
        f.add(p1);
        // to set image at the background and access from the file directory
        //ImageIcon class is used
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/icons/3.png"));
        // to crop or to small the size of image we use ScaledInstance present in image class in awt
        Image i2 =i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        //to add image we use JLabel class
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        // now the image is not visible because it has been added at frame
        //add(back)
        // to add image on panel
        p1.add(back);
        // to close the process on a single click
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }

        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        //to add image we use JLabel class
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        //to add image we use JLabel class
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);


        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        //to add image we use JLabel class
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);


        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15= new ImageIcon(i14);
        //to add image we use JLabel class
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        // with JLabel we can write anything on Frame
        JLabel name = new JLabel("Sanchit");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status= new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);
        
        // for textfield
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        // for send button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.addActionListener(this);
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);


        f.setSize(450, 700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try{
        String out =text.getText();
        JLabel output = new JLabel(out);

        JPanel p2 = formatLabel(out);
        

         
        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        //this dosent store strings as input
        // but a panel can be added
        right.add(p2,BorderLayout.LINE_END);
        //for multiple  of msgs alignment in right side 
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical,BorderLayout.PAGE_START);

        // to reload the msgs we need object of frame we need to repaint it
        dout.writeUTF(out);

        text.setText(" ");
        f.repaint();
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();

        }

    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style = \"width :150px \">"+out+"</p></html>");
        output.setFont(new Font("TAHOMA",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        //padding
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");


        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);


        return panel;

    }
    public static void main(String [] args){
        new Server();

        try{
            //to create server
            ServerSocket skt = new ServerSocket(6001);
            //to accept all the msgs create loop for infinite times
            while(true){
                Socket s =skt.accept();
                //to recieve msgs
                DataInputStream din = new DataInputStream(s.getInputStream()); 
                // to send msgs
                dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    // display recieved msg on left
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                   f.validate();
                }
            }

        }catch(Exception e){
            // to print the exception
            e.printStackTrace();
        }

    }
    
}
