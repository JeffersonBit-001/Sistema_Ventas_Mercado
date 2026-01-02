/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import conexion.Conexion;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;
import modelo.Producto;

/**
 *
 * @author JEFFERSON
 */
public class PagosDao extends Conexion {

    /*CLIENTE*/
    
    public void conseguirNombreCliente(Cliente cl) {
        
        String consulta = "select cl.nombre "
                + "from factura f "
                + "inner join clientes cl "
                + "on f.id_cliente=cl.id_cliente "
                + "where f.id =" + cl.getId_fac_pag();
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            while (rs.next()) {
                cl.setNombre(rs.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar idcliente" + e.toString());
        } 
    }
    
    
    
    
    
    public void cargarClientes(String nom_text, JComboBox box) {

        String consulta = "SELECT CONCAT(dni,' - ',nombre, ' ', apellido) AS nom "
                + "FROM clientes WHERE tipo='NE' and dni LIKE '%" + nom_text + "%' or CONCAT(nombre, ' ', apellido) like '%" + nom_text + "%'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            box.removeAllItems();
            box.addItem("--Selecciona--");
            //DefaultListModel<String> listModel = new DefaultListModel<>();
            while (rs.next()) {
                box.addItem(rs.getString("nom"));
                
                //listModel.addElement(rs.getString("nom"));
            }

            //lista.setModel(listModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error 2"+e);
        }
    }
    
    
    

