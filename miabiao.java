package Miaobiao;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.*;
import java.util.Date;
import java.util.Timer;


public class MiaoBiao {
      //获得记录的时间
      SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");
      Date date = new Date(System.currentTimeMillis());
      String s;
    // 创造各个组件
    final static double pai = 3.14159;
    JFrame jf = new JFrame("秒表窗口");
    JPanel jp_one = new JPanel();
    JPanel jp_two = new JPanel();
    JPanel jp_three = new JPanel();
    static int location_x = 0;
    static int location_y = 110;
    static MyCanvas myCanvas = new MyCanvas();

    JButton jb_one = new JButton("开始");
    JButton jb_two = new JButton("暂停");
    JButton jb_three = new JButton("复位");
    // 未开始时点击复位弹出的对话框
    JDialog jd = new JDialog(jf, true);
    JTextArea jta = new JTextArea("程序未开始，此按钮不可选！");

    static JTextField tf = new JTextField(10);
    JTextArea ta = new JTextArea(30, 10);
    // 设置一个变量来观察秒表是否暂停
    static boolean isStop = true;
    // 设置一个变量记录时间
    static private int t = 0;
    // 设置一个变量是否开始
    static boolean haveStart = false;
    // 设置一个变量记录秒表运行次数
    static int number = 0;
    

    private static class MyTimer extends TimerTask {

        @Override
        public void run() {
            if (isStop) {

            } else {
            	//更新指针终点的x，y坐标，并且更新单行文本域的内容
                t++;
                location_x = (int) (Math.cos((pai / 2 - t * pai / 30)) * 110);
                location_y = (int) (Math.sin((pai / 2 - t * pai / 30)) * 110);
                tf.setText("" + t + "秒");
                myCanvas.repaint();
               
            }
            // TODO Auto-generated method stub

        }

    }
  /*  void  Jdbc() throws SQLException
    {  
    	Driver driver =new com.mysql.jdbc.Driver();
    	String url="jdbc:mysql://localhost:3306/miaobiao";
    	Properties info =new Properties();
    	info.setProperty("user", "root");
    	info.setProperty("password", "root");
    	Connection conn=driver.connect(url, info);
    	String sql="insert into record(number,time,date) values(?,?,?)";
    	PreparedStatement ps=conn.prepareStatement(sql);
    	ps.setInt(1, number);
    	ps.setString(2, t+"秒");
    	ps.setString(3,s);
    	ps.execute();
    	ps.close();
    	conn.close();
    }*/
    // 组装函数
    public void init() {
        // 设置弹窗内容
        jd.setBounds(300, 300, 300, 200);
        jd.setTitle("错误！");
        jd.add(jta);
        // jd.setTe
        // 给复位按钮设置成灰色
        jb_two.setBackground(Color.gray);
        jb_three.setBackground(Color.gray);
        // 给按钮添加事件监听
        jb_one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jb_two.getActionCommand().equals("暂停")) {
                    isStop = false;
                    haveStart = true;
                    jb_two.setBackground(new Color(236, 242, 248));
                    jb_three.setBackground(new Color(236, 242, 248));
                }
            }
        });
        jb_two.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 判断这个按钮当前的状态
                if (!haveStart && e.getActionCommand().equals("暂停")) {
                    jd.setVisible(true);
                } else if (haveStart && e.getActionCommand().equals("暂停")) {
                    isStop = true;
                    jb_two.setText("继续");
                } else {
                    isStop = false;
                    jb_two.setText("暂停");
                }

            }
        });
        jb_three.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (haveStart) {
                    // 更新成员变量的属性值
              
                    isStop = true;
                    haveStart = false;
                    location_x = 0;
                    location_y = 110;
                    jb_two.setText("暂停");
                    // 在右边的文本域中更新内容
                    ta.append("第" + (++number )+ "次：" + t + "秒" + "\n");
                    //写入数据库
  	           /* try {
                		date = new Date(System.currentTimeMillis());
                		s=formatter.format(date);
						Jdbc();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
                    t = 0;
                    tf.setText("");
                    // 给两个按钮回复颜色
                    jb_three.setBackground(Color.gray);
                    jb_two.setBackground(Color.gray);
                    myCanvas.repaint();

                } else {
                    jd.setVisible(true);
                }
            }
        });
        // 给三个面板添加组件
        myCanvas.setSize(new Dimension(360, 360));
        //myCanvas.setBackground(Color.blue);
        jp_one.add(myCanvas);
        jp_one.add(tf);
        jp_two.add(jb_one);
        jp_two.add(jb_two);
        jp_two.add(jb_three);
        jp_three.add(ta);

        // 给窗口添加三个面板
        jf.add(jp_one, BorderLayout.CENTER);
        jf.add(jp_two, BorderLayout.SOUTH);
        jf.add(jp_three, BorderLayout.EAST);

        // 设置窗口位置，大小，可见性，添加窗口关闭监听
        jf.setBounds(200, 200, 500, 500);
        jf.setVisible(true);
        //jf.addWindowListener(new MyListener());
        jf.setDefaultCloseOperation(1);

        Timer timer = new Timer();
        timer.schedule(new MyTimer(), 0, 1000);

    }

    public static void main(String[] args) {
        new MiaoBiao().init();
    }

 

    // 创建画布
    private static class MyCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            // 绘制钟表图
            g.drawOval(60, 40, 250, 250);
            g.setFont((new Font("Times", Font.BOLD, 30)));
            g.drawString("12", 170, 65);
            g.drawString("3", 290, 175);
            g.drawString("6", 178, 288);
            g.drawString("9", 62, 175);
            g.drawString("1", 235, 80);
            g.drawString("2", 275, 120);
            g.drawString("4", 275, 235);
            g.drawString("5", 235, 275);
            g.drawString("7", 126, 275);
            g.drawString("8", 80, 235);
            g.drawString("10", 75, 120);
            g.drawString("11", 115, 80);
            // 绘制指针,利用计时器更新 a b 的值使指针不停的转动
            int a = 185 + location_x;
            int b = 165 - location_y;
            g.drawLine(185, 165, a, b);

        }

    }
}
