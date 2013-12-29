/**
 * Created by Sebastian Walz
 * 16.04.2013 14:54:32
 */
package de.hska.awp.palaver2.util;

public interface IConstants
{

// DB Verbindung
//  Team
//String		DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/palaver?user=root&password=root";
//  Team
String		DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/test?user=root&password=root";
//	Produktiv
//String		DB_CONNECTION_URL = "jdbc:mysql://localhost:1433/palaver?user=root&password=BaDAbU352M";
//	TEST
//String		DB_CONNECTION_URL = "jdbc:mysql://localhost:1433/test?user=root&password=BaDAbU352M";
	
	// Menuepunkte mit Unterpunkten
	String		MENU_ARTIKEL_HEADLINE = "Artikel";
	String		MENU_SONSTIGES = "Sonstiges...";
	String		MENU_ARTIKEL_NEU = "Artikel anlegen";
	String		MENU_ARTIKEL_ANZEIGEN = "Alle Artikel anzeigen";
	String		MENU_LIEFERANT_HEADLINE = "Lieferant";
	String		MENU_LIEFERANT_NEW = "Lieferant anlegen";
	String		MENU_LIEFERANT_ANZEIGEN = "Alle Lieferanten anzeigen";
	String		MENU_MITARBEITER_HEADLINE = "Mitarbeiter";
	String		MENU_MITARBEITER_ANZEIGEN = "Alle Mitarbeiter anzeigen";
	String      MENU_MITARBEITER_NEU = "Mitarbeiter anlegen";
	String		MENU_EINSTELLUNGEN_HEADLINE = "Einstellungen";
	String		MENU_BESTELLUNG_HEADLINE = "Bestellung";
	String		MENU_BESTELLUNG_NEW_RANDOM = "Bestellung anlegen";
	String		MENU_BESTELLUNG_NEW = "Neue Bestellung";
	String 		MENU_BESTELLUNG_BEARBEITEN = "Bestellung bearbeiten";
	String 		MENU_BESTELLUNG_ANZEIGEN = "Alle Bestellungen anzeigen";
	String		MENU_BESTELLUNG_GENERATE = "Bestellungen generieren";
	String		MENU_NACHRICHT_ANZEIGEN = "Nachrichten anzeigen";
	String		MENU_NACHRICHT_NEU = "Nachricht erstellen";
	String		MENU_NACHRICHT_HEADLINE = "Nachrichten";
	String		MENU_MENGENEINHEIT_ANZEIGEN = "Mengeneinheiten";
	String		MENU_MENGENEINHEIT_NEU = "Mengeneinheit anlegen";
	String		MENU_KATEGORIE_ANZEIGEN = "Kategorien";
	String		MENU_ROLLEN_ANZEIGEN = "Rollen anzeigen";
	String 		MENU_REZEPT_HEADLINE = "Rezept";
	String 		MENU_REZEPT_NEU = "Rezept anlegen";
	String 		MENU_REZEPT_ANZEIGEN = "Alle Rezepte anzeigen";
	String 		MENU_REZEPT_AENDERN = "�ndern";
	String 		MENU_REZEPT_LOESCHEN = "L�schen";
	String 		MENU_MENUPLAN_HEADLINE = "Men�plan";
	String 		MENU_MENUPLAN_AKTUELL = "Aktueller Men�plan";
	String 		MENU_MENUPLAN_HISTORIE = "Men�plan-Historie";
	String		MENU_KUCHENVERWALTUNG_HEADLINE = "Kuchen";
	String		MENU_KUCHENREZEPT_ANLEGEN = "Kuchenrezept anlegen";
	String		MENU_KUCHENREZEPT_ANZEIGEN = "Alle Kuchenrezepte anzeigen";
	String		MENU_KUCHENPLAN_AKTUELL = "Aktueller Kuchenplan";
	String 		MENU_KUCHENPLAN_HISTORIE = "Kuchenplan-Historie";
	String 		MENU_FUSSNOTE = "Fussnoten";
	String 		MENU_GESCHMACK = "Geschm�cker";
	String 		MENU_REZEPTART = "Rezeptarten";
	String		MENU_MENUEART="Men�arten";
	String 		MENU_ZUBEREITUNG = "Zubereitungen";
	String		MENU_LOGOUT = "Abmelden";
	String 		MENU_MENUE_HEADLINE = "Men�";
	String 		MENU_MENUE_ANLEGEN = " Men� anlegen";
	String 		MENU_MENUE_SUCHEN = " Alle Men�s anzeigen";
	String 		MENU_REGEL = "Regeln";
	String 		MENU_HEADER = "Kopfzeile anzeigen/ausblenden";
	
