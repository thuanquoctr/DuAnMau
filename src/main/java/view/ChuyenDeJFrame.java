/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.JDBCUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author DELL
 */
public class ChuyenDeJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChuyenDeJFrame
     */
    private int index;
    String duongdan = "";
    private int stt = 1;

    public ChuyenDeJFrame(String vtro) {
        initComponents();
        setTitle("Quản Lý Chuyên Đề");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fill();
        btnxoa.setEnabled(false);
        btnsua.setEnabled(false);
        btnthem.setEnabled(false);
        btnqr.setEnabled(false);
        vtrolg.setText(vtro);
        seticon();
    }

    public void seticon() {
        ImageIcon icsearch = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\pdf.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        btnpdf.setIcon(icsearch);
        ImageIcon icsearchex = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\excel1.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        btnexcel.setIcon(icsearchex);
    }

    public void tong() {
        try {
            Connection c = JDBCUtil.getConnection();
            Statement stt = c.createStatement();
            ResultSet kq = stt.executeQuery("select count(*) from ChuyenDe");
            while (kq.next()) {
                txttong.setText(kq.getString("count(*)"));
            }
        } catch (Exception e) {
        }
    }

    public void fill() {
        DefaultTableModel model = (DefaultTableModel) tablechuyende.getModel();
        model.setRowCount(0);
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement st = connection.createStatement();
            String sql = " SELECT * FROM ChuyenDe";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String macd = rs.getString("MaCD");
                String tencd = rs.getString("TenCD");
                float hocphi = rs.getFloat("HocPhi");
                int thoiluong = rs.getInt("ThoiLuong");
                String hinh = rs.getString("Hinh");
                String mota = rs.getString("MoTa");
                Object[] datanew = {stt, macd, tencd, Math.round(hocphi), thoiluong, hinh, mota};
                model.addRow(datanew);
                stt++;
                tong();
            }
            stt = 1;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean checkthoiluong() {
        if (Double.valueOf(txtthoiluong.getText().trim()) <= 0) {
            return false;
        }
        return true;
    }

    public boolean checkhocphi() {
        if (Double.valueOf(txthocphi.getText().trim()) <= 0) {
            return false;
        }
        return true;
    }

    public void reset() {
        index = -1;
        txtmacd.setText("");
        txttencd.setText("");
        txtthoiluong.setText("");
        txthocphi.setText("");
        txtmota.setText("");
        btnxoa.setEnabled(false);
        btnsua.setEnabled(false);
        btnthem.setEnabled(false);
        txtmacd.setEnabled(true);
        txtmacd.requestFocus();
        duongdan = "";
        ImageIcon icupdate = new ImageIcon(
                new ImageIcon(duongdan).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
        anh.setIcon(icupdate);
    }

    public boolean check() {
        if (txtmacd.getText().trim().equals("")) {
            return false;
        }
        if (txttencd.getText().trim().equals("")) {
            return false;
        }
        if (txtthoiluong.getText().trim().equals("")) {
            return false;
        }
        if (txthocphi.getText().trim().equals("")) {
            return false;
        }
        if (txtmota.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean checkusername() {
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from ChuyenDe as cd where cd.TenCD = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, txttencd.getText());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser2 = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        anh = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtmacd = new javax.swing.JTextField();
        txttencd = new javax.swing.JTextField();
        txtthoiluong = new javax.swing.JTextField();
        txthocphi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtmota = new javax.swing.JTextArea();
        btnmoi = new javax.swing.JButton();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablechuyende = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txttong = new javax.swing.JLabel();
        btnpdf = new javax.swing.JButton();
        btnexcel = new javax.swing.JButton();
        btnqr = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        vtrolg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Hình Logo");

        anh.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(51, 51, 255)));
        anh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anhMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Mã Chuyên Đề");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Tên Chuyên Đề");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Học Phí");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Thời Lượng (Giờ)");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Mô Tả Chuyên Đề");

        txtmacd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txttencd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtthoiluong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txthocphi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtmota.setColumns(20);
        txtmota.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtmota.setRows(20);
        jScrollPane1.setViewportView(txtmota);

        btnmoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnmoi.setText("Mới");
        btnmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmoiActionPerformed(evt);
            }
        });

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton5.setText("|<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton6.setText("<<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton7.setText(">|");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton8.setText(">>");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(anh, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txtmacd))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(txtthoiluong))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(txthocphi))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(txttencd))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)))
                        .addGap(0, 682, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtmacd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttencd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtthoiluong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthocphi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(anh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 65, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Cập Nhật", jPanel1);

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        tablechuyende.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ CHUYÊN ĐỀ", "TÊN CHUYÊN ĐỀ", "HỌC PHÍ", "THỜI LƯỢNG", "HÌNH"
            }
        ));
        tablechuyende.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablechuyendeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablechuyende);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Số Lượng Chuyên Đề :");

        txttong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnpdf.setText("Pdf");
        btnpdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpdfActionPerformed(evt);
            }
        });

        btnexcel.setText("Excel");
        btnexcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexcelActionPerformed(evt);
            }
        });

        btnqr.setText("QR Code");
        btnqr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnqrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttong, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnqr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnexcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnpdf)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txttong))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnpdf)
                    .addComponent(btnexcel)
                    .addComponent(btnqr))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách", jPanel2);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setText("Xin Chào :");

        vtrolg.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        vtrolg.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vtrolg, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(vtrolg))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anhMouseClicked
        try {
            jFileChooser2.setDialogTitle("Mở File");
            jFileChooser2.showOpenDialog(this);
            File ftenanh = jFileChooser2.getSelectedFile();
            duongdan = ftenanh.getAbsolutePath();
            ImageIcon icupdate = new ImageIcon(
                    new ImageIcon(duongdan).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
            anh.setIcon(icupdate);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_anhMouseClicked
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.dispose();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            new View_Master(vtrolg.getText()).setVisible(true);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            index = 0;
            if (index >= 0) {
                btnxoa.setEnabled(true);
                btnsua.setEnabled(true);
                btnthem.setEnabled(false);
            }
            tablechuyende.setRowSelectionInterval(index, index);
            Connection cnss = JDBCUtil.getConnection();
            PreparedStatement stt = cnss.prepareStatement("select MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa from ChuyenDe where MaCD = ?");
            stt.setString(1, (String) tablechuyende.getValueAt(index, 1));
            ResultSet kqq = stt.executeQuery();
            while (kqq.next()) {
                txtmacd.setEnabled(false);
                txtmacd.setText(kqq.getString("MaCD"));
                txttencd.setText(kqq.getString("TenCD"));
                txtthoiluong.setText(kqq.getString("ThoiLuong"));
                txthocphi.setText(kqq.getString("HocPhi"));
                txtmota.setText(kqq.getString("MoTa"));
                ImageIcon icupdate = new ImageIcon(
                        new ImageIcon(kqq.getString("Hinh")).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
                anh.setIcon(icupdate);
            }

        } catch (Exception e) {
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from ChuyenDe");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int length = Integer.parseInt(kq.getString("count(*)"));
                index = length - 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                tablechuyende.setRowSelectionInterval(index, index);
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa from ChuyenDe where MaCD = ?");
                stt.setString(1, (String) tablechuyende.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtmacd.setEnabled(false);
                    txtmacd.setText(kqq.getString("MaCD"));
                    txttencd.setText(kqq.getString("TenCD"));
                    txtthoiluong.setText(kqq.getString("ThoiLuong"));
                    txthocphi.setText(kqq.getString("HocPhi"));
                    txtmota.setText(kqq.getString("MoTa"));
                    ImageIcon icupdate = new ImageIcon(
                            new ImageIcon(kqq.getString("Hinh")).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
                    anh.setIcon(icupdate);
                }
            }
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from ChuyenDe");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int length = Integer.parseInt(kq.getString("count(*)"));
                index = tablechuyende.getSelectedRow() - 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                if (index < 0) {
                    index = length - 1;
                }
                tablechuyende.setRowSelectionInterval(index, index);
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa from ChuyenDe where MaCD = ?");
                stt.setString(1, (String) tablechuyende.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtmacd.setEnabled(false);
                    txtmacd.setText(kqq.getString("MaCD"));
                    txttencd.setText(kqq.getString("TenCD"));
                    txtthoiluong.setText(kqq.getString("ThoiLuong"));
                    txthocphi.setText(kqq.getString("HocPhi"));
                    txtmota.setText(kqq.getString("MoTa"));
                    ImageIcon icupdate = new ImageIcon(
                            new ImageIcon(kqq.getString("Hinh")).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
                    anh.setIcon(icupdate);
                }
            }
        } catch (Exception e) {
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from ChuyenDe");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int length = Integer.parseInt(kq.getString("count(*)"));
                index = tablechuyende.getSelectedRow() + 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                if (index < 0 || index > length - 1) {
                    index = 0;
                }
                tablechuyende.setRowSelectionInterval(index, index);
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa from ChuyenDe where MaCD = ?");
                stt.setString(1, (String) tablechuyende.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtmacd.setEnabled(false);
                    txtmacd.setText(kqq.getString("MaCD"));
                    txttencd.setText(kqq.getString("TenCD"));
                    txtthoiluong.setText(kqq.getString("ThoiLuong"));
                    txthocphi.setText(kqq.getString("HocPhi"));
                    txtmota.setText(kqq.getString("MoTa"));
                    ImageIcon icupdate = new ImageIcon(
                            new ImageIcon(kqq.getString("Hinh")).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
                    anh.setIcon(icupdate);
                }
            }
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        if (check()) {
            if (checkusername()) {
                try {
                    if (checkhocphi()) {
                        if (checkthoiluong()) {
                            try {
                                if (txtmacd.getText().trim().length() > 5) {
                                    JOptionPane.showMessageDialog(null, "Mã Chuyên Đề Không Vượt Quá 5 Ký Tự !");
                                    return;
                                }
                                Connection connection = JDBCUtil.getConnection();
                                String sql = "insert into ChuyenDe values (?,?,?,?,?,?)";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, txtmacd.getText());
                                statement.setString(2, txttencd.getText());
                                statement.setString(3, txthocphi.getText());
                                statement.setString(4, txtthoiluong.getText());
                                statement.setString(5, duongdan);
                                statement.setString(6, txtmota.getText());
                                int kq = statement.executeUpdate();
                                if (kq > 0) {
                                    JOptionPane.showMessageDialog(null, "Thêm thành công !");
                                    fill();
                                    reset();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Thêm không thành công !");
                                }

                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, "Mã Chuyên Đề Tồn Tại");
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Học phí phải lớn hơn 0");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số thời lượng và học phí !");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Tên chuyên đề tồn tại ,vui lòng chọn tên khác");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin !");
            return;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
        reset();
        txtmacd.setEnabled(true);
        btnthem.setEnabled(true);
    }//GEN-LAST:event_btnmoiActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        if (check()) {
            try {
                Connection connection = JDBCUtil.getConnection();
                String sql = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, txttencd.getText());
                statement.setString(2, txthocphi.getText());
                statement.setString(3, txtthoiluong.getText());
                statement.setString(4, duongdan);
                statement.setString(5, txtmota.getText());
                statement.setString(6, txtmacd.getText());
                int kq = statement.executeUpdate();
                if (kq > 0) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công !");
                    fill();
                    reset();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa không thành công !");
                }

            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Err !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin !");
            return;
        }         // TODO add your handling code here:
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("DELETE FROM ChuyenDe WHERE MaCD=?");
            st.setString(1, txtmacd.getText());
            int kq = st.executeUpdate();
            if (kq > 0) {
                JOptionPane.showMessageDialog(null, "Xóa thành công !");
                fill();
                reset();
            } else {
                JOptionPane.showMessageDialog(null, "Xóa không thành công !");
            }

        } catch (SQLException eee) {
            JOptionPane.showMessageDialog(this, "Chuyên đề này hiện đang có khóa học không thể xóa !");
            return;
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void tablechuyendeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablechuyendeMouseClicked
        index = tablechuyende.getSelectedRow();
        if (index >= 0) {
            btnxoa.setEnabled(true);
            btnsua.setEnabled(true);
            btnqr.setEnabled(true);
            btnthem.setEnabled(false);
        }
        String ma = tablechuyende.getValueAt(index, 1).toString();
        String ten = tablechuyende.getValueAt(index, 2).toString();
        String hp = tablechuyende.getValueAt(index, 3).toString();
        String thoiluong = tablechuyende.getValueAt(index, 4).toString();
        duongdan = tablechuyende.getValueAt(index, 5).toString();
        txtmacd.setText(ma);
        txtmacd.setEnabled(false);
        txttencd.setText(ten);
        txtthoiluong.setText(thoiluong);
        int hphi = (int) Math.round(Double.valueOf(hp));
        txthocphi.setText(String.valueOf(hphi));
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select MoTa from ChuyenDe where MaCD = ?");
            st.setString(1, ma);
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                txtmota.setText(kq.getString("MoTa"));
            }
        } catch (Exception e) {
        }
        ImageIcon icupdate = new ImageIcon(
                new ImageIcon(tablechuyende.getValueAt(index, 5).toString()).getImage().getScaledInstance(207, 210, java.awt.Image.SCALE_SMOOTH));
        anh.setIcon(icupdate);// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tablechuyendeMouseClicked

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked

    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void btnpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpdfActionPerformed
        MessageFormat header = new MessageFormat("Danh sách chuyên đề");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tablechuyende.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnpdfActionPerformed
    public void Excel(JTable table, File file) {
        try {
            HSSFWorkbook fWorkbook = new HSSFWorkbook();
            HSSFSheet fSheet = fWorkbook.createSheet();
            HSSFRow fRow = fSheet.createRow((short) 0);
            TableModel m = table.getModel();
            for (int i = 0; i < m.getColumnCount(); i++) {
                fRow.createCell((short) i).setCellValue(m.getColumnName(i));
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                HSSFRow row = fSheet.createRow((short) i + 1);
                for (int j = 0; j < m.getColumnCount(); j++) {
                    row.createCell((short) j).setCellValue(table.getValueAt(i, j) == null ? "" : table.getValueAt(i, j) + "");
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            JOptionPane.showMessageDialog(this, "OK");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Err");
        }
    }
    private void btnexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexcelActionPerformed
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getName();
            String path = chooser.getSelectedFile().getParentFile().getPath();
            String file = Paths.get(path, name + ".xls").toString();
            Excel(tablechuyende, new File(file));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnexcelActionPerformed

    private void btnqrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrActionPerformed
        try {
            String sothutu = tablechuyende.getValueAt(index, 0).toString();
            String ma = tablechuyende.getValueAt(index, 1).toString();
            String cd = tablechuyende.getValueAt(index, 2).toString();
            String hp = tablechuyende.getValueAt(index, 3).toString();
            String tl = tablechuyende.getValueAt(index, 4).toString();
            String data = "STT: " + sothutu + "\nMaChuyenDe: " + ma + " \nTenChuyenDe: " + cd + "\nHocPhi: " + hp + "\nThoiLuong: " + tl;
            String patch = "C:\\Users\\DELL\\Downloads\\Hinh\\qr.png";
            BitMatrix mattrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(mattrix, "png", Paths.get(patch));
            new viewqr().setVisible(true);
        } catch (Exception e) {
            System.out.println("Err");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnqrActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anh;
    private javax.swing.JButton btnexcel;
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnpdf;
    private javax.swing.JButton btnqr;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablechuyende;
    private javax.swing.JTextField txthocphi;
    private javax.swing.JTextField txtmacd;
    private javax.swing.JTextArea txtmota;
    private javax.swing.JTextField txttencd;
    private javax.swing.JTextField txtthoiluong;
    private javax.swing.JLabel txttong;
    private javax.swing.JLabel vtrolg;
    // End of variables declaration//GEN-END:variables
}
