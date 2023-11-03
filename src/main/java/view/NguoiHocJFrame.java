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
import java.sql.Statement;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class NguoiHocJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NguoiHocJFrame
     */
    private int stt = 1;
    private int stttim = 1;
    private int index;

    public NguoiHocJFrame(String vt) {
        initComponents();
        setTitle("Quản Lý Người Học");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vtro.setText(vt);
        btnthem.setEnabled(false);
        btnxoa.setEnabled(false);
        btnsua.setEnabled(false);
        btnqr.setEnabled(false);
        t1.setVisible(false);
        txtkqtim.setVisible(false);
        t2.setVisible(false);
        fill();
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

    public void tim() {
        DefaultTableModel model = (DefaultTableModel) tablenguoihoc.getModel();
        model.setRowCount(0);
        try {
            if (txttim.getText().trim().equals("")) {
                fill();
                t1.setVisible(false);
                txtkqtim.setVisible(false);
                t2.setVisible(false);
                return;
            }
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select * from NguoiHoc where HoTen like ?");
            String bientim = "%" + txttim.getText() + "%";
            st.setString(1, bientim);
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                t1.setVisible(true);
                txtkqtim.setVisible(true);
                t2.setVisible(true);
                String manh = kq.getString("MaNH");
                String ht = kq.getString("HoTen");
                String ns = kq.getString("NgaySinh");
                String gt = kq.getString("GioiTinh");
                String dt = kq.getString("DienThoai");
                String mail = kq.getString("Email");
                String manv = kq.getString("MaNV");
                String ngaydk = kq.getString("NgayDK");
                Object[] datatim = {stttim, manh, ht, gt, ns, dt, mail, manv, ngaydk};
                model.addRow(datatim);
                stttim++;
                txtkqtim.setText(tablenguoihoc.getRowCount() + "");
            }
            stttim = 1;
        } catch (Exception e) {
        }
    }

    public void tong() {
        try {
            Connection c = JDBCUtil.getConnection();
            Statement st = c.createStatement();
            ResultSet kq = st.executeQuery("select count(*) from NguoiHoc");
            while (kq.next()) {
                txttong.setText(kq.getString("count(*)"));
            }
        } catch (Exception e) {
        }
    }

    public void fill() {
        DefaultTableModel model = (DefaultTableModel) tablenguoihoc.getModel();
        model.setRowCount(0);
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement stm = cns.prepareStatement("select MaNH,HoTen,GioiTinh,NgaySinh,DienThoai,Email,MaNV,NgayDK from NguoiHoc ");
            ResultSet kq = stm.executeQuery();
            while (kq.next()) {
                String manh = kq.getString("MaNH");
                String ht = kq.getString("HoTen");
                int gt = kq.getInt("GioiTinh");
                String gtinh = "";
                if (gt == 1) {
                    gtinh = "Nam";
                }
                if (gt == 0) {
                    gtinh = "Nữ";
                }
                String ns = kq.getString("NgaySinh");
                String sdt = kq.getString("DienThoai");
                String mail = kq.getString("Email");
                String manv = kq.getString("MaNV");
                String ngaydk = kq.getString("NgayDK");
                Object[] datafill = {stt, manh, ht, gtinh, ns, sdt, mail, manv, ngaydk};
                model.addRow(datafill);
                stt++;
                tong();
            }
            stt = 1;
        } catch (Exception e) {
        }
    }

    public void reset() {
        index = -1;
        txtmanh.setText("");
        txthovaten.setText("");
        cbxgt.setSelectedIndex(0);
        txtngaysinh.setText("");
        txtdienthoai.setText("");
        txtmail.setText("");
        txtghichu.setText("");
        btnxoa.setEnabled(false);
        btnsua.setEnabled(false);
        txtmanh.requestFocus();
    }

    public boolean checknull() {
        if (txtmanh.getText().trim().equals("")) {
            return false;
        }
        if (txthovaten.getText().trim().equals("")) {
            return false;
        }
        if (txtngaysinh.getText().trim().equals("")) {
            return false;
        }
        if (txtdienthoai.getText().trim().equals("")) {
            return false;
        }
        if (txtmail.getText().trim().equals("")) {
            return false;
        }
        if (txtghichu.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean valuedate() {
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
        Pattern p = Pattern.compile(regex);
        Matcher mc = p.matcher(txtngaysinh.getText());
        if (!mc.find()) {
            return false;
        }
        return true;
    }

//    public boolean sosanh() {
//        LocalDate ngaykg = LocalDate.parse(txtngaykhaigiang.getText());
//        LocalDate ngayht = LocalDate.parse(txtngaytao.getText());
//        if (ngaykg.isBefore(ngayht)) {
//            return false;
//        } else if (ngaykg.isAfter(ngayht)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    public boolean regexemail() {
        String regex = "^[a-zA-Z]+[a-zA-Z0-9]*@{1}[a-zA-Z]+mail.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txtmail.getText());
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public boolean regexsdt() {
        String regex = "^0[983]{1}\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txtdienthoai.getText());
        if (!matcher.find()) {
            return false;
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

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtmanh = new javax.swing.JTextField();
        txthovaten = new javax.swing.JTextField();
        cbxgt = new javax.swing.JComboBox<>();
        txtngaysinh = new javax.swing.JTextField();
        txtdienthoai = new javax.swing.JTextField();
        txtmail = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtghichu = new javax.swing.JTextArea();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnmoi = new javax.swing.JButton();
        btndau = new javax.swing.JButton();
        lui = new javax.swing.JButton();
        toi = new javax.swing.JButton();
        btncuoi = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        vtro = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txttim = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablenguoihoc = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txttong = new javax.swing.JLabel();
        t1 = new javax.swing.JLabel();
        t2 = new javax.swing.JLabel();
        txtkqtim = new javax.swing.JLabel();
        btnpdf = new javax.swing.JButton();
        btnexcel = new javax.swing.JButton();
        btnqr = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Quản Lý Người Học");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Họ Và Tên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Giới Tính");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Mã Người Học");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Điện Thoại");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Ngày Sinh");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Địa Chỉ Email");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Ghi Chú");

        txtmanh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txthovaten.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cbxgt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxgt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        txtngaysinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtdienthoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtghichu.setColumns(20);
        txtghichu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtghichu.setRows(20);
        jScrollPane2.setViewportView(txtghichu);

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

        btndau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btndau.setText("|<");
        btndau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndauActionPerformed(evt);
            }
        });

        lui.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lui.setText("<<");
        lui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luiActionPerformed(evt);
            }
        });

        toi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        toi.setText(">>");
        toi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toiActionPerformed(evt);
            }
        });

        btncuoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btncuoi.setText(">|");
        btncuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncuoiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Xin Chào :");

        vtro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        vtro.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(txthovaten, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtmanh)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(230, 230, 230)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxgt, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtngaysinh)
                                    .addComponent(txtmail))
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnthem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnxoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnsua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnmoi)
                                .addGap(85, 85, 85)
                                .addComponent(btndau)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lui)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toi)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btncuoi)
                            .addComponent(vtro, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmanh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxgt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndau, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lui, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(vtro))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cập Nhật", jPanel1);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Tìm Kiếm Theo Tên ");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txttim.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txttim.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txttimCaretUpdate(evt);
            }
        });
        txttim.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txttimInputMethodTextChanged(evt);
            }
        });
        txttim.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txttimPropertyChange(evt);
            }
        });
        txttim.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txttimVetoableChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tablenguoihoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ NGƯỜI HỌC", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL", "MÃ NHÂN VIÊN", "NGÀY ĐĂNG KÝ"
            }
        ));
        tablenguoihoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablenguoihocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablenguoihoc);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Tổng số người học :");

        txttong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        t1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        t1.setText("Đã Tìm Kiếm Ra :");

        t2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        t2.setText("Bạn");

        txtkqtim.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtkqtim.setForeground(new java.awt.Color(255, 0, 51));

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
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttong, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(t1)
                        .addGap(18, 18, 18)
                        .addComponent(txtkqtim)
                        .addGap(18, 18, 18)
                        .addComponent(t2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnqr)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnexcel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnpdf))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txttong)
                    .addComponent(t1)
                    .addComponent(t2)
                    .addComponent(txtkqtim))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnpdf)
                    .addComponent(btnexcel)
                    .addComponent(btnqr))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
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
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        if (checknull()) {
            if (regexsdt()) {
                if (regexemail()) {
                    if (valuedate()) {
                        if (txtmanh.getText().trim().length() < 7) {
                            try {
                                Connection c = JDBCUtil.getConnection();
                                PreparedStatement st = c.prepareStatement("update NguoiHoc set HoTen = ?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=?,NgayDK=? where MaNH = ?");

                                st.setString(1, txthovaten.getText());
                                st.setString(2, txtngaysinh.getText());
                                if (cbxgt.getSelectedIndex() == 0) {
                                    int gt = 1;
                                    st.setInt(3, gt);
                                } else {
                                    int gt = 0;
                                    st.setInt(3, gt);
                                }

                                st.setString(4, txtdienthoai.getText());
                                st.setString(5, txtmail.getText());
                                st.setString(6, txtghichu.getText());
                                Connection cnsmanv = JDBCUtil.getConnection();
                                PreparedStatement stmmanv = cnsmanv.prepareStatement("select MaNV from NhanVien where HoTen = ?");
                                stmmanv.setString(1, vtro.getText());
                                ResultSet kqmanv = stmmanv.executeQuery();
                                while (kqmanv.next()) {
                                    st.setString(7, kqmanv.getString("MaNV"));
                                }
                                LocalDate ngayht = LocalDate.now();
                                st.setString(8, String.valueOf(ngayht));
                                st.setString(9, txtmanh.getText());
                                int thucthi = st.executeUpdate();
                                if (thucthi > 0) {
                                    JOptionPane.showMessageDialog(this, "Sửa thành công !");
                                    fill();
                                    reset();
                                    btnthem.setEnabled(false);
                                    return;
                                } else {
                                    JOptionPane.showMessageDialog(this, "Sửa thất bại !");
                                    return;
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Mã người học tối đa 7 kí tự !");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng ghi Ngày Sinh hợp lệ !");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng ghi Mail hợp lệ !");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng ghi số điện thoại hợp lệ !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin không được để trống !");
            return;
        }
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("delete from NguoiHoc where MaNH = ?");
            st.setString(1, txtmanh.getText());
            int tt = st.executeUpdate();
            if (tt > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công !");
                fill();
                reset();
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Xóa không thành công !");
                return;
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
        reset();
        btnthem.setEnabled(true);
    }//GEN-LAST:event_btnmoiActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        if (checknull()) {
            if (regexsdt()) {
                if (regexemail()) {
                    if (valuedate()) {
                        if (txtmanh.getText().trim().length() < 7) {
                            try {
                                Connection c = JDBCUtil.getConnection();
                                PreparedStatement st = c.prepareStatement("insert into NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV,NgayDK) values (?,?,?,?,?,?,?,?,?)");
                                st.setString(1, txtmanh.getText());
                                st.setString(2, txthovaten.getText());
                                st.setString(3, txtngaysinh.getText());
                                if (cbxgt.getSelectedIndex() == 0) {
                                    int gt = 1;
                                    st.setInt(4, gt);
                                } else {
                                    int gt = 0;
                                    st.setInt(4, gt);
                                }

                                st.setString(5, txtdienthoai.getText());
                                st.setString(6, txtmail.getText());
                                st.setString(7, txtghichu.getText());
                                Connection cnsmanv = JDBCUtil.getConnection();
                                PreparedStatement stmmanv = cnsmanv.prepareStatement("select MaNV from NhanVien where HoTen = ?");
                                stmmanv.setString(1, vtro.getText());
                                ResultSet kqmanv = stmmanv.executeQuery();
                                while (kqmanv.next()) {
                                    st.setString(8, kqmanv.getString("MaNV"));
                                }
                                LocalDate ngayht = LocalDate.now();
                                st.setString(9, String.valueOf(ngayht));
                                int thucthi = st.executeUpdate();
                                if (thucthi > 0) {
                                    JOptionPane.showMessageDialog(this, "Thêm thành công !");
                                    fill();
                                    reset();
                                    btnthem.setEnabled(false);
                                    return;
                                } else {
                                    JOptionPane.showMessageDialog(this, "Thêm thất bại !");
                                    return;
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Mã người học tối đa 7 kí tự !");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng ghi Ngày Sinh hợp lệ !");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng ghi Mail hợp lệ !");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng ghi số điện thoại hợp lệ !");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin không được để trống !");
            return;
        }

    }//GEN-LAST:event_btnthemActionPerformed

    private void tablenguoihocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablenguoihocMouseClicked
        // TODO add your handling code here:
        try {
            index = tablenguoihoc.getSelectedRow();
            if (index >= 0) {
                btnxoa.setEnabled(true);
                btnsua.setEnabled(true);
                btnqr.setEnabled(true);
            }
            if (index >= 0) {
                btnthem.setEnabled(false);
            }
            String ma = tablenguoihoc.getValueAt(index, 1).toString();
            String ht = tablenguoihoc.getValueAt(index, 2).toString();
            String ns = tablenguoihoc.getValueAt(index, 4).toString();
            String dt = tablenguoihoc.getValueAt(index, 5).toString();
            String mail = tablenguoihoc.getValueAt(index, 6).toString();
            cbxgt.setSelectedItem(tablenguoihoc.getValueAt(index, 3).toString());
            txtmanh.setText(ma);
            txtmanh.setEnabled(false);
            txthovaten.setText(ht);
            txtngaysinh.setText(ns);
            txtdienthoai.setText(dt);
            txtmail.setText(mail);
            Connection cgc = JDBCUtil.getConnection();
            PreparedStatement stmgc = cgc.prepareStatement("select GhiChu from NguoiHoc where MaNH = ?");
            stmgc.setString(1, (String) tablenguoihoc.getValueAt(index, 1).toString());
            ResultSet kqgc = stmgc.executeQuery();
            while (kqgc.next()) {
                txtghichu.setText(kqgc.getString("GhiChu"));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tablenguoihocMouseClicked

    private void btndauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndauActionPerformed
        try {
            index = 0;
            if (index >= 0) {
                btnxoa.setEnabled(true);
                btnsua.setEnabled(true);
                btnthem.setEnabled(false);
            }
            tablenguoihoc.setRowSelectionInterval(index, index);
            txtmanh.setText((String) tablenguoihoc.getValueAt(index, 1));
            txthovaten.setText((String) tablenguoihoc.getValueAt(index, 2));
            cbxgt.setSelectedItem(tablenguoihoc.getValueAt(index, 3));
            txtngaysinh.setText((String) tablenguoihoc.getValueAt(index, 4));
            txtdienthoai.setText((String) tablenguoihoc.getValueAt(index, 5));
            txtmail.setText((String) tablenguoihoc.getValueAt(index, 6));
            Connection cnss = JDBCUtil.getConnection();
            PreparedStatement stt = cnss.prepareStatement("select GhiChu from NguoiHoc where MaNH = ?");
            stt.setString(1, (String) tablenguoihoc.getValueAt(index, 1));
            ResultSet kqq = stt.executeQuery();
            while (kqq.next()) {
                txtghichu.setText(kqq.getString("GhiChu"));
            }

        } catch (Exception e) {
        }

    }//GEN-LAST:event_btndauActionPerformed

    private void btncuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncuoiActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from NguoiHoc");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                index = Integer.parseInt(kq.getString("count(*)")) - 1;
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                }
                tablenguoihoc.setRowSelectionInterval(index, index);
                txtmanh.setText((String) tablenguoihoc.getValueAt(index, 1));
                txthovaten.setText((String) tablenguoihoc.getValueAt(index, 2));
                cbxgt.setSelectedItem(tablenguoihoc.getValueAt(index, 3));
                txtngaysinh.setText((String) tablenguoihoc.getValueAt(index, 4));
                txtdienthoai.setText((String) tablenguoihoc.getValueAt(index, 5));
                txtmail.setText((String) tablenguoihoc.getValueAt(index, 6));
                Connection cnss = JDBCUtil.getConnection();
                PreparedStatement stt = cnss.prepareStatement("select GhiChu from NguoiHoc where MaNH = ?");
                stt.setString(1, (String) tablenguoihoc.getValueAt(index, 1));
                ResultSet kqq = stt.executeQuery();
                while (kqq.next()) {
                    txtghichu.setText(kqq.getString("GhiChu"));
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btncuoiActionPerformed

    private void toiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toiActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from NguoiHoc");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int leng = Integer.parseInt(kq.getString("count(*)")) - 1;
                index = tablenguoihoc.getSelectedRow() + 1;
                if (index < 0) {
                    index = 0;
                }
                if (index > leng) {
                    index = 0;
                }
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                    tablenguoihoc.setRowSelectionInterval(index, index);
                    txtmanh.setText((String) tablenguoihoc.getValueAt(index, 1));
                    txthovaten.setText((String) tablenguoihoc.getValueAt(index, 2));
                    cbxgt.setSelectedItem(tablenguoihoc.getValueAt(index, 3));
                    txtngaysinh.setText((String) tablenguoihoc.getValueAt(index, 4));
                    txtdienthoai.setText((String) tablenguoihoc.getValueAt(index, 5));
                    txtmail.setText((String) tablenguoihoc.getValueAt(index, 6));
                    Connection cnss = JDBCUtil.getConnection();
                    PreparedStatement stt = cnss.prepareStatement("select GhiChu from NguoiHoc where MaNH = ?");
                    stt.setString(1, (String) tablenguoihoc.getValueAt(index, 1));
                    ResultSet kqq = stt.executeQuery();
                    while (kqq.next()) {
                        txtghichu.setText(kqq.getString("GhiChu"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_toiActionPerformed

    private void luiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luiActionPerformed
        try {
            Connection cns = JDBCUtil.getConnection();
            PreparedStatement st = cns.prepareStatement("select count(*) from NguoiHoc");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                int leng = Integer.parseInt(kq.getString("count(*)")) - 1;
                index = tablenguoihoc.getSelectedRow() - 1;

                if (index < 0) {
                    index = leng;
                }
                if (index >= 0) {
                    btnxoa.setEnabled(true);
                    btnsua.setEnabled(true);
                    btnthem.setEnabled(false);
                    tablenguoihoc.setRowSelectionInterval(index, index);
                    txtmanh.setText((String) tablenguoihoc.getValueAt(index, 1));
                    txthovaten.setText((String) tablenguoihoc.getValueAt(index, 2));
                    cbxgt.setSelectedItem(tablenguoihoc.getValueAt(index, 3));
                    txtngaysinh.setText((String) tablenguoihoc.getValueAt(index, 4));
                    txtdienthoai.setText((String) tablenguoihoc.getValueAt(index, 5));
                    txtmail.setText((String) tablenguoihoc.getValueAt(index, 6));
                    Connection cnss = JDBCUtil.getConnection();
                    PreparedStatement stt = cnss.prepareStatement("select GhiChu from NguoiHoc where MaNH = ?");
                    stt.setString(1, (String) tablenguoihoc.getValueAt(index, 1));
                    ResultSet kqq = stt.executeQuery();
                    while (kqq.next()) {
                        txtghichu.setText(kqq.getString("GhiChu"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_luiActionPerformed

    private void txttimPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txttimPropertyChange
    }//GEN-LAST:event_txttimPropertyChange

    private void txttimVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txttimVetoableChange
    }//GEN-LAST:event_txttimVetoableChange

    private void txttimInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txttimInputMethodTextChanged
        System.out.println("view.NguoiHocJFrame.txttimInputMethodTextChanged()");        // TODO add your handling code here:
    }//GEN-LAST:event_txttimInputMethodTextChanged

    private void txttimCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txttimCaretUpdate
        tim();        // TODO add your handling code here:
    }//GEN-LAST:event_txttimCaretUpdate

    private void btnpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpdfActionPerformed
        MessageFormat header = new MessageFormat("Danh sách người học");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tablenguoihoc.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
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
            Excel(tablenguoihoc, new File(file));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnexcelActionPerformed

    private void btnqrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnqrActionPerformed
        try {
            String sothutu = tablenguoihoc.getValueAt(index, 0).toString();
            String ma = tablenguoihoc.getValueAt(index, 1).toString();
            String ht = tablenguoihoc.getValueAt(index, 2).toString();
            String gt = tablenguoihoc.getValueAt(index, 3).toString();
            String ns = tablenguoihoc.getValueAt(index, 4).toString();
            String dt = tablenguoihoc.getValueAt(index, 5).toString();
            String em = tablenguoihoc.getValueAt(index, 6).toString();
            String manv = tablenguoihoc.getValueAt(index, 7).toString();
            String ndk = tablenguoihoc.getValueAt(index, 8).toString();
            String data = "STT: " + sothutu + "\nMaNguoiHoc: " + ma + " \nHoTen: " + ht + "\nGioiTinh: " + gt + "\nNgaySinh: " + ns + "\nDienThoai: " + dt + "\nEMail: " + em + "\nMaNhanVien: " + manv + "\nNgayDangKy: " + ndk;
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
    private javax.swing.JButton btncuoi;
    private javax.swing.JButton btndau;
    private javax.swing.JButton btnexcel;
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnpdf;
    private javax.swing.JButton btnqr;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbxgt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton lui;
    private javax.swing.JLabel t1;
    private javax.swing.JLabel t2;
    private javax.swing.JTable tablenguoihoc;
    private javax.swing.JButton toi;
    private javax.swing.JTextField txtdienthoai;
    private javax.swing.JTextArea txtghichu;
    private javax.swing.JTextField txthovaten;
    private javax.swing.JLabel txtkqtim;
    private javax.swing.JTextField txtmail;
    private javax.swing.JTextField txtmanh;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txttim;
    private javax.swing.JLabel txttong;
    private javax.swing.JLabel vtro;
    // End of variables declaration//GEN-END:variables
}