	// Beschriftungen fuer Knoepfe
	String		BUTTON_SHOW_FILTER = "Filter anzeigen";
	String		BUTTON_HIDE_FILTER = "Filter ausblenden";
	String		BUTTON_SAVE = "Speichern";
	String		BUTTON_SENDEN = "Senden";
	String		BUTTON_DEAKTIVIEREN = "Deaktivieren";
	String		BUTTON_DISCARD = "Verwerfen";
	String		BUTTON_ADD = "Hinzuf�gen";
	String		BUTTON_NEW = "Erstellen";
	String		BUTTON_EDIT = "Bearbeiten";
	String		BUTTON_OK = "OK";
	String		BUTTON_DELETE = "L�schen";
	String		BUTTON_JA = "Ja";
	String 		BUTTON_NEIN = "Nein";
	String		BUTTON_EMAILVERSAND = "Versenden";
	String		BUTTON_DOWNLOAD = "Download";
	String		BUTTON_BACK = "Zur�ck";
	String		BUTTON_ALL_ORDERS = "Alle Bestellungen";
	String 		BUTTON_REZEPTSAVEASMENUE = "Als Men� Speichern";	
	String 		BUTTON_REZEPTMENUE = "In Men� �berf�hren";	
	String		BUTTON_SELECT = "Ausw�hlen";
	String 		BUTTON_CLEAR_FILTER = "Filter leeren";
	String 		BUTTON_REZEPT_ARTIKEL_ANLEGEN = "Artikel anlegen";
	String 		BUTTON_ADD_ANSPRECHPARTNER = "Ansprechpartner hinzuf�gen";
	String 		BUTTON_SEND = "Senden";
	
	// Icons fuer Knoepfe
	String		BUTTON_DELETE_ICON = "img/Delete.ico";
	String		BUTTON_SAVE_ICON = "img/save.ico";
	String		BUTTON_DISCARD_ICON = "img/cross.ico";
	String 		BUTTON_ADD_ICON = "img/add.ico";
	String 		BUTTON_NEW_ICON = "img/new.ico";
	String 		BUTTON_SHOW_ICON = "img/show.png";
	String 		BUTTON_FOLDER_ICON = "img/folder.png";
	String		BUTTON_SELECT_ICON = "img/select.png";
	String 		BUTTON_EDIT_ICON = "img/edit.ico";
	String		BUTTON_OK_ICON = "img/check.ico";
	String		BUTTON_EXCEL_ICON = "img/excel1.ico";
	String		BUTTON_EMAILVERSAND_ICON = "img/mail.ico";
	
	
	String 		ICON_ZOOM = "icons/zoom.png";
	String 		ICON_PAGE_EDIT = "icons/page_edit.png";
	String 		ICON_PAGE_ADD = "icons/page_add.png";
	String 		ICON_PAGE_DELETE = "icons/page_delete.png";
	String 		ICON_PAGE_REFRESH = "icons/page_refresh.png";
	String 		ICON_PAGE_SAVE = "icons/page_save.png";
	
	
	String		ICON_YES = "img/tick_circle.ico";
	String		ICON_NO = "img/cross_circle.ico";
	String		ICON_DELETE = "img/Delete.ico";

	// Beschriftungen f�r Notifications
	String		INFO_REZEPT_SAVE = "Rezept wurde gespeichert!";
	String		INFO_REZEPT_MENUE_SAVE = "Men� kann nur als Haupgericht gespeichert werden!";
	String		INFO_REZEPT_ZUTATEN = "Bitte Zutaten w�hlen!";
	String		INFO_REZEPT_MENGE = "Bitte Menge kleiner 100.000 w�hlen!";
	String		INFO_REZEPT_REZEPTART = "Bitte Rezeptart w�hlen!";	
	String 		INFO_REZEPT_KOCH = "Bitte Koch w�hlen!";
	String		INFO_REZEPT_NAME = "Bitte Namen eingeben!";
	String		INFO_NAME_VALID = "Bitte vollst�ndigen Namen eingeben";
	String		INFO_REZEPT_KOCH_VALID = "Bitte vollst�ndigen Koch w�hlen";
	String		INFO_REZEPT_REZEPTART_VALID = "Bitte vollst�ndige Rezeptart w�hlen";
	String		INFO_REZEPT_MENUE_SAVE_WINDOW = "Funktion nicht m�glich";