    public List obtenerListaDni(Cliente cl) {
        //con = cn.EstablecerConexion();
        List<Cliente> ListaStDn = new ArrayList();
        String consulta = "select clientes.id_cliente, clientes.dni from clientes where clientes.tipo='NE'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliDn = new Cliente();
                cliDn.setId_fac_pag(rs.getInt("id_cliente"));
                cliDn.setDni(rs.getInt("dni"));
                ListaStDn.add(cliDn);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error obtener clientes dni" + e.toString());

        }
        return ListaStDn;
    }

    public void verificarDNI(Cliente cl) {
        List<Cliente> ListaStDn = this.obtenerListaDni(cl);
        for (int i = 0; i < ListaStDn.size(); i++) {
            if (cl.getDni() == ListaStDn.get(i).getDni()) {
                cl.setVer("Correcto");
            }
        }
    }

    public void conseguirIdCliente(Cliente cl) {

        String consulta = "select clientes.id_cliente from clientes "
                + "where clientes.tipo='NE' and clientes.dni =" + cl.getDni();
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            while (rs.next()) {
                cl.setId(rs.getInt("id_cliente"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar idcliente" + e.toString());
        }
    }

    /*PRODUCTOS*/
    public void conseguirIdProducto(Producto prod) {

        String consulta = "select idProducto from producto "
                + "where nombre like '%" + prod.getNombre() + "%'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            while (rs.next()) {
                prod.setIdProducto(rs.getInt("idProducto"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar id_productos" + e.toString());
        }
    }

    public void conseguirCantidadProductos(Producto prod) {

        String consulta = "select cantidad from producto "
                + "where nombre like '%" + prod.getNombre() + "%'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            while (rs.next()) {
                prod.setCantidad(rs.getInt("cantidad"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar id_productos" + e.toString());
        }
    }

    public List obtenerMaterialesProductos(Cliente cl) {
        //con = cn.EstablecerConexion();
        List<Producto> ListaStMat = new ArrayList();
        String consulta = "select det.id as idFac, concat_ws(' ', cl.nombre,cl.apellido) as nom,"
                + "cl.dni as dni, prod.nombre as nomProd, det.cant_prod_usados as cantidad, fac.fecha as fec from factura fac "
                + "inner join detalle_factura det "
                + "on fac.id = det.id_factura "
                + "inner join clientes cl "
                + "on fac.id_cliente = cl.id_cliente "
                + "inner join producto prod "
                + "on det.id_producto = prod.idProducto "
                + "where fac.estado = 'pendiente' and cl.tipo='NE' and cl.dni = " + cl.getDni();
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto prod2 = new Producto();
                prod2.setNombre(rs.getString("nomProd"));
                prod2.setCantidad2(rs.getInt("cantidad"));
                ListaStMat.add(prod2);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error obtener productos sistmea" + e.toString());

        }
        return ListaStMat;
    }

    public void disminuirStock(Producto prod, int total) {

        String consulta = "update producto set cantidad=? "
                + "where nombre='" + prod.getNombre() + "'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);

            ps.setInt(1, total);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error 13" + e);
        }
    }

    public void verificarDatosPago(Cliente cl, Producto prod, JComboBox nom_produ, JTextField cant) {
        List<Producto> ListaStMat = this.obtenerMaterialesProductos(cl);

        for (int i = 0; i < ListaStMat.size(); i++) {

            if (ListaStMat.get(i).getNombre().equalsIgnoreCase(nom_produ.getSelectedItem().toString())) {
                cl.setVer("Incorrecto");
            }
        }
    }

    public void cargarItemProducto(JComboBox comboProd, String nom) {

        String consulta = "select * from producto where nombre like '%"+nom+"%'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            comboProd.removeAllItems();
            comboProd.addItem("--Selecciona--");
            while (rs.next()) {
                comboProd.addItem(rs.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 3");
        }
    }

    /*FACTURA*/
    public void insertarFacturaCliente(Cliente cl) {

        String consultaprincipal = "insert into factura(id_cliente,codigo,total,id_usuarios) values (?,?,?,?)";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consultaprincipal);

            ps.setInt(1, cl.getId());
            ps.setString(2, cl.getCod_pago());
            ps.setFloat(3, 0);
            ps.setInt(4, cl.getId_iden2());

            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error para registrar ususus" + e.toString());
        }
    }

    public void conseguirIDFactura(Cliente cl) {

        String consulta = "select id "
                + "from factura "
                + "where id_cliente=" + cl.getId()
                + " and codigo = '" + cl.getCod_pago() + "'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            while (rs.next()) {
                cl.setId_fac_pag(rs.getInt("id"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar id_factura" + e.toString());
        }
    }

    public void mostrarTablaFacturaCliente(JTable paramTable) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override

            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 7) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        /*FormAdmSistema adm=new FormAdmSistema();
        modelo=(DefaultTableModel) adm.jTable1.getModel();*/
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        String sql = "select fac.id as idFac, concat_ws(' ', cl.nombre,cl.apellido) as nom,"
                + "cl.dni as dni, prod.nombre as nomProd, det.cant_prod_usados as cantidad, fac.fecha as fecha, fac.codigo from factura fac "
                + "inner join detalle_factura det "
                + "on fac.id = det.id_factura "
                + "inner join clientes cl "
                + "on fac.id_cliente = cl.id_cliente "
                + "inner join producto prod "
                + "on det.id_producto = prod.idProducto "
                + "where fac.estado = 'pendiente' and cl.tipo='NE' "
                + "order by det.id asc";

        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("DNI");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Fecha");
        modelo.addColumn("Código");
        //modelo.addColumn("Hora");

        paramTable.setModel(modelo);

        String[] datos = new String[7];
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    datos[i] = rs.getString((i + 1));
                }

                modelo.addRow(datos);
            }

            paramTable.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de mostrar datos" + e.toString());
        }

    }

    public boolean buscarTablaFacturaCliente(JTable paramTable, Cliente cl, JComboBox combo) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override

            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 7) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        /*FormAdmSistema adm=new FormAdmSistema();
        modelo=(DefaultTableModel) adm.jTable1.getModel();*/
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        String sql = "select fac.id as idFac, concat_ws(' ', cl.nombre,cl.apellido) as nom,"
                + "cl.dni as dni, prod.nombre as nomProd, det.cant_prod_usados as cantidad, fac.fecha as fecha, fac.codigo from factura fac "
                + "inner join detalle_factura det "
                + "on fac.id = det.id_factura "
                + "inner join clientes cl "
                + "on fac.id_cliente = cl.id_cliente "
                + "inner join producto prod "
                + "on det.id_producto = prod.idProducto "
                + "where fac.estado = 'pendiente' and cl.tipo='NE' and prod.nombre like '%" + cl.getNombre() + "%'"
                + " order by det.id asc";

        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("DNI");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Fecha");
        modelo.addColumn("Código");
        //modelo.addColumn("Hora");

        paramTable.setModel(modelo);

        String[] datos = new String[7];
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    datos[i] = rs.getString((i + 1));
                }

                modelo.addRow(datos);
            }

            paramTable.setModel(modelo);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de mostrar datos" + e.toString());
        }
        return false;
    }

    public void insertarDatosTabladetallePago(Cliente cl, Producto prod) {

        String consultaprincipal = "insert into "
                + "detalle_factura(id_factura,id_producto,"
                + "cant_prod_usados) "
                + "values (?,?,?)";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consultaprincipal);
            ps.setInt(1, cl.getId_fac_pag());

            ps.setInt(2, prod.getIdProducto());
            ps.setInt(3, prod.getCantidad());

            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error para registrar" + e.toString());
        }
    }

    public void modificarFacturaCliente(Cliente cl) {

        String consulta = "update factura set id_cliente=?"
                + " WHERE id=? and codigo = ? and estado =  'pendiente';";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, cl.getId());
            ps.setInt(2, cl.getId_fac_pag());
            ps.setString(3, cl.getCod_pago());

            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 9" + e.toString());
        }
    }

    public void modificarDatosTabladetallePago(Cliente cl, Producto prod,
            int id_prod, int cant) {

        String consultaprincipal = "update detalle_factura as dt "
                
                + "                set dt.cant_prod_usados=?, dt.id_producto = ?  "
                + "                WHERE dt.id_factura=? and "
                + "                dt.id_producto=? ";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consultaprincipal);

            //cs.setInt(1, cl.getId_fac_pag());
            ps.setInt(1, prod.getCantidad());
            
            ps.setInt(2, prod.getIdProducto());
            ps.setInt(3, cl.getId_fac_pag());

            ps.setInt(4, id_prod);

            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error para registrar" + e.toString());
        }
    }

    public void eliminarDetalleFacturaUpdate(Cliente cl, Producto prod) {

        String consulta = "DELETE dt "
                + "FROM detalle_factura AS dt "
                + "WHERE dt.id_factura = ? "
                + "  AND dt.id_producto = ? "
                + "  AND dt.cant_prod_usados = ? ";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, cl.getId_fac_pag());
            ps.setInt(2, prod.getIdProducto());
            ps.setInt(3, prod.getCantidad());
            ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 21");
        }
    }

    public void eliminarFacturaUpdate(Cliente cl) {

        String consulta = "delete from factura where id = ? and estado = 'pendiente';";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, cl.getId_fac_pag());
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 13");
        }
    }

    public void precioTablaDetalleFactura(Cliente cl) {
        
        String consulta = "";

        
            consulta = "select det.precio , det.cant_prod_usados from detalle_factura det " +
                        "inner join producto prod " +
                        "on det.id_producto = prod.idProducto " +
                        "inner join factura f " +
                        "on f.id = det.id_factura " +
                        "where f.codigo = '"+cl.getCod_pago()+"' and f.estado = 'pendiente'";
        

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            double pagoAcumulador = 0;
            int a = 0;
            while (rs.next()) {
                cl.setPago(rs.getInt("precio"));
                cl.setCant_ser(rs.getInt("cant_prod_usados"));
                
                
                
                //double pagoAcumulador=0;
                pagoAcumulador = pagoAcumulador + cl.getPago();
                a = a + cl.getCant_ser();
                
                cl.setPago(pagoAcumulador);
                
                cl.setCant_ser(a);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar idcliente" + e.toString());
        }

    }
    
    public void modificarFacPendiente(Cliente cl) {

        String consulta = "update factura set estado = 'proceso'"
                + " WHERE codigo=? and estado =  'pendiente';";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, cl.getCod_pago());

            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 9" + e.toString());
        }
    }
    

    /*USUARIO*/
    public void conseguirIdUsuario(Cliente cl, JLabel nom_trabaj) {

        String consulta = "select us.id from usuarios as us "
                + "where concat_ws(' ', us.nombre,us.apellido) like '%" + nom_trabaj.getText() + "%'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            while (rs.next()) {
                cl.setId_iden2(rs.getInt("id"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar ID_USUARIOS" + e.toString());
        }
    }

}
