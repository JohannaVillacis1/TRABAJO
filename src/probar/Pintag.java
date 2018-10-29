/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probar;

/**
 *
 * @author MARCOS
 */
public class Pintag {
    //cambio hecho
    // QUE SIGNIFICA ESE MODELO EN LO DE DESARROLLO
    
    public void CargarTablaAutosControles(String dato) {

        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "SELECT AUTOS.AUT_PLACA, MARCA.MAR_NOMBRE, MODELO.MOD_NOMBRE, AUTOS.AUT_ANIO,AUTOS.AUT_COLOR, AUTOS.AUT_CAPACIDAD, AUTOS.AUT_DESCRIPCION FROM AUTOS,MODELO,MARCA WHERE AUTOS.AUT_PLACA LIKE '%" + dato + "%' AND AUTOS.MOD_CODIGO=MODELO.MOD_CODIGO AND MARCA.MAR_CODIGO=MODELO.MAR_CODIGO AND EST_AUT='" + "ACTIVO" + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            modelo = new DefaultTableModel(new Object[][][][][][]{}, new String[]{"PLACA", "MARCA", "MODELO", "AÑO", "COLOR", "CAPACIDAD", "OBSERVACION"}) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return (col != 0 && col != 1 && col != 2);
                }
            };
            while (rs.next()) {
                modelo.addRow(new String[]{
                    rs.getString("AUTOS.AUT_PLACA"),
                    rs.getString("MARCA.MAR_NOMBRE"),
                    rs.getString("MODELO.MOD_NOMBRE"),
                    rs.getString("AUTOS.AUT_ANIO"),
                    rs.getString("AUTOS.AUT_COLOR"),
                    rs.getString("AUTOS.AUT_CAPACIDAD"),
                    rs.getString("AUTOS.AUT_DESCRIPCION")
                });
            }
            modelo.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.UPDATE) {

                        String colname = "";
                        if (e.getColumn() == 3) {
                            colname = "AUT_ANIO";
                        } else if (e.getColumn() == 4) {
                            colname = "AUT_COLOR";
                        } else if (e.getColumn() == 5) {
                            colname = "AUT_CAPACIDAD";
                        } else if (e.getColumn() == 6) {
                            colname = "AUT_DESCRIPCION";
                        }

                        String sql = ("UPDATE AUTOS SET " + colname + "='" + modelo.getValueAt(e.getFirstRow(), e.getColumn()) + "' WHERE AUT_PLACA='" + modelo.getValueAt(e.getFirstRow(), 0)+"'" );
                        Conexion cc = new Conexion();
                        Connection cn = cc.conectar();
                        PreparedStatement pst;
                        try {
                            pst = cn.prepareStatement(sql);
                            int rows = pst.executeUpdate();
                        } catch (SQLException ex) {
                           // Logger.getLogger(InterfaceAutos.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR " + ex);
                        }

                    }
                }

            });
            jtblAutos.setCellSelectionEnabled(true);
            jtblAutos.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");
            jtblAutos.setModel(modelo);
            jtblAutos.getTableHeader().setReorderingAllowed(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    
}