	String		INFO_KUCHENREZEPT_NAME = "Bitte Bezeichnung eingeben!";
	String		INFO_KUCHENREZEPT_UPDATE = "Rezept wurde ge�ndert!";
	String 		INFO_KUCHENREZEPT_DELETE = "Kuchenrezept wurde gel�scht!";

	String		INFO_KUCHENPLAN_MENGE = "Bitte Menge kleiner 100.000 w�hlen!";
	String		INFO_KUCHENPLAN_HISTORIE_DATUMSFORMAT = "Falsches Datumsformat!";
	String		INFO_KUCHENPLAN_HISTORIE_KEINPLAN = "Kein Kuchenplan vorhanden!";
	String		INFO_KUCHENPLAN_HISTORIE_ALT = "Anzeige nur von �lteren Kuchenpl�nen m�glich";
	String		INFO_KUCHENPLAN_HISTORIE_KEINDATUM = "Es wurde kein Datum ausgew�hlt!";
	String		INFO_MENUEPLAN_HISTORIE_KEINPLAN = "Kein Men�plan vorhanden!";
	String		INFO_MENUEPLAN_HISTORIE_ALT = "Anzeige nur von �lteren Men�pl�nen m�glich";
	
	String 		INFO_MENUE_ALS_REZEPT = "Bitte weitere Felder pr�fen!";
	String 		INFO_MENUE_NAME = "Bitte Name eingeben!";
	String 		INFO_MENUE_KOCH = "Bitte Koch w�hlen!";
	String 		INFO_MENUE_MENUEART = "Bitte Men�art w�hlen!";
	String 		INFO_MENUE_GESCHMACK = "Bitte Geschmack w�len!";
	String 		INFO_MENUE_REZEPT = "Bitte Rezept w�hlen!";
	String 		INFO_MENUE_HAUPTGERICHT = "Bitte ein Hauptgericht w�hlen!";
	String 		INFO_MENUE_NUR_HAUPTGERICHT = "Bitte nur ein Hauptgericht w�hlen!";
	
	String		INFO_REGEL_SAVE = "Regel wurde gespeichert!";
	String		INFO_REGEL_EDIT = "Bearbeitete Regel wurde gespeichert!";
	String		INFO_REGEL_DELETE = "Regel wurde gel�scht!";
	String 		INFO_REGEL_ZEILEN = "Bitte gew�nschte Zeilen ausw�hlen";
	String 		INFO_REGEL_SPALTEN = "Bitte gew�nschte Spalten ausw�hlen";
	String 		INFO_REGEL_REGELTYP = "Bitte Regeltyp w�hlen!";
	String 		INFO_REGEL_OPERATOR = "Bitte Operator w�hlen!";
	String		INFO_REGEL_KRITERIEN_ZAHL = "Bitte unter Kriterien eine ganze Zahl eintragen!";
	String		INFO_REGEL_KRITERIEN_TWIN = "Bitte unter Kriterien die gew�nschten Werte ausw�hlen!";
	String		INFO_REGEL_FEHLERMELDUNG = "Bitte eine Fehlermeldung angeben die mehr als 5 Zeichen hat!";
	String		INFO_REGEL_OPERATOR_ERLAUBT = "muss enthalten";
	String 		INFO_REGEL_OPERATOR_VERBOTEN = "verbotene Werte";
	String 		INFO_REGEL_OPERATOR_MINIMAL = "minimale Anzahl";
	String		INFO_REGEL_OPERATOR_MAXIMAL = "maximale Anzahl";
	String		INFO_REGEL_REGELTYP_GESCHMACK = "Geschmack";
	String		INFO_REGEL_REGELTYP_MENUE = "Men�";
	String 		INFO_REGEL_REGELTYP_MENUEART = "Men�art";
	String		INFO_REGEL_REGELTYP_ZUBEREITUNG = "Zubereitung";
	String		INFO_REGEL_REGELTYP_AUFWAND = "Aufwand";
	String 		INFO_REGEL_REGELTYP_FUSSNOTE = "Fu�note";
	String 		INFO_REGEL_SPALTE_1 = "Montag";
	String		INFO_REGEL_SPALTE_2 = "Dienstag";
	String		INFO_REGEL_SPALTE_3 = "Mittwoch";
	String		INFO_REGEL_SPALTE_4 = "Donnerstag";
	String 		INFO_REGEL_SPALTE_5 = "Freitag";
	String		INFO_REGEL_ZEILE_1 = "Fleischgericht";
	String		INFO_REGEL_ZEILE_2 = "Hauptgericht";
	String		INFO_REGEL_ZEILE_3 = "Pastagericht";
	String		INFO_REGEL_ZEILE_4 = "Suppe/Salat";
	String		INFO_REGEL_ZEILE_5 = "Dessert";
	
