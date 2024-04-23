package presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logica.Calendario;

public class FCalendario extends JFrame implements ActionListener {
	private Calendario calendario;
	private JPanel pSeleccion;
	private JPanel pMes;
	private JLabel lMes;
	private JLabel lAno;
	private JTextField tMes;
	private JTextField tAno;
	private JButton bMostrar;
	
	private JButton bDias[][] = new JButton[6][7];
	

	public FCalendario() {
		this.calendario = new Calendario();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Calendario POO");
		this.setSize (800, 600);
		this.setLayout(new BorderLayout());
		
		this.pSeleccion = new JPanel();
		this.add(this.pSeleccion, BorderLayout.NORTH);
		this.pSeleccion.setLayout(new FlowLayout());
		
		this.pMes = new JPanel();
		this.add(this.pMes, BorderLayout.CENTER);
		this.pMes.setLayout(new GridLayout(6,7));
		
		this.lMes = new JLabel("Mes");
		this.lAno = new JLabel("Año");
		this.tMes = new JTextField("4");
		this.tAno = new JTextField("2024");
		
		this.bMostrar = new JButton("Mostrar");
		this.bMostrar.addActionListener(this);
		
		this.pSeleccion.add(this.lMes);
		this.pSeleccion.add(this.tMes);
		this.pSeleccion.add(this.lAno);
		this.pSeleccion.add(this.tAno);
		this.pSeleccion.add(this.bMostrar);
		
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				this.bDias[i][j] = new JButton();
				this.pMes.add(this.bDias[i][j]);
				this.bDias[i][j].addActionListener(this);
			}
		}
		this.setVisible(true);
		this.mostrarCalendario();
	}
	
	public static void main(String[] args) {
		new FCalendario();
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == this.bMostrar) {
			this.mostrarCalendario();
		}else {
			JButton botonOprimido = (JButton)e.getSource();
			String dia = botonOprimido.getText();
			if(dia.length() == 1) {
				dia = "0" + dia;
			}
			String mes = this.tMes.getText();
			if(mes.length() == 1) {
				mes = "0" + mes;
			}			
			String fecha = this.tAno.getText() + "-" + mes + "-" + dia;
			
			String mensaje = "Fecha: " + fecha + "\n";
			if(this.calendario.getEventos().containsKey(fecha)) {
				mensaje += "Evento: " + this.calendario.getEventos().get(fecha).getDescripcion();	
			}else {
				mensaje += "No hay envento";
			}
			
			JOptionPane.showMessageDialog(this, mensaje, "Lista de eventos", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void mostrarCalendario() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Integer.parseInt(this.tAno.getText()), Integer.parseInt(this.tMes.getText()) - 1, 1);
	    int diaDeSemana = cal.get(Calendar.DAY_OF_WEEK) - 1;
	    int ultimoDia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    int numFilas = (ultimoDia + diaDeSemana + 6) / 7;

	    this.pMes.removeAll();

	    this.pMes.setLayout(new GridLayout(numFilas, 7));

	    int dia = 1;
	    for (int i = 0; i < numFilas; i++) {
	        for (int j = 0; j < 7; j++) {
	            if (i == 0 && j < diaDeSemana) {
	                this.bDias[i][j] = new JButton();
	                this.bDias[i][j].setEnabled(false);
	                this.pMes.add(this.bDias[i][j]);
	            } else if (dia <= ultimoDia) {
	                this.bDias[i][j] = new JButton();
	                this.bDias[i][j].addActionListener(this);
	                this.bDias[i][j].setText(String.valueOf(dia));
	                this.pMes.add(this.bDias[i][j]);
	                dia++;
	            } else {
	                this.bDias[i][j] = new JButton();
	                this.bDias[i][j].setEnabled(false);
	                this.pMes.add(this.bDias[i][j]);
	            }
	        }
	    }
	    this.revalidate();
	    this.repaint();
	}
}
