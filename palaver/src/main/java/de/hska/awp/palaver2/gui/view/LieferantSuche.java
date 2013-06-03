package de.hska.awp.palaver2.gui.view;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.hska.awp.palaver2.data.ConnectException;
import de.hska.awp.palaver2.data.DAOException;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Ansprechpartner;
import de.hska.awp.palaver2.lieferantenverwaltung.domain.Lieferant;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Ansprechpartnerverwaltung;
import de.hska.awp.palaver2.lieferantenverwaltung.service.Lieferantenverwaltung;
import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;

@SuppressWarnings("serial")
public class LieferantSuche extends VerticalLayout  implements View{
	
	private HorizontalLayout	box = new HorizontalLayout();
	private HorizontalLayout 	knoepfe = new HorizontalLayout();
	private VerticalLayout 		links = new VerticalLayout();
	private VerticalLayout		rechts = new VerticalLayout();
	private VerticalLayout		mitte = new VerticalLayout();
	
	private TextField			name = new TextField("Name");
	private TextField			bezeichnung = new TextField("Bezeichnung");
	private TextField			kundennummer = new TextField("Kundennummer");
	private TextField			strasse = new TextField("Stra�e");
	private TextField			plz = new TextField("PLZ");
	private TextField			ort = new TextField("Ort");
	private TextField			email = new TextField("E-Mail");
	private TextField			telefon = new TextField("Telefon");
	private TextField			fax = new TextField("Telefax");
	private TextArea			notiz = new TextArea("Notiz");
	private CheckBox			mehrereliefertermine = new CheckBox("mehrere Liefertermine");
	
	private Button				okButton = new Button("Ok");
	private Button 				ansprAdd = new Button(IConstants.BUTTON_ADD);
	private Button				updateB	= new Button(IConstants.BUTTON_EDIT);
	private Button				speichern = new Button(IConstants.BUTTON_SAVE);
	private Button				verwerfen = new Button(IConstants.BUTTON_DISCARD);
	private Button 				emailSenden = new Button("Email senden");
	private Table 				ansprechpartner = new Table();
	
	private String				nameInput;
	private String				telefonInput;
	private String 				handyInput;
	private String 				faxInput;
	private Lieferant 			lieferant = new Lieferant();
	private Ansprechpartner		ansprechpartnerBean = new Ansprechpartner();
	
