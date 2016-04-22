package examen;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Ejercicio1 extends JFrame implements ActionListener {
	int x=1, y=1;
	JLabel lbtit = new JLabel("EXAMEN PROGRA 2");
	JButton b1 = new JButton("Crear");
	JButton b2 = new JButton("Menores");
	JButton b3 = new JButton("Reporte");
	JButton b4 = new JButton("Salir");
	public Ejercicio1(){
		super("Empleado");
		setSize(50, 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout dis = new FlowLayout(FlowLayout.CENTER);
		setLayout(dis);
		add(lbtit);
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==b1) {
			try {
				crear();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==b2) {
			try {
				menos();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (e.getSource()==b3) {
			try {
				reporte();
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==b4) {
			dispose();
		}
		
	}
	public static void crear() throws IOException{
		String ci,nombre;
		int nivel,ant;
		String filen="/Users/pablogonzalescarrasco/UNIVERSIDAD/Programacion_2/JAVA/examen/src/Empleado.bin";
		DataOutputStream out;
		try {
			out = new DataOutputStream(new FileOutputStream(filen,true));
			String n=JOptionPane.showInputDialog("Cuantos empleados desea añadir:");
		    int num=Integer.parseInt(n);
		    for (int i=1;i<=num;i++)
		    { 
		    	ci=JOptionPane.showInputDialog("Cedula de identidad:");
		    	nombre=JOptionPane.showInputDialog("Nombre:");
		    	nivel=Integer.parseInt(JOptionPane.showInputDialog("Nivel salarial:"));
		    	ant=Integer.parseInt(JOptionPane.showInputDialog("Años de antiguedad:"));
		    	out.writeUTF(ci);
		    	out.writeUTF(nombre);
		    	out.writeInt(nivel);
		    	out.writeInt(ant);
		    }
		    System.out.println("Archivo binario creado");
		    out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void menos() throws IOException{
		String filen = "/Users/pablogonzalescarrasco/UNIVERSIDAD/Programacion_2/JAVA/examen/src/Empleado.bin";
		String txt = "/Users/pablogonzalescarrasco/UNIVERSIDAD/Programacion_2/JAVA/examen/src/NivelSalarial.txt";
		int x=0;
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(filen));
			BufferedReader br = new BufferedReader(new FileReader(txt));
			int niveles[]= new int[10];
			int i=0;
			String linea;
			while ((linea=br.readLine())!=null) {
				String datos[]=linea.split(";");
				niveles[i]=Integer.parseInt(datos[1]);
				i++;
			}
			br.close();
			String ci,nombre;
			int nivel,ant;
			int total=0;
			int n=0;
			while (in.available()!=0) {
				ci=in.readUTF();
				nombre=in.readUTF();
				nivel=in.readInt();
				ant=in.readInt();
				total+=niveles[nivel-1];
				n++;
			}
			in.close();
			double promedio=(double)total/n;
			
			DataInputStream in2 = new DataInputStream(new FileInputStream(filen));
			while (in2.available()!=0) {
				ci=in2.readUTF();
				nombre=in2.readUTF();
				nivel=in2.readInt();
				ant=in2.readInt();
				if (niveles[nivel-1]<promedio) {
					x++;
				}
			}
			in2.close();
			JOptionPane.showMessageDialog(null, "Empleados que tienen menor sueldo que el promedio: "+x);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void reporte() throws NumberFormatException, IOException{
		String filen = "/Users/pablogonzalescarrasco/UNIVERSIDAD/Programacion_2/JAVA/examen/src/Empleado.bin";
		String txt = "/Users/pablogonzalescarrasco/UNIVERSIDAD/Programacion_2/JAVA/examen/src/NivelSalarial.txt";
		int x=0;
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(filen));
			BufferedReader br = new BufferedReader(new FileReader(txt));
			int niveles[]= new int[10];
			int i=0;
			String linea;
			while ((linea=br.readLine())!=null) {
				String datos[]=linea.split(";");
				niveles[i]=Integer.parseInt(datos[1]);
				i++;
			}
			br.close();
			String ci,nombre;
			int nivel,ant,suma=0;
			double bono,total,sbono=0, stotal=0;
			String rep="Cedula\tNombre\tSalario\tBono\tTotal\n-------------------------------------------------------------------\n";
			while (in.available()!=0) {
				ci=in.readUTF();
				nombre=in.readUTF();
				nivel=in.readInt();
				ant=in.readInt();
				if (ant<5) {
					bono=niveles[nivel-1]*0;
				}
				else if (ant<=10) {
					bono=niveles[nivel-1]*0.15;
				}
				else if (ant<=15) {
					bono=niveles[nivel-1]*0.2;
				}
				else if (ant<=20) {
					bono=niveles[nivel-1]*0.25;
				}
				else{
					bono=niveles[nivel-1]*0.3;
				}
				total=niveles[nivel-1]+bono;
				rep+=ci+"\t"+nombre+"\t"+niveles[nivel-1]+"\t"+bono+"\t"+total+"\n";
				suma+=niveles[nivel-1];
				sbono+=bono;
				stotal+=total;
			}
			rep+="-------------------------------------------------------------\nTOTAL GENERAL:\t"+suma+"\t"+sbono+"\t"+stotal+"\n";
			JTextArea jta= new JTextArea(20,10);
			JScrollPane js= new JScrollPane(jta);
			jta.setText(rep);
			JOptionPane.showMessageDialog(null, jta);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Ejercicio1 e1 = new Ejercicio1();
		e1.setBounds(300, 300, 200, 300);
	}

}
