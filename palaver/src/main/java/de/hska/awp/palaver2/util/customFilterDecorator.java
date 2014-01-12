package de.hska.awp.palaver2.util;

import java.text.DateFormat;
import java.util.Locale;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.datefield.Resolution;
//https://github.com/tepi/FilteringTable/blob/master/src/org/tepi/filtertable/demo/DemoFilterDecorator.java
public class customFilterDecorator implements FilterDecorator {

	@Override
	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		return null;
	}

	@Override
	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		return null;
	}

	@Override
	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
		// if ("Compress".equals(propertyId)) {
		// return value ? "Validated" : "Not validated";
		// }
		// returning null will output default value
		return value ? "Ja" : "Nein";
	}

	@Override
	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		return value ? new ThemeResource("img/tick_circle.ico") : new ThemeResource("img/cross_circle.ico");
	}

	@Override
	public boolean isTextFilterImmediate(Object propertyId) {
		return false;
	}

	@Override
	public int getTextChangeTimeout(Object propertyId) {
		return 0;
	}

	@Override
	public String getFromCaption() {
		return "Start date:";
	}

	@Override
	public String getToCaption() {
		return "End date:";
	}

	@Override
	public String getSetCaption() {
		return null;
	}

	@Override
	public String getClearCaption() {
		return null;
	}

	@Override
	public Resolution getDateFieldResolution(Object propertyId) {
		return Resolution.DAY;
	}

	public DateFormat getDateFormat(Object propertyId) {
		return null;
	}

	@Override
	public String getAllItemsVisibleString() {
		return null;
	}

	@Override
	public String getDateFormatPattern(Object propertyId) {
		return null;
	}

	@Override
	public Locale getLocale() {
		return Locale.GERMANY;
	}

	@Override
	public NumberFilterPopupConfig getNumberFilterPopupConfig() {
		return null;
	}

	@Override
	public boolean usePopupForNumericProperty(Object propertyId) {
		return false;
	}

}