	String 		INFO_ARTIKEL_NAME = "Bitte Namen eingeben";
	String		INFO_ARTIKEL_MENGENEINHEIT_B = "Bitte Mengeneinheit f�r die Bestell�ng ausw�hlen";
	String		INFO_ARTIKEL_MENGENEINHEIT_K = "Bitte Mengeneinheit f�r Rezepte ausw�hlen";
	String		INFO_ARTIKEL_KATEGORIE = "Bitte Kategorie ausw�hlen";
	String		INFO_ARTIKEL_GEBINDE = "Bitte Bestellgr��e eingeben";
	String		INFO_ARTIKEL_GEBINDE_K = "Bitte Gebinde f�r Rezepte eingeben";
	String 		INFO_ARTIKEL_LIEFERANTNAME = "Bitte Lieferantenname eingeben";
	String 		INFO_ARTIKEL_MENGENEINHEITNAME = "Bitte Namen eingeben";
	String	 	INFO_ARTIKEL_MENGENEINHEITKURZ = "Bitte K�rzel eingeben";
	String 		INFO_ARTIKEL_KATEGORIENAME = "Bitte Namen eingeben";
	String 		INFO_ARTIKEL_PREIS = "Bitte Preis angeben";
	String 		INFO_ARTIKEL_DURCHSCHNITT = "Bitte Durchschnitt angeben";
	String 		INFO_ARTIKEL_AUSWAEHLEN = "Bitte Artikel ausw�hlen";
	String 		INFO_ARTIKEL_DEAKTIVIEREN ="Artikel wurde deaktiviert";
	
	String 		INFO_Lieferant_NAME = "Bitte Namen eingeben";
	String		INFO_LIEFERANT_AUSWAEHLEN = "Bitte Lieferanten ausw�hlen";
	String 		INFO_LIFERANT_EMAIL = "Bitte Email Adresse angeben";
	
	String		INFO_BESTELLUNG_LIEFERANT ="Bitte Lieferant ausw�hlen";
	String 		INFO_BESTELLUNG_TERMIN1 = "Bitte g�ltigen Termin 1 eingeben";
	String 		INFO_BESTELLUNG_TERMIN2 = "Bitte g�ltigen Termin 2 eingeben";
	String		INFO_BESTELLUNG_ARTIKEL = "Bitte Artikel ausw�hlen";
	String 		INFO_BESTELLUNG_AUSWAEHLEN = "Bitte Bestellung ausw�hlen";
	
	String 		INFO_KATEGORIE_AUSWAEHLEN = "Bitte Kategorie ausw�hlen";
	
	String 		INFO_MITARBEITER_NAME = "Bitte Namen eingeben";
	String		INFO_MITARBEITER_VORNAME = "Bitte Vorname eingeben";
	String		INFO_MITARBEITER_BENUTZERNAME = "Bitte Benutzername eingeben";
	String		INFO_MITARBEITER_PASSWORT ="Bitte Passwort eingeben";
	String		INFO_MITARBEITER_AUSWAEHLEN = "Bitte Mitarbeiter ausw�hlen";
	
	String		INFO_VALID_BEZEICHNUNG = "Bitte Bezeichnung eingeben!";
	String		INFO_VALID_ABKUERZUNG = "Bitte Abk�rzung eingeben!";
	String 		INFO_VALID_BEZ_DOPPELT = "Bezeichnung bereits vorhanden!";
	String 		INFO_VALID_BEZ_ABK_DOPPELT = "Bezeichnung oder Abk�rzung bereits vorhanden!";
	
