package de.palaver.alt.service.bestellverwaltung;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import de.palaver.alt.domain.bestellverwaltung.Bestellposition;
import de.palaver.alt.domain.bestellverwaltung.Bestellung;
public class ExcelDatei {

	private static ExcelDatei instance = null;
	DateFormat dateFormat;
	public ExcelDatei() {
		super();
	}

	public static ExcelDatei getInstance() {
		if (instance == null) {
			instance = new ExcelDatei();
		}
		return instance;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public String create(Bestellung bestellung) {
		File file = null;
		try {
			boolean mehrereliefertermine = bestellung.getLieferant().isMehrereliefertermine();
			//TODO: f�r Win 
			String path = "/usr/share/palaver/";
			
			String filename = ("bestellung"
					+ bestellung.getLieferant().getName() + "id"
					+ bestellung.getId() + ".xls").replaceAll(" ", "").toLowerCase();

			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("Bestellung_Cafe_Palaver");

			// Bestellung an ...
			HSSFRow rowName = sheet.createRow((short) 0);
			rowName.createCell((short) 2).setCellValue(
					"Bestellung an " + bestellung.getLieferant().getName());

			dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy");
			
			// Mehrereliefertermine
			if (mehrereliefertermine == true) {
				// Lieferdatum1			
				HSSFRow rowLieferdatum1 = sheet.createRow((short) 2);
//				rowLieferdatum1.createCell((short) 0).setCellValue(
//						"1.Lieferdatum: " + dateFormat.format(bestellung.getLieferdatum()));
				// Lieferdatum2
				HSSFRow rowLieferdatum2 = sheet.createRow((short) 3);
				rowLieferdatum2.createCell((short) 0).setCellValue(
						"2.Lieferdatum: " + dateFormat.format(bestellung.getLieferdatum2()));
			}
			else{
				// Lieferdatum
				HSSFRow rowLieferdatum1 = sheet.createRow((short) 2);
//				rowLieferdatum1.createCell((short) 0).setCellValue(
//						"Lieferdatum: " + dateFormat.format(bestellung.getLieferdatum()));
			}	
			// Header
			HSSFRow rowhead = sheet.createRow((short) 5);
			rowhead.createCell((short) 0).setCellValue("Pos.");
			rowhead.createCell((short) 1).setCellValue("Artkel-Nr.");
			rowhead.createCell((short) 2).setCellValue("Artikelname");
			rowhead.createCell((short) 3).setCellValue("Kategorie");
			rowhead.createCell((short) 4).setCellValue("Gebinde");
			rowhead.createCell((short) 5).setCellValue("Mengeneinheit");
			if (mehrereliefertermine != true) {
				rowhead.createCell((short) 6).setCellValue("Anzahl");
				rowhead.createCell((short) 7).setCellValue("Preis");
			}
			
			else {
				rowhead.createCell((short) 6).setCellValue("Preis");
				rowhead.createCell((short) 7).setCellValue("1. Lieferdatum");
				rowhead.createCell((short) 8).setCellValue("2. Lieferdatum");
			}			

			int i = 6;
			int zahl = 1;
			for (Bestellposition bp : BestellpositionService.getInstance()
					.getBestellpositionenByBestellungId(bestellung.getId())) {
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(zahl);
				row.createCell((short) 1).setCellValue(
						bp.getArtikel().getArtikelnr());
				row.createCell((short) 2).setCellValue(bp.getArtikel().getName());
				row.createCell((short) 3).setCellValue(
						bp.getArtikel().getKategorie().getName());
				row.createCell((short) 4).setCellValue(
						bp.getArtikel().getBestellgroesse());
				row.createCell((short) 5).setCellValue(
						bp.getArtikel().getMengeneinheit().getKurz());
				if (mehrereliefertermine != true) {
					row.createCell((short) 6).setCellValue(bp.getLiefermenge1());
					row.createCell((short) 7).setCellValue(
							bp.getArtikel().getPreis());
				}
				else{
					row.createCell((short) 6).setCellValue(
							bp.getArtikel().getPreis());
					row.createCell((short) 7).setCellValue(bp.getBestellung().getLieferdatum1());
					row.createCell((short) 8).setCellValue(bp.getBestellung().getLieferdatum2());
				}
				++i;
				++zahl;
			}

			dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy HH:mm");
			
			HSSFRow rowEnde = sheet.createRow((short) i+2);
			rowEnde.createCell((short) 2).setCellValue("Bestellung an " + bestellung.getLieferant().getName() +
					" am " + dateFormat.format(new Date()) + " erfolgt.");
			
			//Create Excel
			file = new File(path + filename);
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			System.out.println(ex);

		}
		return file.getAbsolutePath();
	}
}