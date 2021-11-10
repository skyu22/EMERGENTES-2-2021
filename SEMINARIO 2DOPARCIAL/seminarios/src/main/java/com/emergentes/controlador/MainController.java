package com.emergentes.controlador;

import com.emergentes.modelo.eventos;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PreparedStatement ps;
        ConexionDB canal=new ConexionDB();
        Connection conn =canal.conectar();
        ResultSet rs;
        String op;
        ArrayList <eventos> lista=new ArrayList<>();
        
        int id;
       
        op=(request.getParameter("op")!=null)?request.getParameter("op"):"list";
        
       if (op.equals("list")) {
             //operaciones para listar datos
               String sql="select * from seminarios";
           try {
               //consulata de selecccion y alamacenamiento
               ps=conn.prepareStatement(sql);
               rs=ps.executeQuery();
                 while(rs.next()){
                      
                   eventos event=new eventos();
                  
                   event.setId(rs.getInt("id"));
                   event.setTitulo(rs.getString("titulo"));
                   event.setExpositor(rs.getString("expositor"));
                   event.setFecha(rs.getString("fecha"));
                   event.setHora(rs.getString("hora"));
                   event.setCupo(rs.getInt("cupo"));
                   
                   lista.add(event);
             }
            
            request.setAttribute("lista", lista);
            request.getRequestDispatcher("index.jsp").forward(request, response);
          } catch (SQLException ex) {
              Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
            
          }
         if (op.equals("nuevo")) {
              eventos pr = new eventos();
              //System.out.println(li.toString());
              request.setAttribute("event", pr);
              request.getRequestDispatcher("editar.jsp").forward(request, response);
          }
          
        if (op.equals("editar")) {
           id=Integer.parseInt(request.getParameter("id"));
           try{
               eventos event1= new eventos();
               ps=conn.prepareStatement("select * from seminarios where id=?");
               ps.setInt(1, id);
               rs= ps.executeQuery();
               if (rs.next()) {
                 event1.setId(rs.getInt("id"));
                   event1.setTitulo(rs.getString("titulo"));
                   event1.setExpositor(rs.getString("expositor"));
                   event1.setFecha(rs.getString("fecha"));
                   event1.setHora(rs.getString("hora"));
                   event1.setCupo(rs.getInt("cupo"));
               }
               request.setAttribute("event", event1);
               request.getRequestDispatcher("editar.jsp").forward(request, response);
               
           }catch(SQLException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
           }
          }
       
        if (op.equals("eliminar")) {
             id=Integer.parseInt(request.getParameter("id"));
          try {
              ps=conn.prepareStatement("delete from seminarios where id=?");
              ps.setInt(1,id);
              
              ps.executeUpdate();
              response.sendRedirect("MainController");
             } catch (SQLException ex) {
              Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
             }
             
            }
      }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            
        int id=Integer.parseInt(request.getParameter("id"));
           
       
        String titulo =request.getParameter("titulo");
        String expositor =request.getParameter("expositor");
        String fecha =request.getParameter("fecha");
        String hora =request.getParameter("hora");
        
        int cupo = Integer.parseInt(request.getParameter("cupo"));

            eventos event = new eventos();
            event.setId(id);
            event.setTitulo(titulo);
            event.setExpositor(expositor);
            event.setFecha(fecha);
            event.setHora(hora);
            
            event.setCupo(cupo);
       
            ConexionDB canal=new ConexionDB();
            Connection conn =canal.conectar();
            PreparedStatement ps;
            ResultSet rs;
        
        if (id==0) {
                ///isertar registro
            String sql="insert into seminarios(titulo,expositor,fecha,hora,cupo)values(?,?,?,?,?)" ;  
            try {
                ps=conn.prepareStatement(sql);
                ps.setString(1,event.getTitulo());
                ps.setString(2,event.getExpositor());
                ps.setString(3,event.getFecha());
                ps.setString(4,event.getHora());
                
                ps.setInt(5,event.getCupo());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            }else{
              //actualizar de registro .............................
            String sql="update seminarios set titulo=?,expositor=?,fecha=?,hora=? ,cupo=? where id=?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, event.getTitulo());
                ps.setString(2, event.getExpositor());
                ps.setString(3, event.getFecha());
                ps.setString(4, event.getHora());
                
                ps.setInt(5, event.getCupo());
                ps.setInt(6, event.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
                response.sendRedirect("MainController");
       
         }

}
