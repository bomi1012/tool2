package de.bistrosoft.palaver.regelverwaltung.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.ui.UI;

import de.bistrosoft.palaver.menueplanverwaltung.MenueComponent;
import de.bistrosoft.palaver.menueplanverwaltung.MenueplanGridLayout;
import de.bistrosoft.palaver.menueplanverwaltung.domain.Menue;
import de.bistrosoft.palaver.regelverwaltung.service.Regelverwaltung;
import de.bistrosoft.palaver.rezeptverwaltung.domain.Fussnote;
import de.hska.awp.palaver2.util.IConstants;
import de.palaver.Application;
import fi.jasoft.dragdroplayouts.DDGridLayout;

public class Regel {
	Long id;
	List<Integer> zeilenlist;
	String zeilen;
	List<Integer> spaltenlist;
	String spalten;
	String regeltyp;
	String operator;
	List<String> kriterienlist;
	String kriterien;
	String fehlermeldung;
	Boolean aktiv;
	Boolean ignorierbar;

	public Regel() {

	}

	public Boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(Boolean aktiv) {
		this.aktiv = aktiv;
	}

	public Boolean getIgnorierbar() {
		return ignorierbar;
	}

	public void setIgnorierbar(Boolean ignorierbar) {
		this.ignorierbar = ignorierbar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZeile() {
		return zeilen;
	}

	public void setZeile(String zeilen) {
		this.zeilen = zeilen;
	}

	public String getSpalte() {
		return spalten;
	}

	public void setSpalte(String spalten) {
		this.spalten = spalten;
	}

	public List<Integer> getZeilen() {
		return zeilenlist;
	}

	public void setZeilen(List<Integer> zeilen) {
		this.zeilenlist = zeilen;
	}

	public List<Integer> getSpalten() {
		return spaltenlist;
	}

	public void setSpalten(List<Integer> spalten) {
		this.spaltenlist = spalten;
	}

	public String getRegeltyp() {
		return regeltyp;
	}

	public void setRegeltyp(String regeltyp) {
		this.regeltyp = regeltyp;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getKriterien() {
		return kriterien;
	}

	public void setKriterien(String kriterien) {
		this.kriterien = kriterien;
	}

	public String getFehlermeldung() {
		return fehlermeldung;
	}

	public void setFehlermeldung(String fehlermeldung) {
		this.fehlermeldung = fehlermeldung;
	}

	public Regel(Long id, String regeltyp, String zeilen, String spalten,
			String operator, String kriterien, String fehlermeldung,
			Boolean aktiv, Boolean ignorierbar) {
		this.id = id;
		this.regeltyp = regeltyp;
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.operator = operator;
		this.kriterien = kriterien;
		this.fehlermeldung = fehlermeldung;
		this.aktiv = aktiv;
		this.ignorierbar = ignorierbar;
		this.kriterienlist = Arrays.asList(kriterien.split(", "));

		// FÃ¼lle List<Integer>
		// Zeilen
		int topRows = 2;
		List<String> strZeilen = Arrays.asList(zeilen.split(","));
		// List<String> strZeilen=Arrays.asList(zeilen.split("\\s*,\\s*"));
		zeilenlist = new ArrayList<Integer>();

		for (String s : strZeilen) {
			if (s.trim().equals(IConstants.INFO_REGEL_ZEILE_1)) {
				zeilenlist.add(0 + topRows);
			} else if (s.trim().equals(IConstants.INFO_REGEL_ZEILE_2)) {
				zeilenlist.add(1 + topRows);
				zeilenlist.add(2 + topRows);
			} else if (s.trim().equals(IConstants.INFO_REGEL_ZEILE_3)) {
				zeilenlist.add(3 + topRows);
			} else if (s.trim().equals(IConstants.INFO_REGEL_ZEILE_4)) {
				zeilenlist.add(4 + topRows);
			} else if (s.trim().equals(IConstants.INFO_REGEL_ZEILE_5)) {
				zeilenlist.add(5 + topRows);
			}
		}
		// Spalten
		int leftCols = 1;
		List<String> strSpalten = Arrays.asList(spalten.split(","));
		spaltenlist = new ArrayList<Integer>();
		for (String s : strSpalten) {
			if (s.trim().equals(IConstants.INFO_REGEL_SPALTE_1)) {
				spaltenlist.add(0 + leftCols);
			} else if (s.trim().equals(IConstants.INFO_REGEL_SPALTE_2)) {
				spaltenlist.add(1 + leftCols);
			} else if (s.trim().equals(IConstants.INFO_REGEL_SPALTE_3)) {
				spaltenlist.add(2 + leftCols);
			} else if (s.trim().equals(IConstants.INFO_REGEL_SPALTE_4)) {
				spaltenlist.add(3 + leftCols);
			} else if (s.trim().equals(IConstants.INFO_REGEL_SPALTE_5)) {
				spaltenlist.add(4 + leftCols);
			}
		}
	}

	public Regel(String regeltyp, List<Integer> zeilen, List<Integer> spalten,
			String operator, List<String> kriterien, String fehlermeldung,
			Boolean aktiv) {
		this.regeltyp = regeltyp;
		this.zeilenlist = zeilen;
		this.spaltenlist = spalten;
		this.operator = operator;
		this.kriterienlist = kriterien;
		this.fehlermeldung = fehlermeldung;
		this.aktiv = aktiv;
	}

	public void check(MenueComponent mc, MenueplanGridLayout mp) {
		if (getZeilen() != null && getSpalten() != null) {
			if (this.getZeilen().indexOf(mc.row) >= 0
					|| this.getZeilen().indexOf(-1) >= 0) {
				if (this.getSpalten().indexOf(mc.col) >= 0
						|| this.getSpalten().indexOf(-1) >= 0) {
					this.findeRegel(mc, mp);
				}
			}
		}
	}

	public void findeRegel(MenueComponent mc, MenueplanGridLayout mp) {
		if (regeltyp.equals(IConstants.INFO_REGEL_REGELTYP_MENUE)) {
			mc.addFehlerRegel(checkName(mc, mp));
		} else if (regeltyp.trim().equals(
				IConstants.INFO_REGEL_REGELTYP_MENUEART)) {
			mc.addFehlerRegel(checkMenueart(mc, mp));
		} else if (regeltyp.equals(IConstants.INFO_REGEL_REGELTYP_GESCHMACK)) {
			mc.addFehlerRegel(checkGeschmack(mc, mp));
		} else if (regeltyp.equals(IConstants.INFO_REGEL_REGELTYP_FUSSNOTE)) {
			mc.addFehlerRegel(checkFussnote(mc, mp));
		} else if (regeltyp.equals(IConstants.INFO_REGEL_REGELTYP_ZUBEREITUNG)) {
			mc.addFehlerRegel(checkZubereitung(mc, mp));
		} else if (regeltyp.equals(IConstants.INFO_REGEL_REGELTYP_AUFWAND)) {
			mc.addFehlerRegel(checkAufwand(mc, mp));
		}
	}

	// Zubereitung Rezept
	// Fußnote Menü
	// Geschmack Menü
	// Aufwand Menü
	// Menüart Menü
	// Name Menü

	private Regel checkZubereitung(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (operator.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
			if (menue.getFussnoten() != null && menue.getFussnoten().size() > 0) {
				for (Fussnote fs : menue.getFussnoten()) {
					if (kriterienlist.indexOf(fs.getBezeichnung()) >= 0) {
						return null;
					}
					return this;
				}
			} else
				return this;

		} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
			if (menue.getFussnoten() != null && menue.getFussnoten().size() > 0) {
				for (Fussnote fs : menue.getFussnoten()) {
					if (kriterienlist.indexOf(fs.getBezeichnung()) == -1) {
						return null;
					} else
						return this;
				}
			} else
				return null;

		} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MAXIMAL)) {
			if (menue.getFussnoten() != null) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (Fussnote fs : mc.getMenue().getFussnoten()) {
					count = 0;
					for (int col = 0; col < grid.getColumns(); ++col) {
						for (int row = 0; row < grid.getRows(); ++row) {
							if ((zeilenlist.indexOf(row) >= 0 || zeilenlist
									.indexOf(-1) >= 0)
									&& (spaltenlist.indexOf(col) >= 0 || spaltenlist
											.indexOf(-1) >= 0)) {
								if (grid.getComponent(col, row) instanceof MenueComponent) {
									MenueComponent tmp = (MenueComponent) grid
											.getComponent(col, row);
									if (tmp.getMenue().getFussnoten()
											.indexOf(fs) >= 0) {
										if (tmp.getFehlerRegeln() != null) {
											if (tmp.getFehlerRegeln().indexOf(
													this) == -1) {
												++count;
												if (count > maxValue) {
													return this;
												}
											}
										} else {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									}
								}
							}
						}
					}
				}
			} else
				return null;
		} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MINIMAL)) {

		}
		return null;
	}

	private Regel checkFussnote(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (operator.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
			if (menue.getFussnoten() != null && menue.getFussnoten().size() > 0) {
				for (Fussnote fs : menue.getFussnoten()) {
					if (kriterienlist.indexOf(fs.getBezeichnung()) >= 0) {
						return null;
					}
					return this;
				}
			} else
				return this;

		} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
			if (menue.getFussnoten() != null && menue.getFussnoten().size() > 0) {
				for (Fussnote fs : menue.getFussnoten()) {
					if (kriterienlist.indexOf(fs.getBezeichnung()) == -1) {

					} else
						return this;
				}
			} else
				return null;

		}  else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MAXIMAL)) {
			if (menue.getFussnoten() != null) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (Fussnote fs : mc.getMenue().getFussnoten()) {
					count = 0;
					for (int col = 0; col < grid.getColumns(); ++col) {
						for (int row = 0; row < grid.getRows(); ++row) {
							if ((zeilenlist.indexOf(row) >= 0 || zeilenlist
									.indexOf(-1) >= 0)
									&& (spaltenlist.indexOf(col) >= 0 || spaltenlist
											.indexOf(-1) >= 0)) {
								if (grid.getComponent(col, row) instanceof MenueComponent) {
									MenueComponent tmp = (MenueComponent) grid
											.getComponent(col, row);
									if (tmp.getMenue().getFussnoten()
											.indexOf(fs) >= 0) {
										if (tmp.getFehlerRegeln() != null) {
											if (tmp.getFehlerRegeln().indexOf(
													this) == -1) {
												++count;
												if (count > maxValue) {
													return this;
												}
											}
										} else {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									}
								}
							}
						}
					}
				}
			} else
				return null;
		}
		return null;
	}

	private Regel checkGeschmack(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (menue.getGeschmack() != null) {
			if (operator.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				if (kriterienlist.indexOf(menue.getGeschmack().getBezeichnung()
						.toString()) == -1) {
					return this;
				}
			} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
				if (kriterienlist.indexOf(menue.getGeschmack().getBezeichnung()
						.toString()) >= 0) {
					return this;
				}
			} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MAXIMAL)) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((zeilenlist.indexOf(row) >= 0 || zeilenlist
								.indexOf(-1) >= 0)
								&& (spaltenlist.indexOf(col) >= 0 || spaltenlist
										.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid
										.getComponent(col, row);
								if (mc.getMenue().getGeschmack()
										.equals(tmp.getMenue().getGeschmack())) {
									if (tmp.getFehlerRegeln() != null) {
										if (tmp.getFehlerRegeln().indexOf(this) == -1) {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									} else {
										++count;
										if (count > maxValue) {
											return this;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Regel checkAufwand(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (menue.getGeschmack() != null) {
			if (operator.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				if (kriterienlist.indexOf(menue.getGeschmack().getBezeichnung()
						.toString()) == -1) {
					return this;
				}
			} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
				if (kriterienlist.indexOf(menue.getGeschmack().getBezeichnung()
						.toString()) >= 0) {
					return this;
				}
			} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MAXIMAL)) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((zeilenlist.indexOf(row) >= 0 || zeilenlist
								.indexOf(-1) >= 0)
								&& (spaltenlist.indexOf(col) >= 0 || spaltenlist
										.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid
										.getComponent(col, row);
								if (mc.getMenue().getKoch()
										.equals(tmp.getMenue().getKoch())
										&& mc.getMenue().getAufwand()) {
									if (tmp.getFehlerRegeln() != null) {
										if (tmp.getFehlerRegeln().indexOf(this) == -1) {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									} else {
										++count;
										if (count > maxValue) {
											return this;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Regel checkMenueart(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();
		if (menue.getMenueart() != null) {
			if (operator.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
				if (kriterienlist.indexOf(menue.getMenueart().getBezeichnung()
						.toString()) == -1) {
					return this;
				}
			} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
				if (kriterienlist.indexOf(menue.getMenueart().getBezeichnung()
						.toString()) >= 0) {
					return this;
				}
			} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MAXIMAL)) {
				int count = 0;
				int maxValue = Integer.MAX_VALUE;
				try {
					maxValue = Integer.parseInt(kriterienlist.get(0));
				} catch (NumberFormatException e) {
					// do something! anything to handle the exception.
				}

				DDGridLayout grid = mp.layout;
				for (int col = 0; col < grid.getColumns(); ++col) {
					for (int row = 0; row < grid.getRows(); ++row) {
						if ((zeilenlist.indexOf(row) >= 0 || zeilenlist
								.indexOf(-1) >= 0)
								&& (spaltenlist.indexOf(col) >= 0 || spaltenlist
										.indexOf(-1) >= 0)) {
							if (grid.getComponent(col, row) instanceof MenueComponent) {
								MenueComponent tmp = (MenueComponent) grid
										.getComponent(col, row);
								if (mc.getMenue().getMenueart()
										.equals(tmp.getMenue().getMenueart())) {
									if (tmp.getFehlerRegeln() != null) {
										if (tmp.getFehlerRegeln().indexOf(this) == -1) {
											++count;
											if (count > maxValue) {
												return this;
											}
										}
									} else {
										++count;
										if (count > maxValue) {
											return this;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Regel checkName(MenueComponent mc, MenueplanGridLayout mp) {
		Menue menue = mc.getMenue();

		if (operator.equals(IConstants.INFO_REGEL_OPERATOR_ERLAUBT)) {
			if (kriterienlist.indexOf(menue.getName()) == -1) {

				return this;
			}
		} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_VERBOTEN)) {
			if (kriterienlist.indexOf(menue.getName()) >= 0) {

				return this;
			}
		} else if (operator.equals(IConstants.INFO_REGEL_OPERATOR_MAXIMAL)) {
			int count = 0;
			int maxValue = Integer.MAX_VALUE;
			try {
				maxValue = Integer.parseInt(kriterienlist.get(0));
			} catch (NumberFormatException e) {
				// do something! anything to handle the exception.
			}

			DDGridLayout grid = mp.layout;
			for (int col = 0; col < grid.getColumns(); ++col) {
				for (int row = 0; row < grid.getRows(); ++row) {
					if ((zeilenlist.indexOf(row) >= 0 || zeilenlist.indexOf(-1) >= 0)
							&& (spaltenlist.indexOf(col) >= 0 || spaltenlist
									.indexOf(-1) >= 0)) {
						if (grid.getComponent(col, row) instanceof MenueComponent) {
							MenueComponent tmp = (MenueComponent) grid
									.getComponent(col, row);
							if (mc.getMenue().getName()
									.equals(tmp.getMenue().getName())) {
								if (tmp.getFehlerRegeln() != null) {
									if (tmp.getFehlerRegeln().indexOf(this) == -1) {
										++count;
										if (count > maxValue) {
											return this;
										}
									}
								} else {
									++count;
									if (count > maxValue) {
										return this;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static void speichern(Regel regel) {

		Regel neueregel = new Regel();

		neueregel.setZeile(regel.getZeile());
		neueregel.setSpalte(regel.getSpalte());
		neueregel.setRegeltyp(regel.getRegeltyp());
		neueregel.setOperator(regel.getOperator());
		neueregel.setKriterien(regel.getKriterien());
		neueregel.setFehlermeldung(regel.getFehlermeldung());
		neueregel.setAktiv(regel.getAktiv());
		neueregel.setIgnorierbar(regel.getIgnorierbar());

		try {
			Regelverwaltung.getInstance().createRegel(regel);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void loeschen(Regel regel) {

		try {
			Regelverwaltung.getInstance().deleteRegel(regel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		((Application) UI.getCurrent().getData())
				.showDialog(IConstants.INFO_REGEL_DELETE);
	}
}
