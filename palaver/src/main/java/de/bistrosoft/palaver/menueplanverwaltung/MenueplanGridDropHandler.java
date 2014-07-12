package de.bistrosoft.palaver.menueplanverwaltung;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

import de.bistrosoft.palaver.regelverwaltung.domain.Regel;
import de.palaver.Application;
import de.palaver.management.emploee.Rolle;
import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.DDGridLayout.GridLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.drophandlers.AbstractDefaultLayoutDropHandler;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

@SuppressWarnings("serial")
public class MenueplanGridDropHandler extends AbstractDefaultLayoutDropHandler {

	private final Alignment dropAlignment;

	/**
	 * Default constructor
	 */
	public MenueplanGridDropHandler() {
		dropAlignment = Alignment.MIDDLE_CENTER;
	}

	/**
	 * Constructor
	 * 
	 * @param dropCellAlignment
	 *            The cell alignment of the component after it has been dropped
	 */
	public MenueplanGridDropHandler(Alignment dropCellAlignment) {
		this.dropAlignment = Alignment.MIDDLE_CENTER;
	}

	@Override
	protected void handleComponentReordering(DragAndDropEvent event) {
		GridLayoutTargetDetails details = (GridLayoutTargetDetails) event
				.getTargetDetails();

		final DDGridLayout layout = (DDGridLayout) details.getTarget();

		final MenueplanGridLayout menueplan = (MenueplanGridLayout) layout
				.getParent().getParent();

		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
				.getTransferable();

		// Finde Quellkomponente, Quellzeile, Quellspalte
		Component sourceComp = transferable.getComponent();
		Integer sourceRow = -1;
		Integer sourceColumn = -1;

		final int COLUMNS = layout.getColumns();
		final int ROWS = layout.getRows();

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if (sourceComp.equals(layout.getComponent(col, row))) {
					sourceColumn = col;
					sourceRow = row;
				}
			}
		}

		// Finde Zielkomponente, Zielzeile, Zielspalte
		Integer destRow = details.getOverRow();
		Integer destColumn = details.getOverColumn();
		Component destComp = details.getOverComponent();

		// Prüfe, dass Komponente nicht in die ersten zwei Zeilen gedropped wird
		// Prüfe, dass Zielkomponente ungleich Quelkomponente ist
		if (!(destRow < 2) && !(destColumn < 1) && (destComp != sourceComp)) {
			// Menüs vertauschen
			menueplan
					.vertauscheMenue(sourceComp, destComp, destColumn, destRow);

			// Wenn Zielkomponente vorhanden füge diese ein
			if (destComp != null) {
				layout.addComponent(destComp, sourceColumn, sourceRow);
				layout.setComponentAlignment(destComp, dropAlignment);
			}
			if (destComp instanceof MenueComponent) {
				MenueComponent comp = (MenueComponent) destComp;
				comp.isChanged(true);
				comp.setCol(sourceColumn);
				comp.setRow(sourceRow);
				comp.pruefeRegeln(menueplan);
				menueplan.pruefeRegeln(comp);
				pruefeIgnorierbar(comp);
				

				final Integer finalSourceColumn = sourceColumn;
				final Integer finalSourceRow = sourceRow;
				final Integer finalDestColumn = destColumn;
				final Integer finalDestRow = destRow;
				final Component finalDestComp = destComp;
				final Component finalSourceComp = sourceComp;
				
				if (comp.getFehlerRegeln() != null) {
					for (Regel r : comp.getFehlerRegeln()) {
						if (!r.getIgnorierbar()) {
							ConfirmDialog.show(UI.getCurrent(),
									"Regel verletzt",
									"Das Menü kann an dieser Stelle nicht eingefüht werden. Fehlermeldung: ("
											+ r.getFehlermeldung() + ")", "OK",
									"Ignorieren", new ConfirmDialog.Listener() {

										public void onClose(ConfirmDialog dialog) {
											if (dialog.isConfirmed()) {
												layout.removeComponent(finalSourceComp);
												layout.removeComponent(finalDestComp);

												layout.addComponent(
														finalSourceComp,
														finalSourceColumn,
														finalSourceRow);
												layout.addComponent(
														finalDestComp,
														finalDestColumn,
														finalDestRow);

												if (finalDestComp instanceof MenueComponent) {
													MenueComponent comp = (MenueComponent) finalDestComp;
													comp.isChanged(true);
													comp.setCol(finalSourceColumn);
													comp.setRow(finalSourceRow);
													comp.pruefeRegeln(menueplan);
													menueplan
															.pruefeRegeln(comp);
													pruefeIgnorierbar(comp);
												}
												if (finalSourceComp instanceof MenueComponent) {
													MenueComponent comp = (MenueComponent) finalSourceComp;
													comp.isChanged(true);
													comp.setCol(finalDestColumn);
													comp.setRow(finalDestRow);
													comp.pruefeRegeln(menueplan);
													menueplan
															.pruefeRegeln(comp);
												}
											} else {
												if (!((Application) UI
														.getCurrent().getData())
														.userHasPersmission(Rolle.ADMINISTRATOR)) {
													layout.removeComponent(finalSourceComp);
													layout.removeComponent(finalDestComp);

													layout.addComponent(
															finalSourceComp,
															finalSourceColumn,
															finalSourceRow);
													layout.addComponent(
															finalDestComp,
															finalDestColumn,
															finalDestRow);

													if (finalDestComp instanceof MenueComponent) {
														MenueComponent comp = (MenueComponent) finalDestComp;
														comp.isChanged(true);
														comp.setCol(finalSourceColumn);
														comp.setRow(finalSourceRow);
														comp.pruefeRegeln(menueplan);
														menueplan
																.pruefeRegeln(comp);
														pruefeIgnorierbar(comp);
													}
													if (finalSourceComp instanceof MenueComponent) {
														MenueComponent comp = (MenueComponent) finalSourceComp;
														comp.isChanged(true);
														comp.setCol(finalDestColumn);
														comp.setRow(finalDestRow);
														comp.pruefeRegeln(menueplan);
														menueplan
																.pruefeRegeln(comp);
													}
													
													((Application) UI.getCurrent().getData())
													.showDialog("Ignorieren nur als Administrator möglich!");

												}
											}
										}
									});

						}
					}

				}
			}
			if (sourceComp instanceof MenueComponent) {
				MenueComponent comp = (MenueComponent) sourceComp;
				comp.isChanged(true);
				comp.setCol(destColumn);
				comp.setRow(destRow);
				comp.pruefeRegeln(menueplan);
				menueplan.pruefeRegeln(comp);

				final Integer finalSourceColumn = sourceColumn;
				final Integer finalSourceRow = sourceRow;
				final Integer finalDestColumn = destColumn;
				final Integer finalDestRow = destRow;
				final Component finalDestComp = destComp;
				final Component finalSourceComp = sourceComp;

				if (comp.getFehlerRegeln() != null) {
					for (Regel r : comp.getFehlerRegeln()) {
						if (!r.getIgnorierbar()) {
							ConfirmDialog.show(UI.getCurrent(),
									"Regel verletzt",
									"Das Menü kann an dieser Stelle nicht eingefüht werden. Fehlermeldung: ("
											+ r.getFehlermeldung() + ")", "OK",
									"Ignorieren", new ConfirmDialog.Listener() {

										public void onClose(ConfirmDialog dialog) {
											if (dialog.isConfirmed()) {
												layout.removeComponent(finalSourceComp);
												layout.removeComponent(finalDestComp);

												layout.addComponent(
														finalSourceComp,
														finalSourceColumn,
														finalSourceRow);
												layout.addComponent(
														finalDestComp,
														finalDestColumn,
														finalDestRow);

												if (finalDestComp instanceof MenueComponent) {
													MenueComponent comp = (MenueComponent) finalDestComp;
													comp.isChanged(true);
													comp.setCol(finalDestColumn);
													comp.setRow(finalDestRow);
													comp.pruefeRegeln(menueplan);
													menueplan
															.pruefeRegeln(comp);
													pruefeIgnorierbar(comp);
												}
												if (finalSourceComp instanceof MenueComponent) {
													MenueComponent comp = (MenueComponent) finalSourceComp;
													comp.isChanged(true);
													comp.setCol(finalSourceColumn);
													comp.setRow(finalSourceRow);
													comp.pruefeRegeln(menueplan);
													menueplan
															.pruefeRegeln(comp);
												}
											} else {
												if (!((Application) UI
														.getCurrent().getData())
														.userHasPersmission(Rolle.ADMINISTRATOR)) {
													layout.removeComponent(finalSourceComp);
													layout.removeComponent(finalDestComp);

													layout.addComponent(
															finalSourceComp,
															finalSourceColumn,
															finalSourceRow);
													layout.addComponent(
															finalDestComp,
															finalDestColumn,
															finalDestRow);

													if (finalDestComp instanceof MenueComponent) {
														MenueComponent comp = (MenueComponent) finalDestComp;
														comp.isChanged(true);
														comp.setRow(finalDestRow);
														comp.setCol(finalDestColumn);
														comp.pruefeRegeln(menueplan);
														menueplan
																.pruefeRegeln(comp);
														pruefeIgnorierbar(comp);
													}
													if (finalSourceComp instanceof MenueComponent) {
														MenueComponent comp = (MenueComponent) finalSourceComp;
														comp.isChanged(true);
														comp.setCol(finalSourceColumn);
														comp.setRow(finalSourceRow);
														comp.pruefeRegeln(menueplan);
														menueplan
																.pruefeRegeln(comp);
													}
													
													((Application) UI.getCurrent().getData())
													.showDialog("Ignorieren nur als Administrator möglich!");

												}
											}
										}
									});

						}
					}

				}

			}
		}
	}

	private Boolean pruefeIgnorierbar(MenueComponent mc) {
		final Boolean ok = true;

		if (mc != null) {
			if (mc.getFehlerRegeln() != null) {
				for (Regel r : mc.getFehlerRegeln()) {
					if (!r.getIgnorierbar()) {
						ConfirmDialog.show(UI.getCurrent(), "Regel verletzt",
								"Das Menü kann an dieser Stelle nicht eingefüht werden. Fehlermeldung: ("
										+ r.getFehlermeldung() + ")", "OK",
								"Ignorieren", new ConfirmDialog.Listener() {

									public void onClose(ConfirmDialog dialog) {
										if (dialog.isConfirmed()) {

										} else {

										}
									}
								});
					}
				}
			}
		}
		return ok;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void handleDropFromLayout(DragAndDropEvent event) {
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
				.getTransferable();
		GridLayoutTargetDetails details = (GridLayoutTargetDetails) event
				.getTargetDetails();
		DDGridLayout layout = (DDGridLayout) details.getTarget();
		Component source = event.getTransferable().getSourceComponent();
		Component comp = transferable.getComponent();

		if (comp == layout) {
			// Dropping myself on myself, if parent is absolute layout then
			// move
			if (comp.getParent() instanceof DDAbsoluteLayout) {
				MouseEventDetails mouseDown = transferable.getMouseDownEvent();
				MouseEventDetails mouseUp = details.getMouseEvent();
				int movex = mouseUp.getClientX() - mouseDown.getClientX();
				int movey = mouseUp.getClientY() - mouseDown.getClientY();

				DDAbsoluteLayout parent = (DDAbsoluteLayout) comp.getParent();
				ComponentPosition position = parent.getPosition(comp);

				float x = position.getLeftValue() + movex;
				float y = position.getTopValue() + movey;
				position.setLeft(x, Sizeable.UNITS_PIXELS);
				position.setTop(y, Sizeable.UNITS_PIXELS);

				return;
			}

		} else {

			// Check that we are not dragging an outer layout into an inner
			// layout
			Component parent = layout.getParent();
			while (parent != null) {
				if (parent == comp) {
					return;
				}
				parent = parent.getParent();
			}

			// Remove component from its source
			if (source instanceof ComponentContainer) {
				ComponentContainer sourceLayout = (ComponentContainer) source;
				sourceLayout.removeComponent(comp);
			}
		}

		int row = details.getOverRow();
		int column = details.getOverColumn();
		addComponent(event, comp, column, row);
	}

	protected void addComponent(DragAndDropEvent event, Component component,
			int column, int row) {
	}
}
