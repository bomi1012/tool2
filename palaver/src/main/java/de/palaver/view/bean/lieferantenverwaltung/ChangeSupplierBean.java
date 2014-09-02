package de.palaver.view.bean.lieferantenverwaltung;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.management.info.person.Adresse;
import de.palaver.management.info.person.Kontakte;
import de.palaver.management.supplier.Ansprechpartner;
import de.palaver.management.supplier.Supplier;
import de.palaver.management.supplier.service.AnsprechpartnerService;
import de.palaver.view.bean.util.ChangeFieldsPersonAbstract;
import de.palaver.view.bean.util.HTMLComponents;
import de.palaver.view.bean.util.interfaces.IChangeViewPage;
import de.palaver.view.layout.popup.YesNoPopup;

public class ChangeSupplierBean extends ChangeFieldsPersonAbstract implements View, ValueChangeListener, IChangeViewPage {
	private static final long serialVersionUID = 63219450376751801L;
	private static final String TEXT = "Neuer Lieferant wurde erstellt. <br> " +
			"M�chten Sie den Ansprechpartner hinzuf�gen?";
	private static final String TEXT_FIELD_LIEFERANT_NAME = "Lieferantname";
	private boolean m_toCreate;
	
	private VerticalLayout m_innerBoxSupplier;
	private Button m_addUserButton;
	private Label m_subHeadContactPerson;
	
	private VerticalLayout m_innerBoxPerson;
	private List<Ansprechpartner> m_contactPersonList;

	private BeanItemContainer<Ansprechpartner> m_container;

