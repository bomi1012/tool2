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
	String 		MENU_REZEPT_AENDERN = "Ändern";
	String 		MENU_REZEPT_LOESCHEN = "Löschen";
	String 		MENU_MENUPLAN_HEADLINE = "Menüplan";
	String 		MENU_MENUPLAN_AKTUELL = "Aktueller Menüplan";
	String 		MENU_MENUPLAN_HISTORIE = "Menüplan-Historie";
	String		MENU_KUCHENVERWALTUNG_HEADLINE = "Kuchen";
	String		MENU_KUCHENREZEPT_ANLEGEN = "Kuchenrezept anlegen";
	String		MENU_KUCHENREZEPT_ANZEIGEN = "Alle Kuchenrezepte anzeigen";
	String		MENU_KUCHENPLAN_AKTUELL = "Aktueller Kuchenplan";
	String 		MENU_KUCHENPLAN_HISTORIE = "Kuchenplan-Historie";
	String 		MENU_FUSSNOTE = "Fussnoten";
	String 		MENU_GESCHMACK = "Geschmäcker";
	String 		MENU_REZEPTART = "Rezeptarten";
	String		MENU_MENUEART="Menüarten";
	String 		MENU_ZUBEREITUNG = "Zubereitungen";
	String		MENU_LOGOUT = "Abmelden";
	String 		MENU_MENUE_HEADLINE = "Menü";
	String 		MENU_MENUE_ANLEGEN = " Menü anlegen";
	String 		MENU_MENUE_SUCHEN = " Alle Menüs anzeigen";
	String 		MENU_REGEL = "Regeln";
	String 		MENU_HEADER = "Kopfzeile anzeigen/ausblenden";
	
	// Beschriftungen fuer Knoepfe
	String		BUTTON_SHOW_FILTER = "Filter anzeigen";
	String		BUTTON_HIDE_FILTER = "Filter ausblenden";
	String		BUTTON_SAVE = "Speichern";
	String		BUTTON_SENDEN = "Senden";
	String		BUTTON_DEAKTIVIEREN = "Deaktivieren";
	String		BUTTON_DISCARD = "Verwerfen";
	String		BUTTON_ADD = "Hinzufügen";
	String		BUTTON_NEW = "Erstellen";
	String		BUTTON_EDIT = "Bearbeiten";
	String		BUTTON_OK = "OK";
	String		BUTTON_DELETE = "Löschen";
	String		BUTTON_JA = "Ja";
	String 		BUTTON_NEIN = "Nein";
	String		BUTTON_EMAILVERSAND = "Versenden";
	String		BUTTON_DOWNLOAD = "Download";
	String		BUTTON_BACK = "Zurück";
	String		BUTTON_ALL_ORDERS = "Alle Bestellungen";
	String 		BUTTON_REZEPTSAVEASMENUE = "Als Menü Speichern";	
	String 		BUTTON_REZEPTMENUE = "In Menü überführen";	
	String		BUTTON_SELECT = "Auswählen";
	String 		BUTTON_CLEAR_FILTER = "Filter leeren";
	String 		BUTTON_REZEPT_ARTIKEL_ANLEGEN = "Artikel anlegen";
	String 		BUTTON_ADD_ANSPRECHPARTNER = "Ansprechpartner hinzufügen";
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

	// Beschriftungen für Notifications
	String		INFO_REZEPT_SAVE = "Rezept wurde gespeichert!";
	String		INFO_REZEPT_MENUE_SAVE = "Menü kann nur als Haupgericht gespeichert werden!";
	String		INFO_REZEPT_ZUTATEN = "Bitte Zutaten wählen!";
	String		INFO_REZEPT_MENGE = "Bitte Menge kleiner 100.000 wählen!";
	String		INFO_REZEPT_REZEPTART = "Bitte Rezeptart wählen!";	
	String 		INFO_REZEPT_KOCH = "Bitte Koch wählen!";
	String		INFO_REZEPT_NAME = "Bitte Namen eingeben!";
	String		INFO_NAME_VALID = "Bitte vollständigen Namen eingeben";
	String		INFO_REZEPT_KOCH_VALID = "Bitte vollständigen Koch wählen";
	String		INFO_REZEPT_REZEPTART_VALID = "Bitte vollständige Rezeptart wählen";
	String		INFO_REZEPT_MENUE_SAVE_WINDOW = "Funktion nicht möglich";

	String		INFO_KUCHENREZEPT_NAME = "Bitte Bezeichnung eingeben!";
	String		INFO_KUCHENREZEPT_UPDATE = "Rezept wurde geändert!";
	String 		INFO_KUCHENREZEPT_DELETE = "Kuchenrezept wurde gelöscht!";

	String		INFO_KUCHENPLAN_MENGE = "Bitte Menge kleiner 100.000 wählen!";
	String		INFO_KUCHENPLAN_HISTORIE_DATUMSFORMAT = "Falsches Datumsformat!";
	String		INFO_KUCHENPLAN_HISTORIE_KEINPLAN = "Kein Kuchenplan vorhanden!";
	String		INFO_KUCHENPLAN_HISTORIE_ALT = "Anzeige nur von älteren Kuchenplänen möglich";
	String		INFO_KUCHENPLAN_HISTORIE_KEINDATUM = "Es wurde kein Datum ausgewählt!";
	String		INFO_MENUEPLAN_HISTORIE_KEINPLAN = "Kein Menüplan vorhanden!";
	String		INFO_MENUEPLAN_HISTORIE_ALT = "Anzeige nur von älteren Menüplänen möglich";
	
	String 		INFO_MENUE_ALS_REZEPT = "Bitte weitere Felder prüfen!";
	String 		INFO_MENUE_NAME = "Bitte Name eingeben!";
	String 		INFO_MENUE_KOCH = "Bitte Koch wählen!";
	String 		INFO_MENUE_MENUEART = "Bitte Menüart wählen!";
	String 		INFO_MENUE_GESCHMACK = "Bitte Geschmack wälen!";
	String 		INFO_MENUE_REZEPT = "Bitte Rezept wählen!";
	String 		INFO_MENUE_HAUPTGERICHT = "Bitte ein Hauptgericht wählen!";
	String 		INFO_MENUE_NUR_HAUPTGERICHT = "Bitte nur ein Hauptgericht wählen!";
	
	String		INFO_REGEL_SAVE = "Regel wurde gespeichert!";
	String		INFO_REGEL_EDIT = "Bearbeitete Regel wurde gespeichert!";
	String		INFO_REGEL_DELETE = "Regel wurde gelöscht!";
	String 		INFO_REGEL_ZEILEN = "Bitte gewünschte Zeilen auswählen";
	String 		INFO_REGEL_SPALTEN = "Bitte gewünschte Spalten auswählen";
	String 		INFO_REGEL_REGELTYP = "Bitte Regeltyp wählen!";
	String 		INFO_REGEL_OPERATOR = "Bitte Operator wählen!";
	String		INFO_REGEL_KRITERIEN_ZAHL = "Bitte unter Kriterien eine ganze Zahl eintragen!";
	String		INFO_REGEL_KRITERIEN_TWIN = "Bitte unter Kriterien die gewünschten Werte auswählen!";
	String		INFO_REGEL_FEHLERMELDUNG = "Bitte eine Fehlermeldung angeben die mehr als 5 Zeichen hat!";
	String		INFO_REGEL_OPERATOR_ERLAUBT = "muss enthalten";
	String 		INFO_REGEL_OPERATOR_VERBOTEN = "verbotene Werte";
	String 		INFO_REGEL_OPERATOR_MINIMAL = "minimale Anzahl";
	String		INFO_REGEL_OPERATOR_MAXIMAL = "maximale Anzahl";
	String		INFO_REGEL_REGELTYP_GESCHMACK = "Geschmack";
	String		INFO_REGEL_REGELTYP_MENUE = "Menü";
	String 		INFO_REGEL_REGELTYP_MENUEART = "Menüart";
	String		INFO_REGEL_REGELTYP_ZUBEREITUNG = "Zubereitung";
	String		INFO_REGEL_REGELTYP_AUFWAND = "Aufwand";
	String 		INFO_REGEL_REGELTYP_FUSSNOTE = "Fußnote";
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
	String		INFO_ARTIKEL_MENGENEINHEIT_B = "Bitte Mengeneinheit für die Bestellüng auswählen";
	String		INFO_ARTIKEL_MENGENEINHEIT_K = "Bitte Mengeneinheit für Rezepte auswählen";
	String		INFO_ARTIKEL_KATEGORIE = "Bitte Kategorie auswählen";
	String		INFO_ARTIKEL_GEBINDE = "Bitte Bestellgröße eingeben";
	String		INFO_ARTIKEL_GEBINDE_K = "Bitte Gebinde für Rezepte eingeben";
	String 		INFO_ARTIKEL_LIEFERANTNAME = "Bitte Lieferantenname eingeben";
	String 		INFO_ARTIKEL_MENGENEINHEITNAME = "Bitte Namen eingeben";
	String	 	INFO_ARTIKEL_MENGENEINHEITKURZ = "Bitte Kürzel eingeben";
	String 		INFO_ARTIKEL_KATEGORIENAME = "Bitte Namen eingeben";
	String 		INFO_ARTIKEL_PREIS = "Bitte Preis angeben";
	String 		INFO_ARTIKEL_DURCHSCHNITT = "Bitte Durchschnitt angeben";
	String 		INFO_ARTIKEL_AUSWAEHLEN = "Bitte Artikel auswählen";
	String 		INFO_ARTIKEL_DEAKTIVIEREN ="Artikel wurde deaktiviert";
	
	String 		INFO_Lieferant_NAME = "Bitte Namen eingeben";
	String		INFO_LIEFERANT_AUSWAEHLEN = "Bitte Lieferanten auswählen";
	String 		INFO_LIFERANT_EMAIL = "Bitte Email Adresse angeben";
	
	String		INFO_BESTELLUNG_LIEFERANT ="Bitte Lieferant auswählen";
	String 		INFO_BESTELLUNG_TERMIN1 = "Bitte gültigen Termin 1 eingeben";
	String 		INFO_BESTELLUNG_TERMIN2 = "Bitte gültigen Termin 2 eingeben";
	String		INFO_BESTELLUNG_ARTIKEL = "Bitte Artikel auswählen";
	String 		INFO_BESTELLUNG_AUSWAEHLEN = "Bitte Bestellung auswählen";
	
	String 		INFO_KATEGORIE_AUSWAEHLEN = "Bitte Kategorie auswählen";
	
	String 		INFO_MITARBEITER_NAME = "Bitte Namen eingeben";
	String		INFO_MITARBEITER_VORNAME = "Bitte Vorname eingeben";
	String		INFO_MITARBEITER_BENUTZERNAME = "Bitte Benutzername eingeben";
	String		INFO_MITARBEITER_PASSWORT ="Bitte Passwort eingeben";
	String		INFO_MITARBEITER_AUSWAEHLEN = "Bitte Mitarbeiter auswählen";
	
	String		INFO_VALID_BEZEICHNUNG = "Bitte Bezeichnung eingeben!";
	String		INFO_VALID_ABKUERZUNG = "Bitte Abkürzung eingeben!";
	String 		INFO_VALID_BEZ_DOPPELT = "Bezeichnung bereits vorhanden!";
	String 		INFO_VALID_BEZ_ABK_DOPPELT = "Bezeichnung oder Abkürzung bereits vorhanden!";
	
	String 		INFO_REZEPTANZEIGEN_SELECT = "Bitte Rezept auswählen!";
	String		INFO_MENUEANZEIGEN_SELECT = "Bitte Menü auswählen!";
	String 		INFO_KUCHENREZEPTANZEIGEN_SELECT = "Bitte Kuchenrezept auswählen!";
	
	String		INFO_FUSSNOTE_SELECT = "Bitte Fussnote auswählen!";
	String		INFO_FUSSNOTE_SAVE = "Fussnote wurde gespeichert!";
	String		INFO_FUSSNOTE_EDIT = "Fussnote wurde geändert!";
	
	String 		INFO_MENU_HINZUFUEGEN = "Bitte geben Sie bei Portion in % eine ganze Zahl an!";
	
	String		INFO_GESCHMACK_SELECT = "Bitte Geschmack auswählen!";
	String		INFO_GESCHMACK_SAVE = "Geschmack wurde gespeichert!";
	String		INFO_GESCHMACK_EDIT = "Geschmack wurde geändert!";
	
	String		INFO_ZUBEREITUNG_SELECT = "Bitte Zubereitung auswählen!";
	String		INFO_ZUBEREITUNG_SAVE = "Zubereitung wurde gespeichert!";
	String		INFO_ZUBEREITUNG_EDIT = "Zubereitung wurde geändert!";
	
	String		INFO_MENUEART_SELECT = "Bitte Menüart auswählen!";
	String		INFO_MENUEART_SAVE = "Menüart wurde gespeichert!";
	String		INFO_MENUEART_EDIT = "Menüart wurde geändert!";
	
	String 		INFO_LIEFERANT_SAVE = "Lieferant wurde gespeichert!";
	String 		INFO_LIEFERANT_NOT_SAVE = "Lieferant bereits vorhanden";
	
	String 		INFO_MENGENEINHEIT_AUSWAEHLEN = "Bitte Mengeneinheit auswählen";
	
	String 		INFO_NACHRICHT_TEXT = "Bitte Nachricht eingeben";
	String 		INFO_NACHRICHT_EMPF = "Bitte Empfängerrolle auswählen";
	String 		INFO_INPUT_WERT = "Bitte gültigen Wert eingeben";

	// Beschriftung für Tabellenüberschriften
	String 		BESTELLGROESSE = "Bestellgröße";
	String 		GEBINDE = "Gebinde";

	String 		INFO_REZEPT_DELETE = "Rezept wurde gelöscht!";
	String 		INFO_MENUE_DELETE = "Menü wurde gelöscht!";
	
	// Fußnoten Kuchenplan 
	String 		FUSSNOTEN_KUCHENPLAN = "<div align=center>ohne Gewähr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (oWe) = weizenfrei &nbsp;&nbsp; (oG) = glutenfrei &nbsp;&nbsp; (oE) = eifrei &nbsp;&nbsp; (oL) = laktosefrei <BR> (mM) = mitMandeln &nbsp;&nbsp; (mWa) = mit Walnüssen &nbsp;&nbsp; (mH) = mit Haselnüssen &nbsp;&nbsp; (mA) = mit Alkohol &nbsp;&nbsp;</div>";

	// Fußnoten Menueplan 
	String 		FUSSNOTEN_MENUEPLAN = "<div align=center>ohne Gewähr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (v) = vegan &nbsp;&nbsp; (vm) = vegan mögl. &nbsp;&nbsp; (veg.m) = vegetarisch mögl. &nbsp;&nbsp; (Z) = ohne Zwiebel &nbsp;&nbsp; (Zm) = ohne Zwiebel mögl. <BR> (K) = ohne Knoblauch &nbsp;&nbsp; (Km) = ohne Knoblauch mögl. &nbsp;&nbsp; (W) = ohne Weizen &nbsp;&nbsp; (Wm) = ohne Weizen mögl. &nbsp;&nbsp; (M) = ohne KuhMilch &nbsp;&nbsp; (Mm) = ohne KuhMilch mögl.</div>";












































	
}
