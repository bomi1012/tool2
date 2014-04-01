//package de.bistrosoft.palaver.gui.view;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Table;
//import com.vaadin.ui.TwinColSelect;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
//
//import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
//import de.hska.awp.palaver2.artikelverwaltung.service.Artikelverwaltung;
//import de.hska.awp.palaver2.data.ConnectException;
//import de.hska.awp.palaver2.data.DAOException;
//import de.bistrosoft.palaver.rezeptverwaltung.domain.RezeptHasArtikel;
//
//public class WinSelectArtikel extends Window {
//	
//	// private IndexedContainer container;
//			Window subwindow = this;
//
//			// private FilterTable table;
//			// private TextField searchField = new TextField();
//			VerticalLayout box = new VerticalLayout();
//
//			// private Button showFilter;
//			private Button btAdd;
//			private TwinColSelect artikelcol = new TwinColSelect();
//			public String valueString = new String();
//			private String artikelcolInput;
//
//			// BeanItemContainer<Artikel> artikelContainer;
//
//			private Table tblArtikel;
//			public static List<String> ArtId = new ArrayList<String>();
//			public static List<String> ArtName = new ArrayList<String>();
//			List<RezeptHasArtikel> ausgArtikel;
//			
//			
//			public WinSelectArtikel(Table tblArtikel, List<RezeptHasArtikel> nAusgArtikel) {
//				artikelcol.setMultiSelect(true);
//				artikelcol.setSizeFull();
//				initLayout();
//				initArtikelList();
//				this.tblArtikel = tblArtikel;
//				this.ausgArtikel = nAusgArtikel;
//				// initSearch();
//
//				btAdd.addClickListener(new ClickListener() {
//					@Override
//					public void buttonClick(ClickEvent event) {
////						System.out.println(valueString);
//						List<String> ArtId = Arrays.asList(valueString.substring(1,
//								valueString.length() - 1).split("\\s*,\\s*"));
//						for (String s : ArtId) {
////							System.out.println(s);
//						}
//						// valueString.split
//						BeanItemContainer<RezeptHasArtikel> artikelcontainer;
//						List<RezeptHasArtikel> artikellist = new ArrayList<RezeptHasArtikel>();
//
//						for (String sId : ArtId) {
//							Long id = null;
//							try {
//								id = Long.parseLong(sId.trim());
//
//							} catch (NumberFormatException nfe) {
//
//							}
//
//							Artikel artikel = null;
//							try {
//								artikel = Artikelverwaltung.getInstance()
//										.getArtikelById(id);
//								RezeptHasArtikel a = new RezeptHasArtikel(artikel,
//										null, 1.1);
//								artikellist.add(a);
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						}
//						artikelcontainer = new BeanItemContainer<RezeptHasArtikel>(
//								RezeptHasArtikel.class, artikellist);
//						RezeptAnlegen.tblArtikel
//								.setContainerDataSource(artikelcontainer);
//						ausgArtikel=artikellist;
//						subwindow.close();
//					}
//				});
//			}
//			
//			private void initLayout() {
//				final VerticalLayout box = new VerticalLayout();
//				box.setSizeFull();
//				box.setMargin(true);
//				setContent(box);
//				btAdd = new Button("Hinzufügen");
//				box.addComponent(artikelcol);
//				box.addComponent(btAdd);
//
//			}
//			
//			private void initArtikelList() {
//
//				List<Artikel> artikel;
//				try {
//					artikel = Artikelverwaltung.getInstance().getAllArtikelName();
//					for (Artikel e : artikel) {
//						artikelcol.addItem(e.getId());
//						artikelcol.setItemCaption(e.getId(), e.getName());
//					}
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//
//				artikelcol.addValueChangeListener(new ValueChangeListener() {
//					@Override
//					public void valueChange(final ValueChangeEvent event) {
//						valueString = String
//								.valueOf(event.getProperty().getValue());
//
//					}
//				});			
//	
//	
//
//			}
//
//			}
//		
