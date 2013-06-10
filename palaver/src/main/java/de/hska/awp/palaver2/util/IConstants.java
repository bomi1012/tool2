/**
 * Created by Sebastian Walz
 * 16.04.2013 14:54:32
 */
package de.hska.awp.palaver2.util;

public interface IConstants
{

	// DB Verbindung
//  Team1
//	String		DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/palaver?user=root&password=awp";
//  Team2
	String		DB_CONNECTION_URL = "jdbc:mysql://localhost:1433/palaver?user=root&password=root";
//	String		DB_CONNECTION_URL = "jdbc:mysql://localhost:1433/palaver?user=palaver&password=Fa48Hsd42J";
	
	// Menuepunkte mit Unterpunkten
	String		MENU_ARTIKEL_HEADLINE = "Artikel";
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
	String		MENU_BESTELLUNG_NEW_RANDOM = "Neue Bestellung anlegen";
	String		MENU_BESTELLUNG_NEW = "Neue Bestellung";
	String 		MENU_BESTELLUNG_BEARBEITEN = "Bestellung bearbeiten";
	String 		MENU_BESTELLUNG_ANZEIGEN = "Alle Bestellungen anzeigen";
	String		MENU_BESTELLUNG_GENERATE = "Neue Bestellungen generieren";
	String		MENU_NACHRICHT_ANZEIGEN = "Nachrichten anzeigen";
	String		MENU_NACHRICHT_NEU = "Nachrichten erstellen";
	String		MENU_NACHRICHT_HEADLINE = "Nachrichten";
	String		MENU_MENGENEINHEIT_ANZEIGEN = "Mengeneinheiten";
	String		MENU_MENGENEINHEIT_NEU = "Mengeneinheit anlegen";
	String		MENU_KATEGORIE_ANZEIGEN = "Kategorien";
	String		MENU_ROLLEN_ANZEIGEN = "Rollen anzeigen";
	String 		MENU_REZEPT_HEADLINE = "Rezept";
	String 		MENU_REZEPT_NEU = "Anlegen";
	String 		MENU_REZEPT_ANZEIGEN = "Anzeigen";
	String 		MENU_REZEPT_AENDERN = "�ndern";
	String 		MENU_REZEPT_LOESCHEN = "L�schen";
	String 		MENU_MENUPLAN_HEADLINE = "Men�plan";
	String 		MENU_MENUPLAN_AKTUELL = "Aktueller Men�plan";
	String 		MENU_MENUPLAN_HISTORIE = "Men�plan-Historie";
	String		MENU_KUCHENVERWALTUNG_HEADLINE = "Kuchen";
	String		MENU_KUCHENREZEPT_ANLEGEN = "Kuchenrezept Anlegen";
	String		MENU_KUCHENREZEPT_ANZEIGEN = "Kuchenrezept Anzeigen";
	String		MENU_KUCHENPLAN_AKTUELL = "Aktueller Kuchenplan";
	String 		MENU_FUSSNOTE = "Fussnoten";
	String 		MENU_GESCHMACK = "Geschm�cker";
	String 		MENU_REZEPTART = "Rezeptarten";
	String		MENU_MENUEART="Men�arten";
	String 		MENU_ZUBEREITUNG = "Zubereitungen";
	String		MENU_LOGOUT = "Logout";
	String 		MENU_MENUE_HEADLINE = "Men�";
	String 		MENU_MENUE_ANLEGEN = " Men� anlegen";
	String 		MENU_MENUE_SUCHEN = " Men� suchen";
	String 		MENU_REGEL = "Regeln";
	String 		MENU_HEADER = "Kopfzeile anzeigen/ausblenden";
	
