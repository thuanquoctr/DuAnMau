/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ThongKeJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ThongKeJFrame
     */
    private int sttnguoihoc = 1;
    private int sttkkhoahoc = 1;
    private int stttonghopdiem = 1;
    private int sttdoanhthu = 1;
    private int indexnguoihoc;
    private int indexbangdiem;
    private int indextonghopdiem;
    private int indexdoanhthu;

    public ThongKeJFrame(int index, String vtro) {
        initComponents();
        setTitle("Tổng Hợp Thống Kê");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jTabbedPane1.setSelectedIndex(index);
        jTabbedPane1.setEnabled(false);
        lblvaitro.setText(vtro);
        seticon();
        fillnguoihoc();
        fillcbxkhoahoc();
        fillkhoahoc();
        filltonghopdiem();
        fillnamthongke();
        filldoanhthu();
        btnqrnh.setEnabled(false);
        btnqrbd.setEnabled(false);
        btnqrthd.setEnabled(false);
        btnqrdt.setEnabled(false);

    }

    public void seticon() {
        ImageIcon icsearch = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\pdf.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        btnprintpdf.setIcon(icsearch);
        ImageIcon icsearchex = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\excel1.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        btnprintxcel.setIcon(icsearchex);

        ImageIcon icsearchbd = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\pdf.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        printpdfbd.setIcon(icsearchbd);
        ImageIcon icsearchexbd = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\excel1.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        printexcelbd.setIcon(icsearchexbd);

        ImageIcon icsearchthd = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\pdf.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        printpdfthd.setIcon(icsearchthd);
        ImageIcon icsearchexthd = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\excel1.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        printexcelthd.setIcon(icsearchexthd);

        ImageIcon icsearchdt = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\pdf.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        printpdfdt.setIcon(icsearchdt);
        ImageIcon icsearchexdt = new ImageIcon(
                new ImageIcon("C:\\Users\\DELL\\Downloads\\Hinh\\excel1.png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        printexceldt.setIcon(icsearchexdt);

    }

    public void fillnguoihoc() {
        DefaultTableModel modelnguoihoc = (DefaultTableModel) tablenguoihoc.getModel();
        modelnguoihoc.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("SELECT DISTINCT year(NgayDK)\n"
                    + "FROM NguoiHoc\n"
                    + "ORDER BY year(NgayDK) DESC;");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String nam = kq.getString("year(NgayDK)");
                PreparedStatement st1 = c.prepareStatement("select count(*) from NguoiHoc where year(NgayDK) = ?");
                st1.setString(1, nam);
                ResultSet kq1 = st1.executeQuery();
                while (kq1.next()) {
                    String count = kq1.getString("count(*)");
                    PreparedStatement st2 = c.prepareStatement("select Min(NgayDK) from NguoiHoc where year(NgayDK) = ?");
                    st2.setString(1, nam);
                    ResultSet kq2 = st2.executeQuery();
                    while (kq2.next()) {
                        String datemin = kq2.getString("Min(NgayDK)");
                        PreparedStatement st3 = c.prepareStatement("select Max(NgayDK) from NguoiHoc where year(NgayDK) = ?");
                        st3.setString(1, nam);
                        ResultSet kq3 = st3.executeQuery();
                        while (kq3.next()) {
                            String datemax = kq3.getString("Max(NgayDK)");
                            Object[] dta = {sttnguoihoc, nam, count, datemin, datemax};
                            modelnguoihoc.addRow(dta);
                        }
                    }
                }
                sttnguoihoc++;
            }
            sttnguoihoc = 1;
        } catch (Exception e) {
        }
    }

    public void fillcbxkhoahoc() {
        DefaultComboBoxModel boxModel = (DefaultComboBoxModel) cbxkhoahoc.getModel();
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select cd.TenCD from KhoaHoc as kh join ChuyenDe as cd where kh.MaCD = cd.MaCD");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                boxModel.addElement(kq.getString("cd.TenCD"));
            }
        } catch (Exception e) {
        }

    }

    public void fillkhoahoc() {
        DefaultTableModel modelkhoahoc = (DefaultTableModel) tablebangdiem.getModel();
        modelkhoahoc.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select nh.MaNH , nh.HoTen, hv.Diem from NguoiHoc as nh join HocVien as hv on nh.MaNH = hv.MaNH join KhoaHoc as kh on hv.MaKH = kh.MaKH where kh.MaKH = ?");
            Connection c1 = JDBCUtil.getConnection();
            PreparedStatement st1 = c1.prepareStatement("select kh.MaKH from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where cd.TenCD = ?");
            st1.setString(1, (String) cbxkhoahoc.getSelectedItem());
            ResultSet kq1 = st1.executeQuery();
            while (kq1.next()) {
                st.setString(1, kq1.getString("kh.MaKH"));
            }
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String manh = kq.getString("nh.MaNH");
                String hovaten = kq.getString("nh.HoTen");
                String diem = kq.getString("hv.Diem");
                Object[] data = {sttkkhoahoc, manh, hovaten, diem, xeploai(Float.parseFloat(diem))};
                modelkhoahoc.addRow(data);
                sttkkhoahoc++;
            }
            sttkkhoahoc = 1;
        } catch (Exception e) {
        }
    }

    public String xeploai(float diem) {
        if (diem < 0) {
            return "Chưa nhập điểm";
        }
        if (diem < 3) {
            return "Kém";
        }
        if (diem < 5) {
            return "Yếu";
        }
        if (diem < 6.5) {
            return "Trung Bình";
        }
        if (diem < 7.5) {
            return "Khá";
        }
        if (diem < 9) {
            return "Giỏi";
        }
        return "Xuất Sắc";
    }

    public void filltonghopdiem() {
        DefaultTableModel modeltonghopdiem = (DefaultTableModel) tabletonghopdiem.getModel();
        modeltonghopdiem.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select cd.TenCD , kh.MaKH from ChuyenDe as cd join KhoaHoc as kh on cd.MaCD = kh.MaCD join HocVien as hv on hv.MaKH = kh.MaKH GROUP BY cd.TenCD, kh.MaKH");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String tencd = kq.getString("cd.TenCD");
                Connection c1 = JDBCUtil.getConnection();
                PreparedStatement st1 = c1.prepareStatement("select count(*) from HocVien where MaKH = ?");
                st1.setString(1, kq.getString("kh.MaKH"));
                ResultSet kq1 = st1.executeQuery();
                while (kq1.next()) {
                    String sl = kq1.getString("count(*)");
                    Connection c2 = JDBCUtil.getConnection();
                    PreparedStatement st2 = c2.prepareStatement("select Max(Diem) from HocVien where MaKH = ?");
                    st2.setString(1, kq.getString("kh.MaKH"));
                    ResultSet kq2 = st2.executeQuery();
                    while (kq2.next()) {
                        String diemmax = kq2.getString("Max(Diem)");
                        Connection c3 = JDBCUtil.getConnection();
                        PreparedStatement st3 = c3.prepareStatement("select Min(Diem) from HocVien where MaKH = ?");
                        st3.setString(1, kq.getString("kh.MaKH"));
                        ResultSet kq3 = st3.executeQuery();
                        while (kq3.next()) {
                            String diemmin = kq3.getString("Min(Diem)");
                            Connection c4 = JDBCUtil.getConnection();
                            PreparedStatement st4 = c4.prepareStatement("select Avg(Diem) from HocVien where MaKH = ?");
                            st4.setString(1, kq.getString("kh.MaKH"));
                            ResultSet kq4 = st4.executeQuery();
                            while (kq4.next()) {
                                String diemtb = kq4.getString("Avg(Diem)");
                                Object[] data = {stttonghopdiem, tencd, sl, diemmax, diemmin, diemtb};
                                modeltonghopdiem.addRow(data);

                            }
                        }
                    }
                }
                stttonghopdiem++;
            }
            stttonghopdiem = 1;

        } catch (Exception e) {
            e.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablenguoihoc = new javax.swing.JTable();
        btnprintpdf = new javax.swing.JButton();
        btnprintxcel = new javax.swing.JButton();
        btnqrnh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxkhoahoc = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablebangdiem = new javax.swing.JTable();
        printpdfbd = new javax.swing.JButton();
        printexcelbd = new javax.swing.JButton();
        btnqrbd = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabletonghopdiem = new javax.swing.JTable();
        printpdfthd = new javax.swing.JButton();
        printexcelthd = new javax.swing.JButton();
        btnqrthd = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbxnamdoanhthu = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabledoanhthu123 = new javax.swing.JTable();
        printpdfdt = new javax.swing.JButton();
        printexceldt = new javax.swing.JButton();
        btnqrdt = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblvaitro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("TỔNG HỢP THỐNG KÊ");

        tablenguoihoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "NĂM", "SỐ NGƯỜI HỌC", "ĐẦU TIÊN", "SAU CÙNG"
            }
        ));
        tablenguoihoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablenguoihocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablenguoihoc);

        btnprintpdf.setText("Pdf");
        btnprintpdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintpdfActionPerformed(evt);
            }
        });

        btnprintxcel.setText("Excel");
        btnprintxcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintxcelActionPerformed(evt);
            }
        });

        btnqrnh.setText("QR Code");
        btnqrnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnqrnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnqrnh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnprintxcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnprintpdf)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnprintpdf)
                    .addComponent(btnprintxcel)
                    .addComponent(btnqrnh))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Người Học", jPanel1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Khóa Học : ");

        cbxkhoahoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxkhoahoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxkhoahocItemStateChanged(evt);
            }
        });

        tablebangdiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tablebangdiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ NGƯỜI HỌC", "HỌ VÀ TÊN", "ĐIỂM", "SẾP LOẠI"
            }
        ));
        tablebangdiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablebangdiemMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablebangdiem);

        printpdfbd.setText("Pdf");
        printpdfbd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printpdfbdActionPerformed(evt);
            }
        });

        printexcelbd.setText("Excel");
        printexcelbd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printexcelbdActionPerformed(evt);
            }
        });

        btnqrbd.setText("QR Code");
        btnqrbd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnqrbdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxkhoahoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnqrbd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printexcelbd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printpdfbd)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxkhoahoc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printpdfbd)
                    .addComponent(printexcelbd)
                    .addComponent(btnqrbd))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bảng Điểm", jPanel2);

        tabletonghopdiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabletonghopdiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "CHUYÊN ĐỀ", "TỔNG SỐ HỌC VIÊN", "CAO NHẤT", "THẤP NHẤT", "ĐIỂM TRUNG BÌNH"
            }
        ));
        tabletonghopdiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabletonghopdiemMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabletonghopdiem);

        printpdfthd.setText("Pdf");
        printpdfthd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printpdfthdActionPerformed(evt);
            }
        });

        printexcelthd.setText("Excel");
        printexcelthd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printexcelthdActionPerformed(evt);
            }
        });

        btnqrthd.setText("QR Code");
        btnqrthd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnqrthdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnqrthd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printexcelthd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printpdfthd)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printpdfthd)
                    .addComponent(printexcelthd)
                    .addComponent(btnqrthd))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Tổng Hợp Điểm", jPanel3);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Năm :");

        cbxnamdoanhthu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tabledoanhthu123.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "CHUYÊN ĐỀ", "SỐ KHÓA", "SỐ HỌC VIÊN", "DOANH THU", "HỌC PHÍ CAO NHẤT", "HỌC PHÍ  THẤP NHẤT", "HỌC PHÍ  TRUNG BÌNH"
            }
        ));
        tabledoanhthu123.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledoanhthu123MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabledoanhthu123);

        printpdfdt.setText("Pdf");
        printpdfdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printpdfdtActionPerformed(evt);
            }
        });

        printexceldt.setText("Excel");
        printexceldt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printexceldtActionPerformed(evt);
            }
        });

        btnqrdt.setText("QR Code");
        btnqrdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnqrdtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxnamdoanhthu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnqrdt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printexceldt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printpdfdt))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 19, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxnamdoanhthu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printpdfdt)
                    .addComponent(printexceldt)
                    .addComponent(btnqrdt))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Doanh Thu", jPanel4);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Xin Chào :");

        lblvaitro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblvaitro.setForeground(new java.awt.Color(0, 0, 204));
        lblvaitro.setText("jLabel5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblvaitro, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(lblvaitro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select VaiTro from NhanVien where HoTen = ?");
            st.setString(1, lblvaitro.getText());
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                if (kq.getString("VaiTro").equals("0")) {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    View_Master mt = new View_Master(lblvaitro.getText());
                    mt.setVisible(true);
                    mt.doanhthu.setEnabled(false);
                } else {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    View_Master mt = new View_Master(lblvaitro.getText());
                    mt.setVisible(true);
                }
            }

        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed
    public void fillnamthongke() {
        DefaultComboBoxModel modeldoanhthu123 = (DefaultComboBoxModel) cbxnamdoanhthu.getModel();
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select distinct year(NgayTao) from KhoaHoc");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                modeldoanhthu123.addElement(kq.getString("year(NgayTao)"));
            }
        } catch (Exception e) {
        }
    }

    public void filldoanhthu() {
        DefaultTableModel modeldoanhthu = (DefaultTableModel) tabledoanhthu123.getModel();
        modeldoanhthu.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select cd.TenCD , kh.MaKH from ChuyenDe as cd join KhoaHoc as kh on cd.MaCD = kh.MaCD join HocVien as hv on hv.MaKH = kh.MaKH where year(NgayTao) = ? GROUP BY cd.TenCD, kh.MaKH");
            st.setString(1, (String) cbxnamdoanhthu.getSelectedItem());
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String tencd = kq.getString("cd.TenCD");
                String makh = kq.getString("kh.MaKH");
                PreparedStatement st1 = c.prepareStatement("select count(*) from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where cd.TenCD = ?");
                st1.setString(1, tencd);
                ResultSet kq1 = st1.executeQuery();
                while (kq1.next()) {
                    String sokhoa = kq1.getString("count(*)");
                    PreparedStatement st2 = c.prepareStatement("select count(*) from HocVien where MaKH = ?");
                    st2.setString(1, makh);
                    ResultSet kq2 = st2.executeQuery();
                    while (kq2.next()) {
                        String sohocvien = kq2.getString("count(*)");
                        PreparedStatement st3 = c.prepareStatement("select Sum(cd.HocPhi) from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where cd.TenCD = ?");
                        st3.setString(1, tencd);
                        ResultSet kq3 = st3.executeQuery();
                        while (kq3.next()) {
                            double doanhthu = kq3.getDouble("Sum(cd.HocPhi)");
                            PreparedStatement st4 = c.prepareStatement("select Max(kh.HocPhi) from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where cd.TenCD = ?");
                            st4.setString(1, tencd);
                            ResultSet kq4 = st4.executeQuery();
                            while (kq4.next()) {
                                double hpmax = kq4.getDouble("Max(kh.HocPhi)");
                                PreparedStatement st5 = c.prepareStatement("select Min(kh.HocPhi) from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where cd.TenCD = ?");
                                st5.setString(1, tencd);
                                ResultSet kq5 = st5.executeQuery();
                                while (kq5.next()) {
                                    double hpmin = kq5.getDouble("Min(kh.HocPhi)");
                                    PreparedStatement st6 = c.prepareStatement("select Avg(kh.HocPhi) from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where cd.TenCD = ?");
                                    st6.setString(1, tencd);
                                    ResultSet kq6 = st6.executeQuery();
                                    while (kq6.next()) {
                                        double hptb = kq6.getDouble("Avg(kh.HocPhi)");
                                        Object[] data = {sttdoanhthu, tencd, sokhoa, sohocvien, Math.round(doanhthu), Math.round(hpmax), Math.round(hpmin), Math.round(hptb)};
                                        modeldoanhthu.addRow(data);
                                    }
                                }
                            }
                        }

                    }
                }
                sttdoanhthu++;
            }
            sttdoanhthu = 1;
        } catch (Exception e) {
        }
    }


    private void cbxkhoahocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxkhoahocItemStateChanged
        fillkhoahoc();        // TODO add your handling code here:
    }//GEN-LAST:event_cbxkhoahocItemStateChanged

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
    private void btnprintxcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintxcelActionPerformed
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getName();
            String path = chooser.getSelectedFile().getParentFile().getPath();
            String file = Paths.get(path, name + ".xls").toString();
            Excel(tablenguoihoc, new File(file));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnprintxcelActionPerformed

    private void btnprintpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintpdfActionPerformed
        MessageFormat header = new MessageFormat("Danh sách người học");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tablenguoihoc.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnprintpdfActionPerformed

    private void printexcelbdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printexcelbdActionPerformed
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getName();
            String path = chooser.getSelectedFile().getParentFile().getPath();
            String file = Paths.get(path, name + ".xls").toString();
            Excel(tablebangdiem, new File(file));
        }         // TODO add your handling code here:
    }//GEN-LAST:event_printexcelbdActionPerformed

    private void printpdfbdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printpdfbdActionPerformed
        MessageFormat header = new MessageFormat("Danh sách bảng điểm");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tablebangdiem.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }         // TODO add your handling code here:
    }//GEN-LAST:event_printpdfbdActionPerformed

    private void printpdfthdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printpdfthdActionPerformed
        MessageFormat header = new MessageFormat("Danh sách tổng hợp điểm");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tabletonghopdiem.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }
     }//GEN-LAST:event_printpdfthdActionPerformed

    private void printexcelthdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printexcelthdActionPerformed
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getName();
            String path = chooser.getSelectedFile().getParentFile().getPath();
            String file = Paths.get(path, name + ".xls").toString();
            Excel(tabletonghopdiem, new File(file));
        }           // TODO add your handling code here:
    }//GEN-LAST:event_printexcelthdActionPerformed

    private void printpdfdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printpdfdtActionPerformed
        MessageFormat header = new MessageFormat("Danh sách doanh thu");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tabledoanhthu123.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_printpdfdtActionPerformed

    private void printexceldtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printexceldtActionPerformed
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getName();
            String path = chooser.getSelectedFile().getParentFile().getPath();
            String file = Paths.get(path, name + ".xls").toString();
            Excel(tabledoanhthu123, new File(file));
        }          // TODO add your handling code here:
    }//GEN-LAST:event_printexceldtActionPerformed

    private void btnqrnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrnhActionPerformed
        try {
            String sothutu = tablenguoihoc.getValueAt(indexnguoihoc, 0).toString();
            String nam = tablenguoihoc.getValueAt(indexnguoihoc, 1).toString();
            String sonh = tablenguoihoc.getValueAt(indexnguoihoc, 2).toString();
            String dautien = tablenguoihoc.getValueAt(indexnguoihoc, 3).toString();
            String saucung = tablenguoihoc.getValueAt(indexnguoihoc, 4).toString();
            String data = "STT: " + sothutu + "\nNam: " + nam + " \nSoNguoiHoc: " + sonh + "\nDauTien: " + dautien + "\nSauCung: " + saucung;
            String patch = "C:\\Users\\DELL\\Downloads\\Hinh\\qr.png";
            BitMatrix mattrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(mattrix, "png", Paths.get(patch));
            new viewqr().setVisible(true);
        } catch (Exception e) {
            System.out.println("Err");
        }       // TODO add your handling code here:
    }//GEN-LAST:event_btnqrnhActionPerformed

    private void tablenguoihocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablenguoihocMouseClicked
        indexnguoihoc = tablenguoihoc.getSelectedRow();
        if (indexnguoihoc >= 0) {
            btnqrnh.setEnabled(true);
        }
    }//GEN-LAST:event_tablenguoihocMouseClicked

    private void btnqrbdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrbdActionPerformed
        try {
            String sothutu = tablebangdiem.getValueAt(indexbangdiem, 0).toString();
            String manh = tablebangdiem.getValueAt(indexbangdiem, 1).toString();
            String hoten = tablebangdiem.getValueAt(indexbangdiem, 2).toString();
            String diem = tablebangdiem.getValueAt(indexbangdiem, 3).toString();
            String xeploai = tablebangdiem.getValueAt(indexbangdiem, 4).toString();
            String data = "STT: " + sothutu + "\nMaNguoiHoc: " + manh + " \nHoTen: " + hoten + "\nDiem: " + diem + "\nXepLoai: " + xeploai;
            String patch = "C:\\Users\\DELL\\Downloads\\Hinh\\qr.png";
            BitMatrix mattrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(mattrix, "png", Paths.get(patch));
            new viewqr().setVisible(true);
        } catch (Exception e) {
            System.out.println("Err");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnqrbdActionPerformed

    private void tablebangdiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablebangdiemMouseClicked
        indexbangdiem = tablebangdiem.getSelectedRow();
        if (indexbangdiem >= 0) {
            btnqrbd.setEnabled(true);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tablebangdiemMouseClicked

    private void btnqrthdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrthdActionPerformed
        try {
            String sothutu = tabletonghopdiem.getValueAt(indextonghopdiem, 0).toString();
            String cd = tabletonghopdiem.getValueAt(indextonghopdiem, 1).toString();
            String tong = tabletonghopdiem.getValueAt(indextonghopdiem, 2).toString();
            String caonhat = tabletonghopdiem.getValueAt(indextonghopdiem, 3).toString();
            String thapnhat = tabletonghopdiem.getValueAt(indextonghopdiem, 4).toString();
            String tb = tabletonghopdiem.getValueAt(indextonghopdiem, 5).toString();
            String data = "STT: " + sothutu + "\nChuyenDe: " + cd + " \nTongHocVien: " + tong + "\nCaoNhat: " + caonhat + "\nThapNhat: " + thapnhat + "\nTrungBinh: " + tb;
            String patch = "C:\\Users\\DELL\\Downloads\\Hinh\\qr.png";
            BitMatrix mattrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(mattrix, "png", Paths.get(patch));
            new viewqr().setVisible(true);
        } catch (Exception e) {
            System.out.println("Err");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnqrthdActionPerformed

    private void btnqrdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrdtActionPerformed
        try {
            String sothutu = tabledoanhthu123.getValueAt(indexdoanhthu, 0).toString();
            String cd = tabledoanhthu123.getValueAt(indexdoanhthu, 1).toString();
            String sokhoa = tabledoanhthu123.getValueAt(indexdoanhthu, 2).toString();
            String sohocvien = tabledoanhthu123.getValueAt(indexdoanhthu, 3).toString();
            String doanhthu = tabledoanhthu123.getValueAt(indexdoanhthu, 4).toString();
            String hpcaonhat = tabledoanhthu123.getValueAt(indexdoanhthu, 5).toString();
            String hpthapnhat = tabledoanhthu123.getValueAt(indexdoanhthu, 6).toString();
            String hptb = tabledoanhthu123.getValueAt(indexdoanhthu, 7).toString();
            String data = "STT: " + sothutu + "\nChuyenDe: " + cd + " \nSoKhoa: " + sokhoa + "\nSoHocVien: " + sohocvien + "\nDoanhThu: " + doanhthu + "\nHocPhiCaoNhat: " + hpcaonhat + "\nHocPhiThapNhat: " + hpthapnhat + "\nHocPhiTrungBinh: " + hptb;
            String patch = "C:\\Users\\DELL\\Downloads\\Hinh\\qr.png";
            BitMatrix mattrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(mattrix, "png", Paths.get(patch));
            new viewqr().setVisible(true);
        } catch (Exception e) {
            System.out.println("Err");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnqrdtActionPerformed

    private void tabletonghopdiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabletonghopdiemMouseClicked
        indextonghopdiem = tabletonghopdiem.getSelectedRow();
        if (indextonghopdiem >= 0) {
            btnqrthd.setEnabled(true);
        }          // TODO add your handling code here:
    }//GEN-LAST:event_tabletonghopdiemMouseClicked

    private void tabledoanhthu123MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledoanhthu123MouseClicked
        indexdoanhthu = tabledoanhthu123.getSelectedRow();
        if (indexdoanhthu >= 0) {
            btnqrdt.setEnabled(true);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tabledoanhthu123MouseClicked

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnprintpdf;
    private javax.swing.JButton btnprintxcel;
    private javax.swing.JButton btnqrbd;
    private javax.swing.JButton btnqrdt;
    private javax.swing.JButton btnqrnh;
    private javax.swing.JButton btnqrthd;
    private javax.swing.JComboBox<String> cbxkhoahoc;
    private javax.swing.JComboBox<String> cbxnamdoanhthu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblvaitro;
    private javax.swing.JButton printexcelbd;
    private javax.swing.JButton printexceldt;
    private javax.swing.JButton printexcelthd;
    private javax.swing.JButton printpdfbd;
    private javax.swing.JButton printpdfdt;
    private javax.swing.JButton printpdfthd;
    private javax.swing.JTable tablebangdiem;
    private javax.swing.JTable tabledoanhthu123;
    private javax.swing.JTable tablenguoihoc;
    private javax.swing.JTable tabletonghopdiem;
    // End of variables declaration//GEN-END:variables
}
