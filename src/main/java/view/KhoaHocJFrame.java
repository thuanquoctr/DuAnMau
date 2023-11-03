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
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.DefaultComboBoxModel;
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
public class KhoaHocJFrame extends javax.swing.JFrame {

    /**
     * Creates new form KhoaHocJFrame
     */
    private int index;
    private int sttbd = 1;

    public KhoaHocJFrame(String vtdn) {
        initComponents();
        setTitle("Quản Lý Khóa Học");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fillcd();
        fill();
        btnxoa.setEnabled(false);
        btnsua.setEnabled(false);
        btnthem.setEnabled(false);
        vtrodangnhap.setText(vtdn);
        btnhocvien.setVisible(false);
        btnqr.setEnabled(false);
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

    public void fill() {
        DefaultTableModel model = (DefaultTableModel) tablekhoahoc.getModel();
        model.setRowCount(0);
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement st = connection.createStatement();
            String sql = " SELECT kh.MaKH,cd.TenCD,cd.ThoiLuong,cd.HocPhi,kh.NgayKG,kh.NgayTao,nv.HoTen FROM ChuyenDe as cd join KhoaHoc as kh on cd.MaCD = kh.MaCD join NhanVien as nv on nv.MaNV = kh.MaNV";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String makh = rs.getString("MaKH");
                String cd = rs.getString("TenCD");
                String tl = rs.getString("ThoiLuong");
                String hp = rs.getString("HocPhi");
                String ngaykg = rs.getString("NgayKG");
                String ngaytao = rs.getString("NgayTao");
                String taoboi = rs.getString("HoTen");
                Object[] datanew = {sttbd, makh, cd, tl, hp, ngaykg, taoboi, ngaytao};
                model.addRow(datanew);
                sttbd++;
            }
            sttbd = 1;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void reset() {
        index = 0;
        txtngaykhaigiang.setText("");
        txtghichu.setText("");
        txtnguoitao.setText("");
        txtngaykhaigiang.setText("");
        LocalDate lcd = LocalDate.now();
        txtngaytao.setText(String.valueOf(lcd));
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
        btnhocvien.setVisible(false);

    }

    public boolean checkngaykhaigiang() {
        if (txtngaykhaigiang.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean checkghichu() {
        if (txtghichu.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean valuedate() {
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
        Pattern p = Pattern.compile(regex);
        Matcher mc = p.matcher(txtngaykhaigiang.getText());
        if (!mc.find()) {
            return false;
        }
        return true;
    }

    public boolean sosanh() {
        LocalDate ngaykg = LocalDate.parse(txtngaykhaigiang.getText());
        LocalDate ngayht = LocalDate.parse(txtngaytao.getText());
        if (ngaykg.isBefore(ngayht)) {
            return false;
        } else if (ngaykg.isAfter(ngayht)) {
            return true;
        } else {
            return false;
        }
    }

    public void fillcd() {
        DefaultComboBoxModel modelcd = (DefaultComboBoxModel) cbxcde.getModel();
        try {
            Connection c = JDBCUtil.getConnection();
            Statement st = c.createStatement();
            ResultSet kq = st.executeQuery("select TenCD, HocPhi, ThoiLuong from ChuyenDe");
            while (kq.next()) {
                String cde = kq.getString("TenCD");
                modelcd.addElement(cde);
            }
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement stm = cns.prepareStatement("select HocPhi , ThoiLuong from ChuyenDe where TenCD = ?");
            stm.setString(1, (String) cbxcde.getSelectedItem());
            ResultSet kqq = stm.executeQuery();
            while (kqq.next()) {
                String hp = kqq.getString("HocPhi");
                String tl = kqq.getString("ThoiLuong");
                int hphi = (int) Math.round(Double.valueOf(hp));
                txthocphi.setText(String.valueOf(hphi));
                txtthoiluong.setText(tl);
            }
        } catch (Exception e) {
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

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbxcde = new javax.swing.JComboBox<>();
        txtngaykhaigiang = new javax.swing.JTextField();
        txthocphi = new javax.swing.JTextField();
        txtthoiluong = new javax.swing.JTextField();
        txtnguoitao = new javax.swing.JTextField();
        txtngaytao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtghichu = new javax.swing.JTextArea();
        btnthem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        btnhocvien = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablekhoahoc = new javax.swing.JTable();
        btnpdf = new javax.swing.JButton();
        btnexcel = new javax.swing.JButton();
        btnqr = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        vtrodangnhap = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("QUẢN LÝ KHÓA HỌC");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Chuyên Đề");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Ngày Khai Giảng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Học Phí");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Thời Lượng (Giờ)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Người Tạo");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Ghi Chú");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Ngày Tạo");

        cbxcde.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxcde.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxcdeItemStateChanged(evt);
            }
        });
        cbxcde.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbxcdeMouseEntered(evt);
            }
        });

        txtngaykhaigiang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txthocphi.setEditable(false);
        txthocphi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtthoiluong.setEditable(false);
        txtthoiluong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtnguoitao.setEditable(false);
        txtnguoitao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtngaytao.setEditable(false);
        txtngaytao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtghichu.setColumns(20);
        txtghichu.setRows(20);
        jScrollPane1.setViewportView(txtghichu);

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton5.setText("<<");

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton6.setText("|<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton7.setText(">|");

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton8.setText(">>");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        btnhocvien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnhocvien.setText("Học Viên");
        btnhocvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhocvienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txthocphi, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(cbxcde, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4)))
                                    .addComponent(jLabel7))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtngaykhaigiang)
                                    .addComponent(txtthoiluong)
                                    .addComponent(txtngaytao)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnhocvien, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtnguoitao, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxcde, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(txtngaykhaigiang))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthocphi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtthoiluong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnguoitao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhocvien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cập Nhật", jPanel1);

        tablekhoahoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tablekhoahoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ KHÓA HỌC", "CHUYÊN ĐỀ", "THỜI LƯỢNG", "HỌC PHÍ", "KHAI GIẢNG", "TẠO BỞI", "NGÀY TẠO"
            }
        ));
        tablekhoahoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablekhoahocMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablekhoahocMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tablekhoahoc);

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnqr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnexcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnpdf)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnpdf)
                    .addComponent(btnexcel)
                    .addComponent(btnqr))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách", jPanel2);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Xin Chào :");

        vtrodangnhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        vtrodangnhap.setForeground(new java.awt.Color(255, 0, 0));
        vtrodangnhap.setText("jLabel10");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vtrodangnhap, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(vtrodangnhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        this.dispose();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            new View_Master(vtrodangnhap.getText()).setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowClosed

    private void cbxcdeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxcdeItemStateChanged
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select HocPhi , ThoiLuong from ChuyenDe where TenCD = ?");
            st.setString(1, (String) cbxcde.getSelectedItem());
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String hp = kq.getString("HocPhi");
                String tl = kq.getString("ThoiLuong");
                int hphi = (int) Math.round(Double.valueOf(hp));
                txthocphi.setText(String.valueOf(hphi));
                txtthoiluong.setText(tl);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbxcdeItemStateChanged

    private void cbxcdeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxcdeMouseEntered
    }//GEN-LAST:event_cbxcdeMouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reset();
        btnthem.setEnabled(true);
        txtnguoitao.setText(vtrodangnhap.getText());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

    }//GEN-LAST:event_jButton6ActionPerformed

    private void tablekhoahocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablekhoahocMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablekhoahocMouseEntered

    private void tablekhoahocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablekhoahocMouseClicked
        index = tablekhoahoc.getSelectedRow();
        if (index >= 0) {
            btnxoa.setEnabled(true);
            btnsua.setEnabled(true);
            btnthem.setEnabled(false);
            btnhocvien.setVisible(true);
            btnqr.setEnabled(true);
        }
        try {
            Connection c = JDBCUtil.getConnection();
            Statement st = c.createStatement();
            ResultSet kq = st.executeQuery("select TenCD from ChuyenDe");
            while (kq.next()) {
                String tencd = kq.getString("TenCD");
                txtngaykhaigiang.setText((String) tablekhoahoc.getValueAt(index, 5));
                txtnguoitao.setText((String) tablekhoahoc.getValueAt(index, 6));
                txtngaytao.setText((String) tablekhoahoc.getValueAt(index, 7));
                Connection cns = JDBCUtil.getConnection();
                PreparedStatement stt = cns.prepareStatement("select GhiChu from KhoaHoc as kh join ChuyenDe as cd on cd.MaCD = kh.MaCD where cd.TenCD = ?");
                stt.setString(1, (String) tablekhoahoc.getValueAt(index, 2));
                ResultSet kq1 = stt.executeQuery();
                while (kq1.next()) {
                    txtghichu.setText(kq1.getString("GhiChu"));
                }
                if (tencd.equals(tablekhoahoc.getValueAt(index, 2))) {
                    cbxcde.setSelectedItem(tablekhoahoc.getValueAt(index, 2));
                    break;
                }
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_tablekhoahocMouseClicked

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if (checkngaykhaigiang()) {
            if (checkghichu()) {
                if (valuedate()) {
                    if (sosanh()) {
                        try {
                            Connection cns = JDBCUtil.getConnection();
                            PreparedStatement stm = cns.prepareStatement("select MaCD from ChuyenDe where TenCD = ?");
                            stm.setString(1, (String) cbxcde.getSelectedItem());
                            ResultSet kqmacd = stm.executeQuery();
                            while (kqmacd.next()) {
                                Connection c = JDBCUtil.getConnection();
                                PreparedStatement st = c.prepareStatement("insert into KhoaHoc (MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao) values (?,?,?,?,?,?,?)");
                                st.setString(1, kqmacd.getString("MaCD"));
                                st.setString(2, txthocphi.getText());
                                st.setString(3, txtthoiluong.getText());
                                st.setString(4, txtngaykhaigiang.getText());
                                st.setString(5, txtghichu.getText());
                                Connection cnss = JDBCUtil.getConnection();
                                PreparedStatement sttt = cnss.prepareStatement("select MaNV from NhanVien where HoTen = ?");
                                sttt.setString(1, txtnguoitao.getText());
                                ResultSet kqmanv = sttt.executeQuery();
                                while (kqmanv.next()) {
                                    st.setString(6, kqmanv.getString("MaNV"));
                                }
                                st.setString(7, txtngaytao.getText());
                                int kqthucthi = st.executeUpdate();
                                if (kqthucthi > 0) {
                                    JOptionPane.showMessageDialog(this, "Thêm thành công !");
                                    fill();
                                    txtngaykhaigiang.setText("");
                                    txtnguoitao.setText("");
                                    txtngaytao.setText("");
                                    txtghichu.setText("");
                                    cbxcde.setSelectedIndex(0);
                                    return;
                                } else {
                                    JOptionPane.showMessageDialog(this, "Thêm thất bại !");
                                    return;
                                }
                            }

                        } catch (Exception e) {
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Ngày khai giảng phải sau ngày tạo !");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ngày khi giảng phải đúng định dạng Năm-Tháng-Ngày(####-##-##) !");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ghi chú !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày khai giảng !");
            return;
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        if (index >= 0) {
            if (checkngaykhaigiang()) {
                if (checkghichu()) {
                    if (valuedate()) {
                        if (sosanh()) {
                            try {
                                Connection cns = JDBCUtil.getConnection();
                                PreparedStatement stm = cns.prepareStatement("select MaCD from ChuyenDe where TenCD = ?");
                                stm.setString(1, (String) cbxcde.getSelectedItem());
                                ResultSet kqmacd = stm.executeQuery();
                                while (kqmacd.next()) {
                                    Connection c = JDBCUtil.getConnection();
                                    PreparedStatement st = c.prepareStatement("update KhoaHoc set MaCD = ?,HocPhi = ?,ThoiLuong = ?,NgayKG = ?,GhiChu = ?,MaNV = ?,NgayTao = ? where MaKH = ?");
                                    st.setString(1, kqmacd.getString("MaCD"));
                                    st.setString(2, txthocphi.getText());
                                    st.setString(3, txtthoiluong.getText());
                                    st.setString(4, txtngaykhaigiang.getText());
                                    st.setString(5, txtghichu.getText());
                                    Connection cnss = JDBCUtil.getConnection();
                                    PreparedStatement sttt = cnss.prepareStatement("select MaNV from NhanVien where HoTen = ?");
                                    sttt.setString(1, txtnguoitao.getText());
                                    ResultSet kqmanv = sttt.executeQuery();
                                    while (kqmanv.next()) {
                                        st.setString(6, kqmanv.getString("MaNV"));
                                    }
                                    st.setString(7, txtngaytao.getText());
                                    st.setString(8, (String) tablekhoahoc.getValueAt(index, 1));
                                    int kqthucthi = st.executeUpdate();
                                    if (kqthucthi > 0) {
                                        JOptionPane.showMessageDialog(this, "Sửa thành công !");
                                        fill();
                                        txtngaykhaigiang.setText("");
                                        txtnguoitao.setText("");
                                        txtngaytao.setText("");
                                        txtghichu.setText("");
                                        cbxcde.setSelectedIndex(0);
                                        btnsua.setEnabled(false);
                                        btnxoa.setEnabled(false);
                                        index = 0;
                                        return;
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Sửa thất bại !");
                                        return;
                                    }
                                }

                            } catch (Exception e) {
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Ngày khai giảng phải sau ngày tạo !");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Ngày khi giảng phải đúng định dạng Năm-Tháng-Ngày(####-##-##) !");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập ghi chú !");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày khai giảng !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khóa học muốn sửa !");
            return;
        }

    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        if (index >= 0) {
            try {
                Connection c = JDBCUtil.getConnection();
                PreparedStatement st = c.prepareStatement("delete from KhoaHoc where MaKH = ?");
                st.setString(1, (String) tablekhoahoc.getValueAt(index, 1));
                int kq = st.executeUpdate();
                if (kq > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công !");
                    fill();
                    cbxcde.setSelectedIndex(0);
                    txtngaykhaigiang.setText("");
                    txtnguoitao.setText("");
                    txtngaytao.setText("");
                    txtghichu.setText("");
                    btnsua.setEnabled(false);
                    btnxoa.setEnabled(false);
                    index = 0;
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa không thành công !");
                    return;
                }
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khóa học muốn xóa ! ");
            return;
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnhocvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhocvienActionPerformed
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            HocVienJFrame hv = new HocVienJFrame((String) cbxcde.getSelectedItem());
            hv.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnhocvienActionPerformed

    private void btnpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpdfActionPerformed
        MessageFormat header = new MessageFormat("Danh sách khóa học");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tablekhoahoc.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
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
            Excel(tablekhoahoc, new File(file));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnexcelActionPerformed

    private void btnqrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrActionPerformed
        try {
            String sothutu = tablekhoahoc.getValueAt(index, 0).toString();
            String ma = tablekhoahoc.getValueAt(index, 1).toString();
            String cd = tablekhoahoc.getValueAt(index, 2).toString();
            String tl = tablekhoahoc.getValueAt(index, 3).toString();
            String hp = tablekhoahoc.getValueAt(index, 4).toString();
            String kg = tablekhoahoc.getValueAt(index, 5).toString();
            String tb = tablekhoahoc.getValueAt(index, 6).toString();
            String nt = tablekhoahoc.getValueAt(index, 7).toString();
            String data = "STT: " + sothutu + "\nMaKhoaHoc: " + ma + " \nChuyenDe: " + cd + "\nThoiLuong: " + tl + "\nHocPhi: " + hp + "\nKhaiGiang: " + kg + "\nTaoBoi: " + tb + "\nNgayTao: " + nt;
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
    private javax.swing.JButton btnexcel;
    private javax.swing.JButton btnhocvien;
    private javax.swing.JButton btnpdf;
    private javax.swing.JButton btnqr;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbxcde;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JTable tablekhoahoc;
    private javax.swing.JTextArea txtghichu;
    private javax.swing.JTextField txthocphi;
    private javax.swing.JTextField txtngaykhaigiang;
    private javax.swing.JTextField txtngaytao;
    private javax.swing.JTextField txtnguoitao;
    private javax.swing.JTextField txtthoiluong;
    private javax.swing.JLabel vtrodangnhap;
    // End of variables declaration//GEN-END:variables
}