	public ChangeSupplierBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		clickListener();
		changeListner();
	}

	public void init() {
		resetMarkAsChange();
		m_toCreate = true;	
		m_supplier = new Supplier(new Adresse(), new Kontakte());
	} 
	
	private void componetsManager() {
		m_headLine = title("einen neuen Lieferanten hinzuf�gen", STYLE_HEADLINE_STANDART);		
		m_subHeadPersonDaten = title("Pers�nliche Daten", STYLE_HEADLINE_SUB);
		m_nameField = textField("Lieferantname", WIDTH_FULL, true, "Lieferantname", 45);
		m_numberField = textField("Lieferantnummer", WIDTH_FULL, false, "Lieferantnummer", 45);
		m_descriptionField = textField("Bezeichnung", WIDTH_FULL, false, "Bezeichnung", 45);
		m_commentField = textArea("Kommentar", WIDTH_FULL, "60", false, "Kommentar", 300);
		
		getContactDataDefinition();
		getAddressDataDefinition();

		m_subHeadContactPerson = title("Alle Ansprechpartnern", STYLE_HEADLINE_SUB);
		m_addUserButton = buttonAsIcon(" neuer Ansprechpartner", BaseTheme.BUTTON_LINK, 
				"cursor-hand lieferant", "icons/user_add.png", true);
					
		m_leftVLayout = vertikalLayoutBuilder(0, "90%");		
		m_centerVLayout = vertikalLayoutBuilder(1, "90%");
		m_rightVLayout = vertikalLayoutBuilder(2, "90%");
		
		m_table = HTMLComponents.table(true, true, WIDTH_FULL, null);
		m_control = controlPanel(this);	
	}

	private void templateBuilder() {		
		m_innerBoxSupplier = new VerticalLayout();		
		m_innerBoxSupplier.setWidth(WIDTH_FULL);
		m_innerBoxSupplier.setSpacing(true);
		m_innerBoxSupplier.setMargin(true);		
		
		m_innerBoxSupplier.addComponent(m_headLine);
		m_innerBoxSupplier.addComponent(new Hr());
		m_innerBoxSupplier.addComponent(horizontalLayoutBuilder());	
		m_innerBoxSupplier.addComponent(new Hr());
		m_innerBoxSupplier.addComponent(m_control);
		
		m_innerBoxSupplier.setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);

		m_innerBoxPerson = new VerticalLayout();	
		m_innerBoxPerson.setWidth(WIDTH_FULL);
		m_innerBoxPerson.setSpacing(true);
		m_innerBoxPerson.setMargin(true);	
		
		m_innerBoxPerson.addComponent(m_subHeadContactPerson);
		m_innerBoxPerson.addComponent(new Hr());
		m_innerBoxPerson.addComponent(m_addUserButton);
		m_innerBoxPerson.addComponent(m_table);		
				
		addComponent(m_innerBoxSupplier);
		addComponent(m_innerBoxPerson);
		
		setComponentAlignment(m_innerBoxSupplier, Alignment.MIDDLE_CENTER);		
		setComponentAlignment(m_innerBoxPerson, Alignment.MIDDLE_CENTER);	
		
		componentPersonShowOrHide(false);
	}

	@SuppressWarnings("serial")
	private void clickListener() {
		m_buttonSpeichern.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (isMarkAsChange() && validiereEingabe()) {
					saveItem();	
					close();
				}							
			}

			private boolean validiereEingabe() {
				if (StringUtils.isBlank(m_nameField.getValue())) {
					message(MESSAGE_LEER_ARG_1, TEXT_FIELD_LIEFERANT_NAME);
					return false;
				}
				return true;
			}
			
			private void saveItem() {
				try {
					if (m_toCreate) {
						addToDB(m_supplier.getAdresse(), 0);
						addToDB(m_supplier.getKontakte(), 0);
						addToDB(m_supplier, 0);
						if (ChangeSupplierBean.this.getParent() instanceof Window) {
							close();
						} else {
							windowModalYesNo();	
						}
					} else {
						if (m_supplier.getKontakte().getId() != null) { 
							if (!checkFields(m_supplier.getKontakte())) {
								removeFromDB(m_supplier.getKontakte());	
								m_supplier.setKontakte(new Kontakte());
							} else {
								updateInDB(m_supplier.getKontakte());							
							}						
						} else {
							addToDB(m_supplier.getKontakte(), 0);
						}
						
						if (m_supplier.getAdresse().getId() != null) { 
							if (!checkFields(m_supplier.getAdresse())) {
								removeFromDB(m_supplier.getAdresse());	
								m_supplier.setAdresse(new Adresse());
							} else {
								updateInDB(m_supplier.getAdresse());							
							}							
						} else {
							addToDB(m_supplier.getAdresse(), 0);
						}					
						updateInDB(m_supplier);
						close();
					}
					message(MESSAGE_SUSSEFULL_ARG_1, "Lieferant");
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});	
		
		m_buttonVerwerfen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();	
			}
		});
		
		m_buttonDeaktiviren.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				windowModalYesNoRemove(m_supplier);	
			}
		});
	}	

	@SuppressWarnings("serial")
	private void changeListner() {
		m_nameField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.setName((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_numberField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.setLieferantnummer((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_commentField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.setNotiz((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_descriptionField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.setBezeichnung((String) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_mehrerLieferterminCheckbox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.setMehrereliefertermine((Boolean) event.getProperty().getValue());
				markAsChange();
			}
		});
		m_telephonField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getKontakte().setTelefon((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_handyField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getKontakte().setHandy((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_faxField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getKontakte().setFax((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_emailField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getKontakte().setEmail((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_webField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getKontakte().setWww((String) event.getProperty().getValue());
				markKontakteAsChange();
			}
		});
		m_streetField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getAdresse().setStrasse((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_housenumberField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getAdresse().setHausnummer((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_cityField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getAdresse().setStadt((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_plzField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getAdresse().setPlz((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
		m_countryField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {
				m_supplier.getAdresse().setLand((String) event.getProperty().getValue());
				markAdresseAsChange();
			}
		});
	}	
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Ansprechpartner>(Ansprechpartner.class, 
					AnsprechpartnerService.getInstance().getAllAnsprechpartnersByLieferantId(m_supplier.getId()));
			setTable();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void setTable() {
		m_table.setContainerDataSource(m_container);
		m_table.setVisibleColumns(new Object[] { "name", "adresse", "kontakt", "bezeichnung" });
		m_table.sort(new Object[] { "name" }, new boolean[] { true });	
	}
		
	@SuppressWarnings("serial")
	private void componentPersonShowOrHide(boolean showTable) {
		if(m_toCreate) {
			m_innerBoxPerson.setVisible(false);
		} else {
			m_innerBoxPerson.setVisible(true);
			m_addUserButton.addClickListener(new ClickListener() {			
				@Override
				public void buttonClick(ClickEvent event) {
					goToContactPersonBean(m_supplier);  
				}
			});
			if(!showTable) {
				m_table.setVisible(false);
			} else {
				m_table.setVisible(true);
				m_table.addValueChangeListener(new ValueChangeListener() {			
					@Override
					public void valueChange(ValueChangeEvent event) {
						if (event.getProperty().getValue() != null) {
							m_contactPerson = (Ansprechpartner) event.getProperty().getValue();							
						}
					}
				});
				
				m_table.addItemClickListener(new ItemClickListener() {
					@Override
					public void itemClick(ItemClickEvent event) {
						if (event.isDoubleClick()) {
							goToContactPersonBean(m_contactPerson);
						}
					}
				});
				beans();
			}
		}		
	}
	
	private void close() {
		if (ChangeSupplierBean.this.getParent() instanceof Window) {
			((Window) ChangeSupplierBean.this.getParent()).close();
		} else {
			ViewHandler.getInstance().switchView(ShowSuppliersBean.class);
		}
	}
			
	@SuppressWarnings("serial")
	private void windowModalYesNo() {
		m_window = windowUI("", true, false, "450", "180");		
		m_yesNoPopup = new YesNoPopup("32x32/user.png", TEXT);
		addComponent(m_yesNoPopup);
		m_window.setContent(m_yesNoPopup);
		
		UI.getCurrent().addWindow(m_window);
		m_yesNoPopup.m_yesButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				m_window.close();
				goToContactPersonBean(m_supplier);
			}
		});
		m_yesNoPopup.m_noButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				m_window.close();
				ViewHandler.getInstance().switchView(ShowSuppliersBean.class);
			}
		});
	}
	
	private void goToContactPersonBean(Object obj) {
		if (obj instanceof Supplier) {
			ViewHandler.getInstance()
				.switchView(ChangeContactPersonBean.class, new ViewDataObject<Supplier>((Supplier) obj));
		} else if (obj instanceof Ansprechpartner) {
			ViewHandler.getInstance()
				.switchView(ChangeContactPersonBean.class, 
						new ViewDataObject<Ansprechpartner>((Ansprechpartner) obj, m_supplier));
		} 
	}

	@Override
	public void valueChange(ValueChangeEvent event) { }
	@Override
	public void getViewParam(ViewData data) {
		if(((ViewDataObject<?>) data).getData() instanceof Supplier) { 
			m_supplier = (Supplier)((ViewDataObject<?>) data).getData();
			setNewInfo();			
			setValueToComponent(getData());

			try {
				m_contactPersonList = AnsprechpartnerService.getInstance().getAllAnsprechpartnersByLieferantId(m_supplier.getId());
				if(m_contactPersonList.size() > 0) {
					componentPersonShowOrHide(true); 
				} else {
					componentPersonShowOrHide(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		resetMarkAsChange();
	}

	private void setNewInfo() {
		getData().clear();
		getData().put(m_nameField, m_supplier.getName());
		getData().put(m_numberField, m_supplier.getLieferantnummer());
		getData().put(m_descriptionField, m_supplier.getBezeichnung());
		getData().put(m_commentField, m_supplier.getNotiz());
		getData().put(m_mehrerLieferterminCheckbox, m_supplier.isMehrereliefertermine());
		
		if (m_supplier.getKontakte() != null) {
			getData().put(m_telephonField, m_supplier.getKontakte().getTelefon());
			getData().put(m_handyField, m_supplier.getKontakte().getHandy());
			getData().put(m_faxField, m_supplier.getKontakte().getFax());
			getData().put(m_emailField, m_supplier.getKontakte().getEmail());
			getData().put(m_webField, m_supplier.getKontakte().getWww());
		} else {
			m_supplier.setKontakte(new Kontakte());			
		}
		
		if (m_supplier.getAdresse() != null) {	
			getData().put(m_streetField, m_supplier.getAdresse().getStrasse());
			getData().put(m_housenumberField, m_supplier.getAdresse().getHausnummer());
			getData().put(m_cityField, m_supplier.getAdresse().getStadt());
			getData().put(m_plzField, m_supplier.getAdresse().getPlz());
			getData().put(m_countryField, m_supplier.getAdresse().getLand());
		} else {
			m_supplier.setAdresse(new Adresse());			
		}
		
		m_toCreate = false;		
		m_headLine.setValue("Mitarbeiter bearbeiten");
		m_buttonDeaktiviren.setVisible(true);	
	}
}
