package vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import modele.Vehicule;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextField input_color;
	private JTable table;
	public DefaultTableModel model;
	public Object[] data = new Object[4];

	private JLabel detail_type;
	private JLabel detail_marque;
	private JLabel detail_couleur;
	private JLabel detail_modele;
	public String[] column = {
		"Type", "Marque", "Modele", "Couleur"
	};;
	public BufferedImage slate;

	/**
	 * Initialisation de la frame dans le constructeur Interface
	 */
	public Interface() {

		setTitle("Gestion de collection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(95, 158, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Decoupage de fenetre en 2 gros panels via ces 2 fonctions
		panel_info_details(contentPane);
		grand_panel(contentPane);

		this.data_table();
	}

	
	private void grand_panel(JPanel contentPane){
		JPanel grand_panel = new JPanel();
		grand_panel.setBackground(new Color(95, 158, 160));
		contentPane.add(grand_panel, BorderLayout.CENTER);
		grand_panel.setLayout(new BorderLayout(0, 5));

		// Decoupage du grand panel en 2 panels via 2 fonctions
		panel_recherche(contentPane, grand_panel);
		panel_table_vehicules(contentPane, grand_panel);
	
	}

	// Panel pour afficher les tables 
	private void panel_table_vehicules(JPanel contentPane, JPanel grand_panel){
		JPanel panel_table_vehicules = new JPanel();
		grand_panel.add(panel_table_vehicules, BorderLayout.CENTER);


		panel_table_vehicules.setLayout(new BorderLayout(0, 0));
		
		this.model = new DefaultTableModel();
		this.model.setColumnIdentifiers(this.column);
		
		// table = new JTable(this.data, this.column);
		this.table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				details_vehicule();
			}
		});

		this.table.setModel(this.model);
		this.table.setFont(new Font("Rockwell", Font.PLAIN, 13));
		this.table.setShowVerticalLines(true);
		JScrollPane scrollPane = new JScrollPane(this.table);

		panel_table_vehicules.add(scrollPane);
	}

	// Panel pour faire des recherches
	private void panel_recherche(JPanel contentPane, JPanel grand_panel){
		JPanel panel_recherche = new JPanel();
		panel_recherche.setBackground(new Color(139, 69, 19));
		grand_panel.add(panel_recherche, BorderLayout.NORTH);
		panel_recherche.setLayout(new GridLayout(3, 5, 10, 0));
		
		JLabel lblNewLabel_color = new JLabel("Chercher votre modele, marque ou couleur prefer\u00E9e");
		lblNewLabel_color.setMaximumSize(new Dimension(300, 16));
		lblNewLabel_color.setMinimumSize(new Dimension(285, 16));
		lblNewLabel_color.setPreferredSize(new Dimension(321, 20));
		lblNewLabel_color.setHorizontalAlignment(SwingConstants.CENTER);
		panel_recherche.add(lblNewLabel_color);
		
		JLabel label = new JLabel("");
		panel_recherche.add(label);
		
		input_color = new JTextField();
		input_color.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String data_to_filter = input_color.getText().toLowerCase();
				filter_vehicule(data_to_filter);
			}
		});
		
		panel_recherche.add(input_color);
		input_color.setColumns(10);
		
		JButton all_vehicules = new JButton("Vendre ");
		all_vehicules.setPreferredSize(new Dimension(100, 28));
		all_vehicules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vendre_vehicule();
				// details_vehicule();
			}
		});
		all_vehicules.setActionCommand("all_vehicules");
		panel_recherche.add(all_vehicules);
		
		JLabel label_2 = new JLabel("");
		panel_recherche.add(label_2);
		
		JLabel label_3 = new JLabel("");
		panel_recherche.add(label_3);
	}

	// Panel pour voir les informations ou details du vehicule 
	private void panel_info_details(JPanel contentPane){
		JPanel panel_info_details = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_info_details.getLayout();
		flowLayout.setVgap(10);
		panel_info_details.setBackground(new Color(240, 128, 128));
		panel_info_details.setPreferredSize(new Dimension(150, 10));
		contentPane.add(panel_info_details, BorderLayout.EAST);

		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel.setBounds(new Rectangle(5, 5, 130, 130));
		
		Image image_detail = this.loadImages().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),Image.SCALE_DEFAULT);
		lblNewLabel.setIcon(new ImageIcon(image_detail));

		panel_info_details.add(lblNewLabel);
		
		this.detail_type = new JLabel("Type");
		this.detail_type.setHorizontalAlignment(SwingConstants.CENTER);
		this.detail_type.setPreferredSize(new Dimension(100, 20));
		this.detail_type.setSize(new Dimension(100, 50));
		this.detail_type.setBounds(new Rectangle(0, 0, 100, 0));
		panel_info_details.add(this.detail_type);
		
		this.detail_marque = new JLabel("Marque");
		this.detail_marque.setHorizontalAlignment(SwingConstants.CENTER);
		this.detail_marque.setPreferredSize(new Dimension(100, 20));
		panel_info_details.add(this.detail_marque);
		
		this.detail_couleur = new JLabel("Couleur");
		this.detail_couleur.setHorizontalAlignment(SwingConstants.CENTER);
		this.detail_couleur.setPreferredSize(new Dimension(100, 20));
		panel_info_details.add(this.detail_couleur);
		
		this.detail_modele = new JLabel("Modele");
		this.detail_modele.setPreferredSize(new Dimension(100, 20));
		this.detail_modele.setHorizontalAlignment(SwingConstants.CENTER);
		panel_info_details.add(this.detail_modele);
	}
	
	// fonction pour charger une image 
	
    private BufferedImage loadImages() {
         try {
         	this.slate  = ImageIO.read(new File("C:\\\\Users\\\\DELL\\\\Documents\\\\Isty\\\\POO\\\\Java\\\\workspace\\\\mini_projet\\\\images\\\\Guzzi.jpg"));
         } catch (IOException ex) {
             Logger.getLogger(Interface.class.getName()).log(
                     Level.SEVERE, null, ex);
         }
		 return this.slate;
     }

	// fonction pour charger les données des vehicules dans les tables

	 private void data_table() {
		this.model.setRowCount(0);
		for(int i=0; i<Vehicule.get_vehicules().size(); i++) {
			this.data[0] = Vehicule.get_vehicules().get(i).getType();
			this.data[1] = Vehicule.get_vehicules().get(i).getMarque();
			this.data[2] = Vehicule.get_vehicules().get(i).getModele();
			this.data[3] = Vehicule.get_vehicules().get(i).getColor();
			this.model.addRow(this.data);
			}
     }

	// fonction pour filtrer les données du vehicules dans les tables via une barre de recherche 

	 private void filter_vehicule(String data_to_filter) {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(this.model);
		this.table.setRowSorter(sorter);
		
		
		System.out.println(data_to_filter);
		if (data_to_filter.length() == 0) {
	          sorter.setRowFilter(null);
	        } else {
	          sorter.setRowFilter(RowFilter.regexFilter(data_to_filter));
	    }
     }

	// fonction pour vendre et retirer un vehicule de la table des voitures

	 private void vendre_vehicule() {
		int i = this.table.getSelectedRow();	
		this.model.removeRow(i);		
//		Vehicule.vendre_vehicule();
     }
	// fonction pour voir les informations du vehicule dans le pannel droite apres avoir selectionné un vehicule dans la table
	 
	 private void details_vehicule() {
		int i = this.table.getSelectedRow();
		this.detail_type.setText(this.model.getValueAt(i, 0).toString());
		this.detail_marque.setText(this.model.getValueAt(i, 1).toString());
		this.detail_modele.setText(this.model.getValueAt(i, 2).toString());
		this.detail_couleur.setText(this.model.getValueAt(i, 3 ).toString());
     }
}