	private TextField			nameAnspr = new TextField("Name");
	private TextField			telefonAnspr = new TextField("Telefon");
	private TextField			handyAnspr = new TextField("Handy");
	private TextField			faxAnspr = new TextField("Fax");
	
	
	public LieferantSuche() throws ConnectException, DAOException, SQLException {
		
		super();
		
		this.setSizeFull();
		this.setMargin(true);
		
		name.setWidth("100%");
		bezeichnung.setWidth("100%");
		kundennummer.setWidth("100%");
		strasse.setWidth("100%");
		plz.setWidth("100%");
		ort.setWidth("100%");
		email.setWidth("100%");
		telefon.setWidth("100%");
		fax.setWidth("100%");
		notiz.setWidth("100%");
		notiz.setRows(5);
		mehrereliefertermine.setWidth("100%");
		
		emailSenden.setEnabled(false);

		ansprechpartner.setWidth("100%");
		ansprechpartner.setHeight("230px");
		
		
		box.setWidth("900px");
		box.setHeight("90%");
		box.setSpacing(true);
		
		links.setWidth("250px");
		rechts.setWidth("320px");
		links.setSpacing(true);
		rechts.setSpacing(true);
		mitte.setWidth("250px");
		mitte.setSpacing(true);
		box.addComponentAsFirst(links);
		box.addComponent(mitte);
		box.addComponent(rechts);
		
		links.addComponent(name);
		links.addComponent(bezeichnung);
		links.addComponent(kundennummer);
		links.addComponent(strasse);
		links.addComponent(plz);
		links.addComponent(ort);
		mitte.addComponent(email);
		mitte.addComponent(telefon);
		mitte.addComponent(fax);
		mitte.addComponent(notiz);
		mitte.addComponent(mehrereliefertermine);
		
		rechts.addComponent(ansprAdd);
		
		ansprAdd.setIcon(new ThemeResource(IConstants.BUTTON_ADD_ICON));
		
		this.addComponent(box);
		this.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
		
		speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
		verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

		rechts.addComponent(ansprechpartner);
		knoepfe.addComponent(okButton);
		knoepfe.addComponentAsFirst(updateB);
		knoepfe.addComponent(emailSenden);
		updateB.setIcon(new ThemeResource(IConstants.BUTTON_EDIT_ICON));
		mitte.addComponent(knoepfe);
		mitte.setComponentAlignment(knoepfe, Alignment.BOTTOM_RIGHT);
		
        okButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ViewHandler.getInstance().switchView(LieferantAnzeigen.class);					
			}
		});
        
        nameAnspr.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                nameInput = valueString;
            }
        });
        
        telefonAnspr.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                telefonInput = valueString;
            }
        });
        
        handyAnspr.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                handyInput = valueString;
            }
        });
        
        faxAnspr.addValueChangeListener(new ValueChangeListener() {

            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                faxInput = valueString;
            }
        });
        
        ansprechpartner.setSelectable(true);
        
        ansprechpartner.addValueChangeListener(new ValueChangeListener()
		{	
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				if (event.getProperty().getValue() != null)
				{
					ansprechpartnerBean = (Ansprechpartner) event.getProperty().getValue();
				}
			}
		});
        
        emailSenden.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event)
			{
				System.out.print("HAllo");
				ViewHandler.getInstance().switchView(EmailOhneBestellung.class, new ViewDataObject<Lieferant>(lieferant));
			}
		});
        
		updateB.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				name.setEnabled(true);
				bezeichnung.setEnabled(true);
				kundennummer.setEnabled(true);
				strasse.setEnabled(true);
				plz.setEnabled(true);
				ort.setEnabled(true);
				email.setEnabled(true);
				telefon.setEnabled(true);
				fax.setEnabled(true);
				notiz.setEnabled(true);
				mehrereliefertermine.setEnabled(true);
				
				knoepfe.setVisible(false);
				HorizontalLayout 	knoNeu = new HorizontalLayout();
				
				knoNeu.addComponent(verwerfen);
				knoNeu.addComponent(speichern);
				mitte.addComponent(knoNeu);
				mitte.setComponentAlignment(knoNeu, Alignment.BOTTOM_RIGHT);
				
				verwerfen.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));						
					}
				});
				
				speichern.addClickListener(new ClickListener()
				{
					public void buttonClick(ClickEvent event)
					{
						lieferant.setName(name.getValue());
						lieferant.setBezeichnung(bezeichnung.getValue());
						lieferant.setKundennummer(kundennummer.getValue());
						lieferant.setStrasse(strasse.getValue());
						lieferant.setOrt(ort.getValue());
						lieferant.setPlz(plz.getValue());
						lieferant.setEmail(email.getValue());
						lieferant.setTelefon(telefon.getValue());
						lieferant.setFax(fax.getValue());
						lieferant.setNotiz(notiz.getValue());
						lieferant.setMehrereliefertermine(mehrereliefertermine.getValue());
						try {
							Lieferantenverwaltung.getInstance().updateLieferant(lieferant);
						} catch (Exception e) {
							throw new NullPointerException("Bitte g�ltige Werte eingeben");
						}
						ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
					}
				});
				
				
			}
		});
		
		ansprechpartner.addItemClickListener(new ItemClickListener() {	
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					final Window anspr = new Window();
					anspr.setClosable(false);
					anspr.setWidth("400px");
					anspr.setHeight("270px");
					anspr.setModal(true);
					anspr.center();
					anspr.setResizable(false);
					anspr.setCaption("Ansprechpartner bearbeiten");
					
					
					UI.getCurrent().addWindow(anspr);
					
					VerticalLayout	layout = new VerticalLayout();
					layout.setMargin(true);
					layout.setWidth("100%");
					layout.setSpacing(true);

					Button			loeschen = new Button(IConstants.BUTTON_DELETE);
					Button			speichern = new Button(IConstants.BUTTON_SAVE);
					Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
					
					nameAnspr.setWidth("100%");
					telefonAnspr.setWidth("100%");
					handyAnspr.setWidth("100%");
					faxAnspr.setWidth("100%");
					
					VerticalLayout feld = new VerticalLayout();
					
					nameAnspr.setValue(ansprechpartnerBean.getName());
					telefonAnspr.setValue(ansprechpartnerBean.getTelefon());
					handyAnspr.setValue(ansprechpartnerBean.getHandy());
					faxAnspr.setValue(ansprechpartnerBean.getFax());
					
					feld.addComponent(nameAnspr);
					feld.addComponent(telefonAnspr);
					feld.addComponent(handyAnspr);
					feld.addComponent(faxAnspr);

					HorizontalLayout control = new HorizontalLayout();
					control.setSpacing(true);
					control.addComponent(loeschen);
					control.addComponent(verwerfen);
					control.addComponent(speichern);
					
					speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
					verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));
					loeschen.setIcon(new ThemeResource(IConstants.ICON_DELETE));
					
					layout.addComponent(feld);
					layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
					layout.addComponent(control);
					layout.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
					anspr.setContent(layout);
					
					nameAnspr.setImmediate(true);
					nameAnspr.setInputPrompt(nameInput);
					nameAnspr.setMaxLength(30);
					
					telefonAnspr.setImmediate(true);
					telefonAnspr.setInputPrompt(telefonInput);
					telefonAnspr.setMaxLength(20);	
					
					handyAnspr.setImmediate(true);
					handyAnspr.setInputPrompt(handyInput);
					handyAnspr.setMaxLength(20);
					
					faxAnspr.setImmediate(true);
					faxAnspr.setInputPrompt(faxInput);
					faxAnspr.setMaxLength(20);
					
					verwerfen.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().removeWindow(anspr);							
						}
					});
					
					loeschen.addClickListener(new ClickListener(){
						
						@Override
						public void buttonClick(ClickEvent event) {
							
							anspr.setStyleName("dialog-window");
							anspr.setClosable(false);
							anspr.setWidth("320px");
							anspr.setHeight("135px");
							anspr.setModal(true);
							anspr.center();
							anspr.setResizable(false);
							anspr.setCaption("");
							
							HorizontalLayout layout = new HorizontalLayout();
							layout.setMargin(true);
							layout.setSizeFull();
							layout.setSpacing(true);
							
							VerticalLayout verticallayout = new VerticalLayout();
														
							Label label = new Label("Wollen Sie den Ansprechpartner wirklich l�schen?");
							verticallayout.addComponent(label);
							verticallayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
							
							HorizontalLayout horizontallayout = new HorizontalLayout();
							horizontallayout.setMargin(true);
							horizontallayout.setSpacing(true);
							
							Button ja = new Button(IConstants.BUTTON_JA);
							Button nein = new Button(IConstants.BUTTON_NEIN);
							
							ja.setIcon(new ThemeResource(IConstants.ICON_YES));
							nein.setIcon(new ThemeResource(IConstants.ICON_NO));
							
							horizontallayout.addComponent(ja);
							horizontallayout.addComponent(nein);
							
							verticallayout.addComponent(horizontallayout);
							verticallayout.setComponentAlignment(horizontallayout, Alignment.MIDDLE_CENTER);
							
							layout.addComponent(verticallayout);
							layout.setComponentAlignment(verticallayout, Alignment.MIDDLE_CENTER);
							
							anspr.setContent(layout);							
							
							ja.addClickListener(new ClickListener()
							{
								public void buttonClick(ClickEvent event){
									try {
									Ansprechpartnerverwaltung.getInstance().deleteAnsprechpartner(ansprechpartnerBean.getId());
									} catch (Exception e) {
									System.out.println(e);
									}
							
								UI.getCurrent().removeWindow(anspr);
								ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
								}
							});
							
							nein.addClickListener(new ClickListener()
							{
								public void buttonClick(ClickEvent event){
									
								UI.getCurrent().removeWindow(anspr);
								ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));
								}
							});
						}						
					});
					
					speichern.addClickListener(new ClickListener()
					{
						public void buttonClick(ClickEvent event)
						{							
							ansprechpartnerBean.setName(nameInput);
							ansprechpartnerBean.setTelefon(telefonInput);
							ansprechpartnerBean.setHandy(handyInput);
							ansprechpartnerBean.setFax(faxInput);
							ansprechpartnerBean.setLieferant(lieferant);
							
							try {
								Ansprechpartnerverwaltung.getInstance().updateAnsprechpartner(ansprechpartnerBean);
							} catch (Exception e) {
								System.out.println(e);

							}				
							
							UI.getCurrent().removeWindow(anspr);
							ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));				
							}
					});
					
					

			        nameAnspr.addValueChangeListener(new ValueChangeListener() {

			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());

			                nameInput = valueString;
			            }
			        });
			        
			        telefonAnspr.addValueChangeListener(new ValueChangeListener() {
			            @Override
			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());
			                telefonInput = valueString;
			            }
			        });
			        
			        handyAnspr.addValueChangeListener(new ValueChangeListener() {

			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());

			                handyInput = valueString;
			            }
			        });
			        
			        faxAnspr.addValueChangeListener(new ValueChangeListener() {

			            public void valueChange(final ValueChangeEvent event) {
			                final String valueString = String.valueOf(event.getProperty()
			                        .getValue());

			                faxInput = valueString;
			            }
			        });
				}
			
				
				
			}
		});
		
		ansprAdd.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window anspr = new Window();
			anspr.setClosable(false);
			anspr.setWidth("400px");
			anspr.setHeight("270px");
			anspr.setModal(true);
			anspr.center();
			anspr.setResizable(false);
			anspr.setCaption("Ansprechpartner hinzuf�gen");
			
			UI.getCurrent().addWindow(anspr);
			
			VerticalLayout	layout = new VerticalLayout();
			layout.setMargin(true);
			layout.setWidth("100%");
			layout.setSpacing(true);

			Button			speichern = new Button(IConstants.BUTTON_SAVE);
			Button			verwerfen = new Button(IConstants.BUTTON_DISCARD);
			
			nameAnspr.setWidth("100%");
			telefonAnspr.setWidth("100%");
			handyAnspr.setWidth("100%");
			faxAnspr.setWidth("100%");
			
			name.setRequired(true);
			
			VerticalLayout feld = new VerticalLayout();
		
			feld.addComponent(nameAnspr);
			feld.addComponent(telefonAnspr);
			feld.addComponent(handyAnspr);
			feld.addComponent(faxAnspr);

			HorizontalLayout control = new HorizontalLayout();
			control.setSpacing(true);
			control.addComponent(verwerfen);
			control.addComponent(speichern);
			speichern.setIcon(new ThemeResource(IConstants.BUTTON_SAVE_ICON));
			verwerfen.setIcon(new ThemeResource(IConstants.BUTTON_DISCARD_ICON));

			layout.addComponent(feld);
			layout.setComponentAlignment(feld, Alignment.MIDDLE_CENTER);
			layout.addComponent(control);
			layout.setComponentAlignment(control, Alignment.BOTTOM_RIGHT);
			anspr.setContent(layout);
			
			nameAnspr.setImmediate(true);
			nameAnspr.setInputPrompt(nameInput);
			nameAnspr.setMaxLength(30);
			
			telefonAnspr.setImmediate(true);
			telefonAnspr.setInputPrompt(telefonInput);
			telefonAnspr.setMaxLength(20);	
			
			handyAnspr.setImmediate(true);
			handyAnspr.setInputPrompt(handyInput);
			handyAnspr.setMaxLength(20);
			
			faxAnspr.setImmediate(true);
			faxAnspr.setInputPrompt(faxInput);
			faxAnspr.setMaxLength(20);
			
			verwerfen.addClickListener(new ClickListener() {				
				@Override
				public void buttonClick(ClickEvent event) {
					UI.getCurrent().removeWindow(anspr);							
				}
			});
			  
			
			
			speichern.addClickListener(new ClickListener()
			{
				public void buttonClick(ClickEvent event)
				{
					Ansprechpartner ans = new Ansprechpartner();
					ans.setName(nameInput);
					ans.setTelefon(telefonInput);
					ans.setHandy(handyInput);
					ans.setFax(faxInput);
					ans.setLieferant(lieferant);
					try {
						Ansprechpartnerverwaltung.getInstance().createAnsprechpartner(ans);
					} catch (Exception e) {
						System.out.println(e);
						throw new NullPointerException("Bitte g�ltige Werte eingeben");

					}				
					
					UI.getCurrent().removeWindow(anspr);
					ViewHandler.getInstance().switchView(LieferantSuche.class, new ViewDataObject<Lieferant>(lieferant));				
					}
			});
			
			

	        nameAnspr.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                nameInput = valueString;
	            }
	        });
	        
	        telefonAnspr.addValueChangeListener(new ValueChangeListener() {
	            @Override
	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());
	                telefonInput = valueString;
	            }
	        });
	        
	        handyAnspr.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                handyInput = valueString;
	            }
	        });
	        
	        faxAnspr.addValueChangeListener(new ValueChangeListener() {

	            public void valueChange(final ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());

	                faxInput = valueString;
	            }
	        });
		}
	});
	}

	/* (non-Javadoc)
	 * @see de.hska.awp.palaver2.util.View#getViewParam(de.hska.awp.palaver2.util.ViewData)
	 */
	@Override
	public void getViewParam(ViewData data)
	{
		lieferant = (Lieferant) ((ViewDataObject<?>)data).getData();
		if(lieferant.getId() == null) {
			try {
				lieferant = Lieferantenverwaltung.getInstance().getLastLieferant();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		name.setValue(lieferant.getName());
		name.setEnabled(false);
			
		bezeichnung.setValue(lieferant.getBezeichnung());
		bezeichnung.setEnabled(false);
			
		kundennummer.setValue(lieferant.getKundennummer());
		kundennummer.setEnabled(false);
			
		strasse.setValue(lieferant.getStrasse());
		strasse.setEnabled(false);
			
		plz.setValue(lieferant.getPlz());
		plz.setEnabled(false);
		
		ort.setValue(lieferant.getOrt());
		ort.setEnabled(false);
			
		email.setValue(lieferant.getEmail());
		email.setEnabled(false);
		if(!lieferant.getEmail().equals(""))
			emailSenden.setEnabled(true);
		
		
		telefon.setValue(lieferant.getTelefon());
		telefon.setEnabled(false);
			
		fax.setValue(lieferant.getFax());
		fax.setEnabled(false);
		
		notiz.setValue(lieferant.getNotiz());
		notiz.setEnabled(false);
		
		mehrereliefertermine.setValue(lieferant.getMehrereliefertermine());
		mehrereliefertermine.setEnabled(false);
		
		BeanItemContainer<Ansprechpartner> container;

		try {
			container = new BeanItemContainer<Ansprechpartner>(Ansprechpartner.class, Ansprechpartnerverwaltung.getInstance().getAnsprechpartnerByLieferant(lieferant));
			ansprechpartner.setContainerDataSource(container);
			ansprechpartner.setVisibleColumns(new Object[] {"name", "telefon", "handy", "fax"});
			ansprechpartner.sort(new Object[] {"id"}, new boolean[] {true});
			ansprechpartner.setColumnCollapsingAllowed(true);
			ansprechpartner.setColumnCollapsed(handyAnspr, false);
			ansprechpartner.setColumnCollapsed(faxAnspr, false);				
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}