	// Beschriftungen fuer Knoepfe
	String		BUTTON_SHOW_FILTER = "Filter anzeigen";
	String		BUTTON_HIDE_FILTER = "Filter ausblenden";
	String		BUTTON_SAVE = "Speichern";
	String		BUTTON_SENDEN = "Senden";
	String		BUTTON_DISCARD = "Verwerfen";
	String		BUTTON_ADD = "Hinzuf�gen";
	String		BUTTON_NEW = "Neu";
	String		BUTTON_EDIT = "Bearbeiten";
	String		BUTTON_OK = "OK";
	String		BUTTON_DELETE = "L�schen";
	String		BUTTON_JA = "Ja";
	String 		BUTTON_NEIN = "Nein";
	String		BUTTON_EMAILVERSAND = "Per Email versenden";
	String		BUTTON_DOWNLOAD = "Download";
	String		BUTTON_BACK = "Zur�ck";
	String		BUTTON_ALL_ORDERS = "Alle Bestellungen";
	String 		BUTTON_REZEPTSAVEASMENUE = "Als Men� Speichern";	
	String		BUTTON_SELECT = "Ausw�hlen";

	
	// Icons fuer Knoepfe
	String		BUTTON_DELETE_ICON = "img/delete.ico";
	String		BUTTON_SAVE_ICON = "img/save.ico";
	String		BUTTON_DISCARD_ICON = "img/cross.ico";
	String 		BUTTON_ADD_ICON = "img/add.ico";
	String 		BUTTON_NEW_ICON = "img/new.ico";
	String 		BUTTON_EDIT_ICON = "img/edit.ico";
	String		BUTTON_OK_ICON = "img/check.ico";
	String		BUTTON_EXCEL_ICON = "img/excel1.ico";
//	String		BUTTON_EMAILVERSAND_ICON = "";
	
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
	
	String 		INFO_MENUE_ALS_REZEPT = "Bitte weiteren Felder pr�fen!";
	String 		INFO_MENUE_NAME = "Bitte Name eingeben!";
	String 		INFO_MENUE_KOCH = "Bitte Koch w�hlen!";
	String 		INFO_MENUE_MENUEART = "Bitte Men�art w�hlen!";
	String 		INFO_MENUE_GESCHMACK = "Bitte Geschmack w�len!";
	String 		INFO_MENUE_REZEPT = "Bitte Rezept w�hlen!";
	String 		INFO_MENUE_HAUPTGERICHT = "Bitte ein Hauptgericht w�hlen!";
	String 		INFO_MENUE_NUR_HAUPTGERICHT = "Bitte nur ein Hauptgericht w�hlen!";
	
	String		INFO_REGEL_SAVE = "Regel wurde gespeichert!";
	String		INFO_REGEL_DELETE = "Regel wurde gel�scht!";
	String 		INFO_REGEL_REGELTYP = "Bitte Regeltyp w�hlen!";
	String 		INFO_REGEL_OPERATOR = "Bitte Operator w�hlen!";
	String		INFO_REGEL_OPERATOR_ERLAUBT = "erlaubte Werte";
	String 		INFO_REGEL_OPERATOR_VERBOTEN = "verbotene Werte";
	String 		INFO_REGEL_OPERATOR_MINIMAL = "minimale Anzahl";
	String		INFO_REGEL_OPERATOR_MAXIMAL = "maximale Anzahl";
	
	String		INFO_VALID_BEZEICHNUNG = "Bitte Bezeichnung eingeben!";
	String		INFO_VALID_ABKUERZUNG = "Bitte Abk�rzung eingeben!";
	
	String		INFO_FUSSNOTE_SELECT = "Bitte Fussnote ausw�hlen!";
	String		INFO_FUSSNOTE_SAVE = "Fussnote wurde gespeichert!";
	String		INFO_FUSSNOTE_EDIT = "Fussnote wurde ge�ndert!";
	
	String		INFO_GESCHMACK_SELECT = "Bitte Geschmack ausw�hlen!";
	String		INFO_GESCHMACK_SAVE = "Geschmack wurde gespeichert!";
	String		INFO_GESCHMACK_EDIT = "Geschmack wurde ge�ndert!";
	
	String		INFO_ZUBEREITUNG_SELECT = "Bitte Zubereitung ausw�hlen!";
	String		INFO_ZUBEREITUNG_SAVE = "Zubereitung wurde gespeichert!";
	String		INFO_ZUBEREITUNG_EDIT = "Zubereitung wurde ge�ndert!";
	
	String		INFO_MENUEART_SELECT = "Bitte Men�art ausw�hlen!";
	String		INFO_MENUEART_SAVE = "Men�art wurde gespeichert!";
	String		INFO_MENUEART_EDIT = "Men�art wurde ge�ndert!";
	

	// Beschriftung f�r Tabellen�berschriften
	String 		BESTELLGROESSE = "Bestellgr��e";



	
}
