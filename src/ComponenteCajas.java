
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

public class ComponenteCajas extends JPanel implements ComponentListener, ActionListener {

    private JRadioButton btnCorreos, btnRfc, btnTelefonos;
    private ButtonGroup grupo;
    private JButton btnCajas;
    private ContenedorCajas cajas;
    private JScrollPane scroll;
    private JButton[] btnEliminar;

    public ComponenteCajas() {
        hazInterfaz();
        hazEscuchas();
    }

    private void hazInterfaz() {
        setLayout(null);
        btnCorreos = new JRadioButton("Correos");
        btnRfc = new JRadioButton("RFC");
        btnTelefonos = new JRadioButton("Telefonos");
        grupo = new ButtonGroup();
        grupo.add(btnCorreos);
        grupo.add(btnRfc);
        grupo.add(btnTelefonos);
        btnCajas = new JButton("Nueva caja");
        cajas = new ContenedorCajas();
        scroll = new JScrollPane(cajas);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setBorder(new LineBorder(Color.BLUE));
        add(btnCorreos);
        add(btnRfc);
        add(btnTelefonos);
        add(btnCajas);
        add(scroll);
    }

    private void hazEscuchas() {
        btnCorreos.addActionListener(this);
        btnRfc.addActionListener(this);
        btnTelefonos.addActionListener(this);
        btnCajas.addActionListener(this);
        addComponentListener(this);
        //
        //
    }

    public String[] getTextAll() {
        return cajas.getTextAll();
    }

    public String getTextEspecifico(int i){
        return cajas.getTextEspecifico(i);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnCajas && grupo.getSelection() != null) {
            cajas.agregarCaja();
            btnEliminar = cajas.getBtnsCierre();
            for (int i = 0; i < btnEliminar.length; i++)
                btnEliminar[i].addActionListener(this);
            if (cajas.existeAlgunaCaja()) {
                for (AbstractButton button : Collections.list(grupo.getElements())) {
                    button.setEnabled(false);
                }
            }
            return;
        } else if (evt.getSource() == btnCajas && grupo.getSelection() == null) {
            return;
        }
        if (evt.getSource() == btnCorreos) {
            cajas.setCadenaAEvaluar("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.com$");
            return;
        }
        if (evt.getSource() == btnRfc) {
            cajas.setCadenaAEvaluar("^[A-Z]{4}\\d{6}[A-Z\\d]{3}$");
            return;
        }
        if (evt.getSource() == btnTelefonos) {
            cajas.setCadenaAEvaluar("^\\d{10}$");
            return;
        }
        for (int i = 0; i < btnEliminar.length; i++) {
            if (evt.getSource() == btnEliminar[i]) {
                cajas.actionPerformed(evt);
                if (!cajas.existeAlgunaCaja()) {
                    for (AbstractButton button : Collections.list(grupo.getElements())) {
                        button.setEnabled(true);
                    }
                }
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent evt) {
        int w = this.getWidth();
        int h = this.getHeight();
        int x = (int) (w * 0.05);
        int y = (int) (h * 0.05);
        int ancho = (int) (w * 0.25);
        int alto = (int) (h * 0.10);
        btnCorreos.setBounds(x, y, ancho, alto);
        x = (int) (w * 0.35);
        btnRfc.setBounds(x, y, ancho, alto);
        x = (int) (w * 0.65);
        btnTelefonos.setBounds(x, y, ancho, alto);
        x = (int) (w * 0.05);
        y = (int) (h * 0.20);
        ancho = (int) (w * 0.3);
        alto = (int) (h * 0.15);
        btnCajas.setBounds(x, y, ancho, alto);
        y = (int) (h * 0.40);
        ancho = (int) (w * 0.95);
        alto = (int) (h * 0.6);
        scroll.setBounds(x, y, ancho, alto);

        cajas.setPreferredSize(new Dimension(w, h));
        scroll.setViewportView(cajas);
        cajas.componentResized(evt);
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
}