	String 		INFO_REZEPTANZEIGEN_SELECT = "Bitte Rezept ausw�hlen!";
	String		INFO_MENUEANZEIGEN_SELECT = "Bitte Men� ausw�hlen!";
	String 		INFO_KUCHENREZEPTANZEIGEN_SELECT = "Bitte Kuchenrezept ausw�hlen!";
	
	String		INFO_FUSSNOTE_SELECT = "Bitte Fussnote ausw�hlen!";
	String		INFO_FUSSNOTE_SAVE = "Fussnote wurde gespeichert!";
	String		INFO_FUSSNOTE_EDIT = "Fussnote wurde ge�ndert!";
	
	String 		INFO_MENU_HINZUFUEGEN = "Bitte geben Sie bei Portion in % eine ganze Zahl an!";
	
	String		INFO_GESCHMACK_SELECT = "Bitte Geschmack ausw�hlen!";
	String		INFO_GESCHMACK_SAVE = "Geschmack wurde gespeichert!";
	String		INFO_GESCHMACK_EDIT = "Geschmack wurde ge�ndert!";
	
	String		INFO_ZUBEREITUNG_SELECT = "Bitte Zubereitung ausw�hlen!";
	String		INFO_ZUBEREITUNG_SAVE = "Zubereitung wurde gespeichert!";
	String		INFO_ZUBEREITUNG_EDIT = "Zubereitung wurde ge�ndert!";
	
	String		INFO_MENUEART_SELECT = "Bitte Men�art ausw�hlen!";
	String		INFO_MENUEART_SAVE = "Men�art wurde gespeichert!";
	String		INFO_MENUEART_EDIT = "Men�art wurde ge�ndert!";
	
	String 		INFO_LIEFERANT_SAVE = "Lieferant wurde gespeichert!";
	String 		INFO_LIEFERANT_NOT_SAVE = "Lieferant bereits vorhanden";
	
	String 		INFO_MENGENEINHEIT_AUSWAEHLEN = "Bitte Mengeneinheit ausw�hlen";
	
	String 		INFO_NACHRICHT_TEXT = "Bitte Nachricht eingeben";
	String 		INFO_NACHRICHT_EMPF = "Bitte Empf�ngerrolle ausw�hlen";
	String 		INFO_INPUT_WERT = "Bitte g�ltigen Wert eingeben";

	// Beschriftung f�r Tabellen�berschriften
	String 		BESTELLGROESSE = "Bestellgr��e";
	String 		GEBINDE = "Gebinde";

	String 		INFO_REZEPT_DELETE = "Rezept wurde gel�scht!";
	String 		INFO_MENUE_DELETE = "Men� wurde gel�scht!";
	
	// Fu�noten Kuchenplan 
	String 		FUSSNOTEN_KUCHENPLAN = "<div align=center>ohne Gew�hr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (oWe) = weizenfrei &nbsp;&nbsp; (oG) = glutenfrei &nbsp;&nbsp; (oE) = eifrei &nbsp;&nbsp; (oL) = laktosefrei <BR> (mM) = mitMandeln &nbsp;&nbsp; (mWa) = mit Waln�ssen &nbsp;&nbsp; (mH) = mit Haseln�ssen &nbsp;&nbsp; (mA) = mit Alkohol &nbsp;&nbsp;</div>";

	// Fu�noten Menueplan 
	String 		FUSSNOTEN_MENUEPLAN = "<div align=center>ohne Gew�hr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (v) = vegan &nbsp;&nbsp; (vm) = vegan m�gl. &nbsp;&nbsp; (veg.m) = vegetarisch m�gl. &nbsp;&nbsp; (Z) = ohne Zwiebel &nbsp;&nbsp; (Zm) = ohne Zwiebel m�gl. <BR> (K) = ohne Knoblauch &nbsp;&nbsp; (Km) = ohne Knoblauch m�gl. &nbsp;&nbsp; (W) = ohne Weizen &nbsp;&nbsp; (Wm) = ohne Weizen m�gl. &nbsp;&nbsp; (M) = ohne KuhMilch &nbsp;&nbsp; (Mm) = ohne KuhMilch m�gl.</div>";












































	
}
