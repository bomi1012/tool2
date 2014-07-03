package de.palaver.view.lieferantenverwaltung.neu;

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

import de.hska.awp.palaver2.util.IConstants;
import de.hska.awp.palaver2.util.View;
import de.hska.awp.palaver2.util.ViewData;
import de.hska.awp.palaver2.util.ViewDataObject;
import de.hska.awp.palaver2.util.ViewHandler;
import de.palaver.Application;
import de.palaver.domain.person.Adresse;
import de.palaver.domain.person.Kontakte;
import de.palaver.domain.person.lieferantenverwaltung.Ansprechpartner;
import de.palaver.domain.person.lieferantenverwaltung.Lieferant;
import de.palaver.service.person.lieferantenverwaltung.AnsprechpartnerService;
import de.palaver.view.layout.popup.YesNoPopup;
import de.palaver.view.manager.HTMLComponents;

public class ChangeSupplierBean extends ChangeFieldsAbstract implements View, ValueChangeListener {
	private static final long serialVersionUID = 63219450376751801L;
	private static final String TEXT = "Neuer Lieferant wurde erstellt. <br> " +
			"Möchten Sie den Ansprechpartner hinzufügen?";
	private boolean m_toCreate;
	
	private VerticalLayout m_innerBoxSupplier;
	private Button m_addUserButton;
	private Label m_subHeadContactPerson;
	
	private VerticalLayout m_innerBoxPerson;
	private List<Ansprechpartner> m_contactPersonList;
	private YesNoPopup m_yesNoPopup;
	private BeanItemContainer<Ansprechpartner> m_container;

	public ChangeSupplierBean() {
		super();
		init();
		componetsManager();
		templateBuilder();
		listeners();
	}

	public void init() {
		m_toCreate = true;
		m_supplier = new Lieferant();
	} 
	
	private void componetsManager() {
		m_headLine = title("Lieferant ändern", STYLE_HEADLINE_STANDART);
		
		m_subHeadPersonDaten = title("Persönliche Daten", STYLE_HEADLINE_SUB);
		m_nameField = textField("Lieferantname", WIDTH_FULL, true, "Lieferantname", this);
		m_numberField = textField("Lieferantnummer", WIDTH_FULL, false, "Lieferantnummer", this);
		m_descriptionField = textField("Bezeichnung", WIDTH_FULL, false, "Bezeichnung", this);
		m_commentField = textArea("Kommentar", WIDTH_FULL, "60", false, "Kommentar", this);
		
		getContactDataDefinition();
		getAddressDataDefinition();

		m_subHeadContactPerson = title("Alle Ansprechpartnern", STYLE_HEADLINE_SUB);
		m_addUserButton = buttonAsIcon(" neuer Ansprechpartner", BaseTheme.BUTTON_LINK, 
				"cursor-hand lieferant", "icons/user_add.png");
					
		m_leftVLayout = vertikalLayoutBuilder(0, "90%");		
		m_centerVLayout = vertikalLayoutBuilder(1, "90%");
		m_rightVLayout = vertikalLayoutBuilder(2, "90%");
		
		m_table = HTMLComponents.table(true, true, "100%", null);
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
		setComponentAlignment(m_control, Alignment.MIDDLE_RIGHT);
		
		componentPersonShowOrHide(false);
	}

	@SuppressWarnings("serial")
	private void listeners() {
		m_buttonSpeichern.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (validiereEingabe()) {
					saveItem();				
				}							
			}

			private boolean validiereEingabe() {
				if (StringUtils.isBlank(m_nameField.getValue())) {
					((Application) UI.getCurrent().getData()).showDialog(IConstants.INFO_LIEFERANT_NAME);
					return false;
				}
				return true;
			}
			
