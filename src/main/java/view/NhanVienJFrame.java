/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Util.MaHoa;
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
public class NhanVienJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienJFrame
     */
    private int index;
    private int stt = 1;

    public NhanVienJFrame(String vt) {
        initComponents();
        setTitle("Quản Lý Nhân Viên");
        setLocationRelativeTo(null);
        fill();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnxoa.setEnabled(false);
        btnsua.setEnabled(false);
        btnthem.setEnabled(false);
        vtro.setText(vt);
        seticon();
        btnqrcode.setEnabled(false);

    }

    public void seticon() {
        ImageIcon icsearch = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\pdf.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        btnprintpdf.setIcon(icsearch);
        ImageIcon icsearchex = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\excel1.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        btnprintexcel.setIcon(icsearchex);
    }

    public void tong() {
        try {
            Connection c = JDBCUtil.getConnection();
            Statement st = c.createStatement();
            ResultSet kq = st.executeQuery("select count(*) from NhanVien");
            while (kq.next()) {
                txttong.setText(kq.getString("count(*)"));
            }
        } catch (Exception e) {
        }
    }

    public void fill() {
        DefaultTableModel model = (DefaultTableModel) tabledsnhanvien.getModel();
        model.setRowCount(0);
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement st = connection.createStatement();
            String sql = " SELECT * FROM NhanVien";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String masv = rs.getString("MaNV");
                String mk = rs.getString("MatKhau");
                String ht = rs.getString("HoTen");
                int vt = rs.getInt("VaiTro");
                String vtro = "";
                if (vt == 1) {
                    vtro = "Trưởng Phòng";
                }
                if (vt == 0) {
                    vtro = "Nhân Viên";
                }
                Object[] datanew = {stt, masv, mk, ht, vtro};
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

    public void reset() {
        index = -1;
        txtmanv.setText("");
        txtmk1.setText("");
        txtmk2.setText("");
        txthoten.setText("");
        grnhanvien.clearSelection();
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
        txtmanv.setEnabled(true);

    }

    public boolean check() {
        if (txtmanv.getText().trim().equals("")) {
            return false;
        }
        if (txtmk1.getText().trim().equals("")) {
            return false;
        }
        if (txtmk2.getText().trim().equals("")) {
            return false;
        }
        if (txthoten.getText().trim().equals("")) {
            return false;
        }
        if (!chktruongphong.isSelected() && !chknhanvien.isSelected()) {
            return false;
        }
        return true;
    }

    public boolean checkusername() {
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "select * from NhanVien as nv where nv.HoTen = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, txthoten.getText());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
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

        grnhanvien = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtmanv = new javax.swing.JTextField();
        txtmk1 = new javax.swing.JPasswordField();
        txtmk2 = new javax.swing.JPasswordField();
        txthoten = new javax.swing.JTextField();
        chktruongphong = new javax.swing.JRadioButton();
        chknhanvien = new javax.swing.JRadioButton();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnmoi = new javax.swing.JButton();
        dau = new javax.swing.JButton();
        lui = new javax.swing.JButton();
        tien = new javax.swing.JButton();
        cuoi = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        vtro = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledsnhanvien = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txttong = new javax.swing.JLabel();
        btnprintpdf = new javax.swing.JButton();
        btnprintexcel = new javax.swing.JButton();
        btnqrcode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN QUẢN TRỊ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Mã Nhân Viên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mật Khẩu");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Xác Nhận Mật Khẩu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Vai Trò");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Họ Và Tên");

        txtmanv.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtmk1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtmk2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txthoten.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        grnhanvien.add(chktruongphong);
        chktruongphong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chktruongphong.setText("Trưởng Phòng");

        grnhanvien.add(chknhanvien);
        chknhanvien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chknhanvien.setText("Nhân Viên");

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

        btnmoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnmoi.setText("Mới");
        btnmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmoiActionPerformed(evt);
            }
        });

        dau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dau.setText("|<");
        dau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dauActionPerformed(evt);
            }
        });

        lui.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lui.setText("<<");
        lui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luiActionPerformed(evt);
            }
        });

        tien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tien.setText(">>");
        tien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tienActionPerformed(evt);
            }
        });

        cuoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cuoi.setText(">|");
        cuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuoiActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Xin Chào :");

        vtro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        vtro.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnthem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnxoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnmoi)
                        .addGap(109, 109, 109)
                        .addComponent(dau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lui)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cuoi)
                        .addGap(0, 44, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chktruongphong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chknhanvien)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmk2, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(txtmanv)
                                .addComponent(txtmk1, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE))
                            .addComponent(txthoten, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vtro, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmk1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmk2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chktruongphong)
                    .addComponent(chknhanvien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dau, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lui, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(vtro))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cập Nhật", jPanel1);

        tabledsnhanvien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabledsnhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ NHÂN VIÊN", "MẬT KHẨU", "HỌ VÀ TÊN", "VAI TRÒ"
            }
        ));
        tabledsnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledsnhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabledsnhanvien);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Tổng Số Nhân Viên :");

        txttong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnprintpdf.setText("Pdf");
        btnprintpdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintpdfActionPerformed(evt);
            }
        });

        btnprintexcel.setText("Excel");
        btnprintexcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintexcelActionPerformed(evt);
            }
        });

        btnqrcode.setText("QR Code");
        btnqrcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnqrcodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnqrcode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnprintexcel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnprintpdf)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txttong)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnprintpdf)
                            .addComponent(btnprintexcel)
                            .addComponent(btnqrcode))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.dispose();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            new View_Master(vtro.getText()).setVisible(true);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if (check()) {
            if (txtmk1.getText().equals(txtmk2.getText())) {
                if (checkusername()) {
                    try {
                        Connection connection = JDBCUtil.getConnection();
                        String sql = "insert into NhanVien values (?,?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, txtmanv.getText());
                        statement.setString(2, MaHoa.MaHoaMK(txtmk1.getText()));
                        statement.setString(3, txthoten.getText());
                        int vt = -1;
                        if (chktruongphong.isSelected()) {
                            vt = 1;
                        }
                        if (chknhanvien.isSelected()) {
                            vt = 0;
                        }
                        statement.setInt(4, vt);
                        int kq = statement.executeUpdate();
                        if (kq > 0) {
                            JOptionPane.showMessageDialog(null, "Thêm thành công !");
                            fill();
                            reset();
                            btnthem.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Thêm không thành công !");
                        }

                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Mã NV tồn tại , vui lòng chọn mã khác !");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập tồn tại ,vui lòng chọn tên khác");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu không hợp lệ !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin !");
            return;
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void tabledsnhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledsnhanvienMouseClicked
        index = tabledsnhanvien.getSelectedRow();
        if (index >= 0) {
            btnxoa.setEnabled(true);
            btnsua.setEnabled(true);
            btnqrcode.setEnabled(true);
        }
        String ma = tabledsnhanvien.getValueAt(index, 1).toString();
        String mk1 = tabledsnhanvien.getValueAt(index, 2).toString();
        String hten = tabledsnhanvien.getValueAt(index, 3).toString();
        String gd = tabledsnhanvien.getValueAt(index, 4).toString();
        txtmanv.setText(ma);
        txtmanv.setEnabled(false);
        txtmk1.setText(mk1);
        txtmk2.setText(mk1);
        txthoten.setText(hten);
        if (gd.equals("Nhân Viên")) {
            chknhanvien.setSelected(true);
        } else {
            chktruongphong.setSelected(true);
        }
//        tabledsnhanvien.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_tabledsnhanvienMouseClicked

    private void cuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuoiActionPerformed
        // TODO add your handling code here:
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from NhanVien");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int length = Integer.parseInt(kq.getString("count(*)"));
                index = length - 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                tabledsnhanvien.setRowSelectionInterval(index, index);
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select MaNV,MatKhau,HoTen,VaiTro from NhanVien where MaNV = ?");
                stt.setString(1, (String) tabledsnhanvien.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtmanv.setEnabled(false);
                    txtmanv.setText(kqq.getString("MaNV"));
                    txtmk1.setText(kqq.getString("MatKhau"));
                    txtmk2.setText(kqq.getString("MatKhau"));
                    txthoten.setText(kqq.getString("HoTen"));
                    if (kqq.getString("VaiTro").equals("0")) {
                        chknhanvien.setSelected(true);
                    } else {
                        chktruongphong.setSelected(true);
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cuoiActionPerformed

    private void tienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tienActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from NhanVien");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int length = Integer.parseInt(kq.getString("count(*)"));
                index = tabledsnhanvien.getSelectedRow() + 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                if (index < 0 || index > length - 1) {
                    index = 0;
                }
                tabledsnhanvien.setRowSelectionInterval(index, index);
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select MaNV,MatKhau,HoTen,VaiTro from NhanVien where MaNV = ?");
                stt.setString(1, (String) tabledsnhanvien.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtmanv.setEnabled(false);
                    txtmanv.setText(kqq.getString("MaNV"));
                    txtmk1.setText(kqq.getString("MatKhau"));
                    txtmk2.setText(kqq.getString("MatKhau"));
                    txthoten.setText(kqq.getString("HoTen"));
                    if (kqq.getString("VaiTro").equals("0")) {
                        chknhanvien.setSelected(true);
                    } else {
                        chktruongphong.setSelected(true);
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tienActionPerformed

    private void luiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luiActionPerformed

        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from NhanVien");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int length = Integer.parseInt(kq.getString("count(*)"));
                index = tabledsnhanvien.getSelectedRow() - 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                if (index < 0) {
                    index = length - 1;
                }
                tabledsnhanvien.setRowSelectionInterval(index, index);
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select MaNV,MatKhau,HoTen,VaiTro from NhanVien where MaNV = ?");
                stt.setString(1, (String) tabledsnhanvien.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtmanv.setEnabled(false);
                    txtmanv.setText(kqq.getString("MaNV"));
                    txtmk1.setText(kqq.getString("MatKhau"));
                    txtmk2.setText(kqq.getString("MatKhau"));
                    txthoten.setText(kqq.getString("HoTen"));
                    if (kqq.getString("VaiTro").equals("0")) {
                        chknhanvien.setSelected(true);
                    } else {
                        chktruongphong.setSelected(true);
                    }
                }
            }
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_luiActionPerformed

    private void dauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dauActionPerformed
        try {
            index = 0;
            if (index >= 0) {
                btnxoa.setEnabled(true);
                btnsua.setEnabled(true);
                btnthem.setEnabled(false);
            }
            tabledsnhanvien.setRowSelectionInterval(index, index);
            Connection cnss = JDBCUtil.getConnection();
            PreparedStatement stt = cnss.prepareStatement("select MaNV,MatKhau,HoTen,VaiTro from NhanVien where MaNV = ?");
            stt.setString(1, (String) tabledsnhanvien.getValueAt(index, 1));
            ResultSet kqq = stt.executeQuery();
            while (kqq.next()) {
                txtmanv.setEnabled(false);
                txtmanv.setText(kqq.getString("MaNV"));
                txtmk1.setText(kqq.getString("MatKhau"));
                txtmk2.setText(kqq.getString("MatKhau"));
                txthoten.setText(kqq.getString("HoTen"));
                if (kqq.getString("VaiTro").equals("0")) {
                    chknhanvien.setSelected(true);
                } else {
                    chktruongphong.setSelected(true);
                }
            }

        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_dauActionPerformed

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
        reset();
        txtmanv.requestFocus();
        btnthem.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnmoiActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        if (check()) {
            if (txtmk1.getText().equals(txtmk2.getText())) {
                try {
                    Connection cnschecksua = JDBCUtil.getConnection();
                    PreparedStatement stmchecksua = cnschecksua.prepareStatement("select HoTen,VaiTro from NhanVien where HoTen = ?");
                    stmchecksua.setString(1, vtro.getText());
                    ResultSet resultSetchecksua = stmchecksua.executeQuery();
                    while (resultSetchecksua.next()) {
                        Connection cnschecksua1 = JDBCUtil.getConnection();
                        PreparedStatement stmchecksua1 = cnschecksua1.prepareStatement("select HoTen,VaiTro from NhanVien where MaNV = ?");
                        stmchecksua1.setString(1, txtmanv.getText());
                        ResultSet resultSetchecksua1 = stmchecksua1.executeQuery();
                        while (resultSetchecksua1.next()) {
                            if (!resultSetchecksua.getString("HoTen").equals(resultSetchecksua1.getString("HoTen"))) {
                                if (resultSetchecksua.getString("VaiTro").equals("1")) {
                                    if (resultSetchecksua.getString("VaiTro").equals(resultSetchecksua1.getString("VaiTro"))) {
                                        JOptionPane.showMessageDialog(this, "Chỉ được sủa chính mình và nhân viên !");
                                        return;
                                    }
                                }
                                if (resultSetchecksua.getString("VaiTro").equals("0")) {
                                    if (resultSetchecksua.getString("VaiTro").equals(resultSetchecksua1.getString("VaiTro"))) {
                                        JOptionPane.showMessageDialog(this, "Bạn là nhân viên chỉ được sủa chính mình !");
                                        return;
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Bạn là nhân viên chỉ được sửa chính mình !");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }

                try {
                    Connection c = JDBCUtil.getConnection();
                    PreparedStatement st = c.prepareStatement("select MatKhau from NhanVien where MaNV = ?");
                    st.setString(1, txtmanv.getText());
                    ResultSet ktramk = st.executeQuery();
                    while (ktramk.next()) {
                        if (txtmk1.getText().trim().equals(ktramk.getString("MatKhau"))) {
                            Connection connection = JDBCUtil.getConnection();
                            String sql = "UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, txtmk1.getText());
                            statement.setString(2, txthoten.getText());
                            int vt = -1;
                            if (chktruongphong.isSelected()) {
                                vt = 1;
                            }
                            if (chknhanvien.isSelected()) {
                                vt = 0;
                            }
                            statement.setInt(3, vt);
                            statement.setString(4, txtmanv.getText());
                            int kq = statement.executeUpdate();
                            if (kq > 0) {
                                JOptionPane.showMessageDialog(null, "Sửa thành công !");
                                fill();
                                reset();
                            } else {
                                JOptionPane.showMessageDialog(null, "Sửa không thành công !");
                            }
                        } else {
                            Connection connection = JDBCUtil.getConnection();
                            String sql = "UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, MaHoa.MaHoaMK(txtmk1.getText()));
                            statement.setString(2, txthoten.getText());
                            int vt = -1;
                            if (chktruongphong.isSelected()) {
                                vt = 1;
                            }
                            if (chknhanvien.isSelected()) {
                                vt = 0;
                            }
                            statement.setInt(3, vt);
                            statement.setString(4, txtmanv.getText());
                            int kq = statement.executeUpdate();
                            if (kq > 0) {
                                JOptionPane.showMessageDialog(null, "Sửa thành công !");
                                fill();
                                reset();
                            } else {
                                JOptionPane.showMessageDialog(null, "Sửa không thành công !");
                            }
                        }
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Err !");
                    return;
                }

            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu không hợp lệ !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin !");
            return;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select MaNV, VaiTro from NhanVien where HoTen = ?");
            st.setString(1, vtro.getText());
            ResultSet kxcm = st.executeQuery();
            while (kxcm.next()) {
                if (kxcm.getString("MaNV").equals(txtmanv.getText())) {
                    JOptionPane.showMessageDialog(this, "Không thể xóa chính mình !");
                    return;
                }
            }
        } catch (Exception e) {
        }

        try {
            Connection cns1 = JDBCUtil.getConnection();
            PreparedStatement stm1 = cns1.prepareStatement("select VaiTro from NhanVien where HoTen = ?");
            stm1.setString(1, vtro.getText());
            ResultSet checkxoa1 = stm1.executeQuery();
            while (checkxoa1.next()) {
                if (checkxoa1.getString("VaiTro").equals("0")) {
                    JOptionPane.showMessageDialog(this, "Bạn là nhân viên, không có quyền xóa !");
                    return;
                }
            }
        } catch (Exception e) {
        }

        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement stm = cns.prepareStatement("select VaiTro from NhanVien where HoTen = ?");
            stm.setString(1, vtro.getText());
            ResultSet checkdelete = stm.executeQuery();
            while (checkdelete.next()) {
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stmm = cnss.prepareStatement("select VaiTro from NhanVien where MaNV = ?");
                stmm.setString(1, txtmanv.getText());
                ResultSet checkdelete1 = stmm.executeQuery();
                while (checkdelete1.next()) {
                    if (checkdelete.getString("VaiTro").equals(checkdelete1.getString("VaiTro"))) {
                        JOptionPane.showMessageDialog(this, "Chỉ được xóa nhân viên , không được xóa cùng cấp !");
                        return;
                    } else {
                        Connection c = JDBCUtil.getConnection();
                        PreparedStatement st = c.prepareStatement("DELETE FROM NhanVien WHERE MaNV=?");
                        st.setString(1, txtmanv.getText());
                        int kq = st.executeUpdate();
                        if (kq > 0) {
                            JOptionPane.showMessageDialog(null, "Xóa thành công !");
                            fill();
                            reset();
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa không thành công !");
                        }
                    }
                }
            }
        } catch (SQLException eee) {
            eee.printStackTrace();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnprintpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintpdfActionPerformed
        MessageFormat header = new MessageFormat("Danh sách trưởng phòng và nhân viên");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tabledsnhanvien.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnprintpdfActionPerformed

    public void Excel(JTable table, File file) {
        try {
            HSSFWorkbook fWorkbook = new HSSFWorkbook();
            HSSFSheet fSheet = fWorkbook.createSheet();
            HSSFRow fRow = fSheet.createRow((short) 0);
            TableModel m = table.getModel();
            for (int i = 0; i < m.getColumnCount(); i++) {
                if (i == 2) {
                    continue;
                }
                fRow.createCell((short) i).setCellValue(m.getColumnName(i));
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                HSSFRow row = fSheet.createRow((short) i + 1);
                for (int j = 0; j < m.getColumnCount(); j++) {
                    if (j == 2) {
                        continue;
                    }
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
    private void btnprintexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintexcelActionPerformed
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getName();
            String path = chooser.getSelectedFile().getParentFile().getPath();
            String file = Paths.get(path, name + ".xls").toString();
            Excel(tabledsnhanvien, new File(file));
        }

    }//GEN-LAST:event_btnprintexcelActionPerformed

    private void btnqrcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrcodeActionPerformed
        try {
            String sothutu = tabledsnhanvien.getValueAt(index, 0).toString();
            String ma = tabledsnhanvien.getValueAt(index, 1).toString();
            String hten = tabledsnhanvien.getValueAt(index, 3).toString();
            String vaitroo = tabledsnhanvien.getValueAt(index, 4).toString();
            String data = "STT: " + sothutu + "\nMa: " + ma + " \nHoTen: " + hten + "\nVaiTro: " + vaitroo;
            String patch = "C:\\Users\\DELL\\Downloads\\Hinh\\qr.png";
            BitMatrix mattrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(mattrix, "png", Paths.get(patch));
            new viewqr().setVisible(true);
        } catch (Exception e) {
            System.out.println("Err");
        }

    }//GEN-LAST:event_btnqrcodeActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnprintexcel;
    private javax.swing.JButton btnprintpdf;
    private javax.swing.JButton btnqrcode;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    public javax.swing.JRadioButton chknhanvien;
    public javax.swing.JRadioButton chktruongphong;
    private javax.swing.JButton cuoi;
    private javax.swing.JButton dau;
    private javax.swing.ButtonGroup grnhanvien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton lui;
    private javax.swing.JTable tabledsnhanvien;
    private javax.swing.JButton tien;
    public javax.swing.JTextField txthoten;
    public javax.swing.JTextField txtmanv;
    public javax.swing.JPasswordField txtmk1;
    public javax.swing.JPasswordField txtmk2;
    private javax.swing.JLabel txttong;
    private javax.swing.JLabel vtro;
    // End of variables declaration//GEN-END:variables
}
