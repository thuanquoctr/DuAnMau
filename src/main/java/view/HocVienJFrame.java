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
import java.sql.Statement;
import java.text.MessageFormat;
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
public class HocVienJFrame extends javax.swing.JFrame {

    /**
     * Creates new form HocVienJFrame
     */
    private String cde = "";
    private int stt = 1;
    private int sttchuadiem = 1;

    public HocVienJFrame(String cd) {
        initComponents();
        setTitle("Quản Lý Học Viên Của Khóa Học");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checktatca.setSelected(true);
        fillhocvien();
        cde = cd;
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

    public void fill() {
        DefaultTableModel model = (DefaultTableModel) tablehv.getModel();
        model.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select hv.MaHV , hv.MaNH , nh.HoTen , hv.Diem from HocVien as hv join NguoiHoc as nh on hv.MaNH = nh.MaNH where hv.MaKH = ?");
            Connection c1 = JDBCUtil.getConnection();
            PreparedStatement st1 = c1.prepareStatement("select MaKH from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where TenCD = ?");
            st1.setString(1, cde);
            ResultSet kq1 = st1.executeQuery();
            while (kq1.next()) {
                st.setString(1, kq1.getString("MaKH"));
            }
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String mahv = kq.getString("hv.MaHV");
                String manh = kq.getString("hv.MaNH");
                String hoten = kq.getString("nh.HoTen");
                String diem = kq.getString("hv.Diem");
                Object[] data = {stt, mahv, manh, hoten, diem};
                model.addRow(data);
                stt++;
            }
            stt = 1;
        } catch (Exception e) {
        }
    }

    public void fillhocvien() {
        DefaultComboBoxModel modelcd = (DefaultComboBoxModel) cbxhocvienkhac.getModel();
        try {
            Connection c = JDBCUtil.getConnection();
            Statement st = c.createStatement();
            ResultSet kq = st.executeQuery("select HoTen from NguoiHoc");
            while (kq.next()) {
                String tvien = kq.getString("HoTen");
                modelcd.addElement(tvien);
            }
        } catch (Exception e) {
        }
    }

    public boolean valuediem() {
        String regex = "^(10|[0-9])$";
        Pattern p = Pattern.compile(regex);
        Matcher mc = p.matcher(txtdiem.getText().trim());
        if (!mc.find()) {
            return false;
        }
        return true;
    }

    public void chuanhapdiem() {
        DefaultTableModel model = (DefaultTableModel) tablehv.getModel();
        model.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select hv.MaHV , hv.MaNH , nh.HoTen , hv.Diem from HocVien as hv join NguoiHoc as nh on hv.MaNH = nh.MaNH where Diem is null");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String mahv = kq.getString("hv.MaHV");
                String manh = kq.getString("hv.MaNH");
                String hoten = kq.getString("nh.HoTen");
                String diem = kq.getString("hv.Diem");
                Object[] data = {sttchuadiem, mahv, manh, hoten, diem};
                model.addRow(data);
                sttchuadiem++;
            }
            sttchuadiem = 1;

        } catch (Exception e) {
        }
    }

    public void danhapdiem() {
        DefaultTableModel model = (DefaultTableModel) tablehv.getModel();
        model.setRowCount(0);
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("select hv.MaHV , hv.MaNH , nh.HoTen , hv.Diem from HocVien as hv join NguoiHoc as nh on hv.MaNH = nh.MaNH where Diem is not null");
            ResultSet kq = st.executeQuery();
            while (kq.next()) {
                String mahv = kq.getString("hv.MaHV");
                String manh = kq.getString("hv.MaNH");
                String hoten = kq.getString("nh.HoTen");
                String diem = kq.getString("hv.Diem");
                Object[] data = {sttchuadiem, mahv, manh, hoten, diem};
                model.addRow(data);
                sttchuadiem++;
            }
            sttchuadiem = 1;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cbxhocvienkhac = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txtdiem = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablehv = new javax.swing.JTable();
        checktatca = new javax.swing.JRadioButton();
        checkchuanhap = new javax.swing.JRadioButton();
        checkdanhap = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        btnpdf = new javax.swing.JButton();
        btnexcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HỌC VIÊN KHÁC", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 24))); // NOI18N

        cbxhocvienkhac.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtdiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxhocvienkhac, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtdiem, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(jButton1)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxhocvienkhac)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtdiem))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HỌC VIÊN TRONG CỦA KHÓA", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 24))); // NOI18N

        tablehv.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tablehv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ HỌC VIÊN", "MÃ NGƯỜI HỌC", "HỌ VÀ TÊN", "ĐIỂM", "XÓA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablehv);

        buttonGroup1.add(checktatca);
        checktatca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        checktatca.setText("Tất Cả");
        checktatca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checktatcaMouseClicked(evt);
            }
        });

        buttonGroup1.add(checkchuanhap);
        checkchuanhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        checkchuanhap.setText("Chưa Nhập Điểm");
        checkchuanhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkchuanhapMouseClicked(evt);
            }
        });

        buttonGroup1.add(checkdanhap);
        checkdanhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        checkdanhap.setText("Đã Nhập Điểm");
        checkdanhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkdanhapMouseClicked(evt);
            }
        });
        checkdanhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkdanhapActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Cập Nhật");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(checktatca, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkdanhap)
                        .addGap(24, 24, 24)
                        .addComponent(checkchuanhap)
                        .addGap(18, 18, 18)
                        .addComponent(btnexcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnpdf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checktatca)
                            .addComponent(checkdanhap)
                            .addComponent(checkchuanhap))
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnpdf)
                        .addComponent(btnexcel)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Connection c = JDBCUtil.getConnection();
            PreparedStatement st = c.prepareStatement("insert into HocVien (MaKH,MaNH,Diem) values (?,?,?)");
            Connection c1 = JDBCUtil.getConnection();
            PreparedStatement st1 = c1.prepareStatement("select MaKH from KhoaHoc as kh join ChuyenDe as cd on kh.MaCD = cd.MaCD where TenCD = ?");
            st1.setString(1, cde);
            ResultSet kq1 = st1.executeQuery();
            while (kq1.next()) {
                st.setString(1, kq1.getString("MaKH"));
            }
            Connection c2 = JDBCUtil.getConnection();
            PreparedStatement st2 = c2.prepareStatement("select MaNH from NguoiHoc where HoTen = ?");
            st2.setString(1, (String) cbxhocvienkhac.getSelectedItem());
            ResultSet kq2 = st2.executeQuery();
            while (kq2.next()) {
                st.setString(2, kq2.getString("MaNH"));
            }
            if (txtdiem.getText().trim().equals("")) {
                st.setString(3, "-1");
            } else {
                if (valuediem()) {
                    st.setString(3, txtdiem.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "Điểm không hợp lệ !");
                    return;
                }
            }

            int kqthucthi = st.executeUpdate();
            if (kqthucthi > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công !");
                fill();
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Thêm không thành công !");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã tồn tại trong khóa học !");
            return;
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            for (int i = 0; i < tablehv.getRowCount(); i++) {
                Object values = tablehv.getValueAt(i, 5);
                if (values != null) {
                    Connection c = JDBCUtil.getConnection();
                    PreparedStatement st = c.prepareStatement("delete from HocVien where MaHV = ?");
                    st.setString(1, (String) tablehv.getValueAt(i, 1));
                    int kq = st.executeUpdate();
                }
            }
            checktatca.setSelected(true);
            fill();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void checkchuanhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkchuanhapMouseClicked
        // TODO add your handling code here:
        chuanhapdiem();
    }//GEN-LAST:event_checkchuanhapMouseClicked

    private void checkdanhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkdanhapMouseClicked
        danhapdiem();        // TODO add your handling code here:
    }//GEN-LAST:event_checkdanhapMouseClicked

    private void checktatcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checktatcaMouseClicked
        fill();        // TODO add your handling code here:
    }//GEN-LAST:event_checktatcaMouseClicked

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
            Excel(tablehv, new File(file));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnexcelActionPerformed

    private void btnpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpdfActionPerformed
        MessageFormat header = new MessageFormat("Danh sách học viên");
        MessageFormat header1 = new MessageFormat("Hết");
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            tablehv.print(JTable.PrintMode.FIT_WIDTH, header, header1, true, set, true);
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnpdfActionPerformed

    private void checkdanhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkdanhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkdanhapActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnexcel;
    private javax.swing.JButton btnpdf;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxhocvienkhac;
    private javax.swing.JRadioButton checkchuanhap;
    private javax.swing.JRadioButton checkdanhap;
    private javax.swing.JRadioButton checktatca;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablehv;
    private javax.swing.JTextField txtdiem;
    // End of variables declaration//GEN-END:variables
}