			private void saveItem() {
				if (m_toCreate) {
					addToDB(new Kontakte());
					addToDB(new Adresse());
					addToDB(new Lieferant());
					if (ChangeSupplierBean.this.getParent() instanceof Window) {
						close();
					} else {
						windowModalYesNo();	
					}
				} else {
					if (m_supplier.getKontakte() != null) {
						if (!checkFields(m_supplier.getKontakte())) {
							removeFromDB(m_supplier.getKontakte());	
							m_supplier.setKontakte(null);
						} else {
							updateInDB(m_supplier.getKontakte());							
						}
					} else {
						addToDB(new Kontakte());
						m_supplier.setKontakte(m_kontakte);
					}
					
					if (m_supplier.getAdresse() != null) {
						if (!checkFields(m_supplier.getAdresse())) {
							removeFromDB(m_supplier.getAdresse());	
							m_supplier.setAdresse(null);
						} else {
							updateInDB(m_supplier.getAdresse());							
						}
					} else {
						addToDB(new Adresse());
						m_supplier.setAdresse(m_adresse);
					}
					
					updateInDB(m_supplier);
					((Application) UI.getCurrent().getData()).showDialog(String.format(MESSAGE_SUSSEFULL_ARG_1, 
							"Lieferant"));	
					close();
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
				//TODO: visible = false --> implementieren!
				//sehe m_control
			}
		});
	}
	
	
	
	private void beans() {
		try {
			m_container = new BeanItemContainer<Ansprechpartner>(Ansprechpartner.class, 
					AnsprechpartnerService.getInstance().getAllAnsprechpartnersByLieferantId(m_supplier.getId()));
			setTable();
		} catch (Exception e) {
			LOG.error(e.toString());
		}		
	}
	
	private void setTable() {
		m_table.setContainerDataSource(m_container);
		m_table.setVisibleColumns(new Object[] { "name", "adresse", "kontakte", "bezeichnung" });
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
			Window win = (Window) ChangeSupplierBean.this.getParent();
			win.close();
		} else {
			ViewHandler.getInstance().switchView(ShowSupplierBean.class);
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
				ViewHandler.getInstance()
					.switchView(ShowSupplierBean.class);
			}
		});
	}
	
	private void goToContactPersonBean(Object obj) {
		if (obj instanceof Lieferant) {
			ViewHandler.getInstance()
				.switchView(ChangeContactPersonBean.class, new ViewDataObject<Lieferant>((Lieferant) obj));
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
		if(((ViewDataObject<?>) data).getData() instanceof Lieferant) { 
			m_supplier = (Lieferant)((ViewDataObject<?>) data).getData();
			
			m_nameField.setValue(m_supplier.getName());
			m_descriptionField.setValue(m_supplier.getBezeichnung());
			m_numberField.setValue(m_supplier.getLieferantnummer());
			m_commentField.setValue(m_supplier.getNotiz());
			m_mehrerLieferterminCheckbox.setValue(m_supplier.isMehrereliefertermine());
			
			if(m_supplier.getKontakte() != null) {
				m_telephonField.setValue(m_supplier.getKontakte().getTelefon());
				m_handyField.setValue(m_supplier.getKontakte().getHandy());
				m_faxField.setValue(m_supplier.getKontakte().getFax());
				m_emailField.setValue(m_supplier.getKontakte().getEmail());
				m_webField.setValue(m_supplier.getKontakte().getWww());
			}
			
			if(m_supplier.getAdresse() != null) {
				m_streetField.setValue(m_supplier.getAdresse().getStrasse());
				m_housenumberField.setValue(m_supplier.getAdresse().getHausnummer());
				m_cityField.setValue(m_supplier.getAdresse().getStadt());
				m_plzField.setValue(m_supplier.getAdresse().getPlz());
				m_countryField.setValue(m_supplier.getAdresse().getLand());
			}
			m_toCreate = false;			
			try {
				m_contactPersonList = AnsprechpartnerService.getInstance().getAllAnsprechpartnersByLieferantId(m_supplier.getId());
				if(m_contactPersonList.size() > 0) {
					componentPersonShowOrHide(true); 
				} else {
					componentPersonShowOrHide(false);
				}
			} catch (Exception e) {
				LOG.error("KontaktPerson_Error: " + e.toString());
			} 
		}
	}

}
