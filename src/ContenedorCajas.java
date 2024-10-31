import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContenedorCajas extends JPanel implements ComponentListener, ActionListener, KeyListener {

    private JTextField[] cajas;
    private JButton[] btnsCierre;
    private int contadorCajas;
    private static final int MAX_CAJAS = 10;
    private String cadenaAEvaluar;

    public ContenedorCajas() {
        hazInterfaz();
        addComponentListener(this);
        contadorCajas = 0;
    }

    private void hazInterfaz() {
        setLayout(null);
        setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        cajas = new JTextField[MAX_CAJAS];
        btnsCierre = new JButton[MAX_CAJAS];
    }

    public void agregarCaja() {
        if (contadorCajas < MAX_CAJAS) {
            cajas[contadorCajas] = new JTextField();
            btnsCierre[contadorCajas] = new JButton("x");
            componentResized(null);
            hazEscuchas();
            add(cajas[contadorCajas]);
            add(btnsCierre[contadorCajas]);
            contadorCajas++;
        }
    }

    private void hazEscuchas() {
        cajas[contadorCajas].addKeyListener(this);
        btnsCierre[contadorCajas].addActionListener(this);
    }

    public JButton[] getBtnsCierre() {
        JButton[] arr;
        int c = 0;
        for (int i = 0; i < MAX_CAJAS; i++) {
            if (cajas[i] != null && btnsCierre[i] != null) {
                c++;
            }
        }
        arr = new JButton[c];
        for (int i = 0; i < c; i++) {
            arr[i] = btnsCierre[i];
        }
        return arr;
    }

    @Override
    public void componentResized(ComponentEvent evt) {
        int w = this.getWidth();
        int h = this.getHeight();
        int x;
        int y = 0;
        int ancho;
        int alto = 0;
        for (int i = 0; i < MAX_CAJAS; i++) {
            if (cajas[i] != null && btnsCierre[i] != null) {
                x = 0;
                y += alto;
                ancho = (int) (w * 0.6);
                alto = (int) (h * 0.1);
                cajas[i].setBounds(x, y, ancho, alto);
                x = (int) (w * 0.7);
                ancho = (int) (w * 0.2);
                btnsCierre[i].setBounds(x, y, ancho, alto);
            }
        }
        revalidate();
        repaint();
    }

    public boolean existeAlgunaCaja() {
        for (int i = 0; i < MAX_CAJAS; i++) {
            if (cajas[i] != null)
                return true;
        }
        return false;
    }

    public String getTextEspecifico(int indice) {
        try {
            return evaluarTextField(cadenaAEvaluar, indice) ? cajas[indice].getText() : null;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public String[] getTextAll() {
        String[] s = new String[contadorCajas];
        try {
            for (int i = 0; i < s.length; i++) {
                if (evaluarTextField(cadenaAEvaluar, i)) 
                    s[i] = cajas[i].getText();
                else
                    s[i] = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s; 
    }
    

    private void acomodarCajas(int primero) {
        for (int i = primero; i < MAX_CAJAS - 1; i++) {
            cajas[i] = cajas[i + 1];
            btnsCierre[i] = btnsCierre[i + 1];
        }
        cajas[MAX_CAJAS - 1] = null;
        btnsCierre[MAX_CAJAS - 1] = null;
    }

    public void setCadenaAEvaluar(String s) {
        cadenaAEvaluar = s;
    }

    private boolean[] evaluarTextFields(String regex) {
        boolean[] flags = new boolean[MAX_CAJAS];
        Pattern patron = Pattern.compile(regex);
        Matcher m;
        for (int i = 0; i < MAX_CAJAS; i++) {
            if (cajas[i] != null) {
                m = patron.matcher(cajas[i].getText());
                flags[i] = m.find();
            } else {
                flags[i] = false;
            }
        }
        return flags;
    }

    private boolean evaluarTextField(String regex, int index) {
        Pattern patron = Pattern.compile(regex);
        Matcher m;
        if (cajas[index] != null) {
            m = patron.matcher(cajas[index].getText());
            return m.find();
        }
        return false;
    }

    public boolean cajasVacias(){
        for (int i=0; i<contadorCajas; i++){
            if(cajas[i].getText().equals(""))
                return true;
        }
        return false;
    }

    @Override
    public void componentMoved(ComponentEvent evt) {
    }

    @Override
    public void componentShown(ComponentEvent evt) {
    }

    @Override
    public void componentHidden(ComponentEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.isControlDown()) {
            evt.consume();
            return;
        }
        // consume la tecla Inicio,flecha izq o flecha der

        if (evt.getKeyCode() == 36 || evt.getKeyCode() == 37 || evt.getKeyCode() == 39) {
            evt.consume();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        for (int i = 0; i < MAX_CAJAS; i++) {
            if (evt.getComponent() == cajas[i]) {
                boolean b = evaluarTextField(cadenaAEvaluar, i);
                if (cajas[i].getText().equals("")){
                    cajas[i].setBorder(new LineBorder(Color.GRAY));
                    return;
                }
                if (b)
                    cajas[i].setBorder(new LineBorder(Color.GREEN));
                else
                    cajas[i].setBorder(new LineBorder(Color.RED));
                return;

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        for (int i = 0; i < MAX_CAJAS; i++) {
            if (btnsCierre[i] != null && evt.getSource() == btnsCierre[i]) {
                remove(cajas[i]);
                remove(btnsCierre[i]);
                acomodarCajas(i);
                componentResized(null);
                revalidate();
                contadorCajas--;
                return;
            }
        }
    }
}
