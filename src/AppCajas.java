/*
 * Nombre : Jose Enrique Felix Esparragoza
 * NoControl : 21170315
 * Materia : Topicos avanzados de programacion
 * Unidad : 2
 * Proyecto :  COMPONENTE CAJAS
 * Fecha : 25, octubre, 2024
 * Maestro : Clemente Garcia Gerardo
 */


import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class AppCajas extends JFrame implements ActionListener{

    private JButton btnAll, btnEsp; 
    private ComponenteCajas c;

    public AppCajas(){
        super("Tarea Componentes");
        hazInterfaz();
    }
    private void hazInterfaz() {
        setSize(750,500);
		this.setLocationRelativeTo(null);
		setLayout(new GridLayout(0,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        c = new ComponenteCajas();
		add(c);
        add(new ComponenteCajas());
		setVisible(true);
        btnAll = new JButton("Get Text All");
        btnEsp = new JButton("Get Text Especifico");
        // add(btnAll);
        // add(btnEsp);
        btnAll.addActionListener(this);
        btnEsp.addActionListener(this);

        
    }
    public static void main(String[] args) {
        new AppCajas();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAll){
            String [] s = c.getTextAll();
            for (String mensaje : s){
                System.out.println(mensaje);
            }
            return;
        }
        if (e.getSource() == btnEsp){
            return;
        }
    }
}
