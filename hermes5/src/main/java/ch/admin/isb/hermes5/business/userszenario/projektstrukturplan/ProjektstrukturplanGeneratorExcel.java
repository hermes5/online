/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.projektstrukturplan;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.util.SzenarioItemUtil;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

public class ProjektstrukturplanGeneratorExcel implements Serializable {

    private static final int VERANTWORTLICH_COL = 1;
    private static final int MAIN_COL = 0;

    private static final long serialVersionUID = 1L;


    @Inject
    @SystemProperty(value = "al.projektstrukturplan.ergebnisIndent", fallback = "      ")
    ConfigurationProperty ergebnisIndent;
    @Inject
    @SystemProperty(value = "al.projektstrukturplan.aufgabeIndent", fallback = "    ")
    ConfigurationProperty aufgabeIndent;
    @Inject
    @SystemProperty(value = "al.projektstrukturplan.modulIndent", fallback = "  ")
    ConfigurationProperty modulIndent;

    @Inject
    @SystemProperty(value = "al.projektstrukturplan.font", fallback = "Arial")
    ConfigurationProperty fontName;

    @Inject
    TranslationRepository translationRepository;

    @Inject
    SzenarioItemUtil szenarioItemUtil;

    public void addProjektstrukturPlan(String root, AnwenderloesungRenderingContainer container, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine) {

        Szenario szenario = container.getSzenario();
        SzenarioItem szenarioTree = container.getSzenarioUserData().getSzenarioTree();

        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Workbreakdownstructure_" + localizationEngine.getLanguage());
            addHeader(localizationEngine, wb, sheet);
            XSSFCellStyle ergebnisStyle = getErgebnisStyle(wb);
            XSSFCellStyle modulStyle = getModulStyle(wb);
            XSSFCellStyle defaultStyle = getDefaultStyle(wb);
            int currentRow = 1;
            List<Phase> phasen = szenario.getPhasen();
            for (Phase phase : phasen) {
                currentRow = addPhase(sheet, currentRow, phase, localizationEngine, ergebnisStyle, modulStyle,
                        defaultStyle, szenarioTree) + 1;
            }

            autosize(sheet);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            zipBuilder.addFile(root + "/" + localizationEngine.getLanguage()
                    + "/" + "Workbreakdownstructure_"
                    + localizationEngine.getLanguage() + ".xlsx", out.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private int addPhase(XSSFSheet sheet, int currentRow, Phase phase, LocalizationEngine localizationEngine,
                    XSSFCellStyle ergebnisStyle, XSSFCellStyle modulStyle, XSSFCellStyle defaultStyle,
                    SzenarioItem szenarioTree) {
        XSSFRow row = sheet.createRow(currentRow++);
        XSSFCell phaseCell = row.createCell(MAIN_COL);
        phaseCell.setCellStyle(defaultStyle);
        String phaseName = localizationEngine.localize(phase.getPresentationName());
        phaseCell.setCellValue(isNotBlank(phaseName) ? phaseName.toUpperCase() : phaseName);
        List<Aufgabe> aufgabenInPhase = phase.getAufgaben();
        List<Modul> module = phase.getModule();
        for (Modul modul : module) {
            currentRow = addModul(sheet, currentRow, localizationEngine, ergebnisStyle, modulStyle, defaultStyle,
                    phase, modul, aufgabenInPhase, szenarioTree);
        }
        return currentRow;
    }

    private int addModul(XSSFSheet sheet, int currentRow, LocalizationEngine localizationEngine,
            XSSFCellStyle ergebnisStyle, XSSFCellStyle modulStyle, XSSFCellStyle defaultStyle, Phase phase,
            Modul modul, List<Aufgabe> aufgabenInPhase, SzenarioItem szenarioTree) {

        XSSFRow modulRow = sheet.createRow(currentRow++);
        String modulName = localizationEngine.localize(modul.getPresentationName());
        XSSFCell modulCell = modulRow.createCell(MAIN_COL);
        modulCell.setCellStyle(modulStyle);
        modulCell.setCellValue(modulIndent.getStringValue()
                + (isNotBlank(modulName) ? modulName.toUpperCase() : modulName));
        for (Aufgabe aufgabe : modul.getAufgaben()) {
        	if(aufgabenInPhase.contains(aufgabe)){
                currentRow = addAufgabe(sheet, currentRow, phase, modul, aufgabe, localizationEngine, ergebnisStyle,
                        defaultStyle, szenarioTree);
        	}
        }
        return currentRow;
    }

    private int addAufgabe(XSSFSheet sheet, int currentRow, Phase phase, Modul modul, Aufgabe aufgabe,
            LocalizationEngine localizationEngine, XSSFCellStyle ergebnisStyle, XSSFCellStyle defaultStyle, SzenarioItem szenarioTree) {

        XSSFRow row = sheet.createRow(currentRow++);
        XSSFCell aufgabeCell = row.createCell(MAIN_COL);
        aufgabeCell.setCellStyle(defaultStyle);
        aufgabeCell.setCellValue(aufgabeIndent.getStringValue()
                + localizationEngine.localize(aufgabe.getPresentationName()));
        Rolle verantwortlicheRolle = aufgabe.getVerantwortlicheRolle();
        if (verantwortlicheRolle != null) {
            XSSFCell rollCell = row.createCell(VERANTWORTLICH_COL);
            rollCell.setCellStyle(defaultStyle);
            rollCell.setCellValue(localizationEngine.localize(verantwortlicheRolle.getPresentationName()));
        }

        List<Ergebnis> ergebnisse = aufgabe.getErgebnisse();
        for (Ergebnis ergebnis : ergebnisse) {
            if (szenarioTree == null || modul.isCustom()
                    || szenarioItemUtil.isErgebnisSelected(szenarioTree, phase, modul, aufgabe, ergebnis)) {

                XSSFRow ergebnisRow = sheet.createRow(currentRow++);
                XSSFCell ergebnisCell = ergebnisRow.createCell(MAIN_COL);
                ergebnisCell.setCellStyle(ergebnisStyle);
                ergebnisCell.setCellValue(ergebnisIndent.getStringValue()
                        + localizationEngine.localize(ergebnis.getPresentationName()));
                XSSFCell verantwortlichCell = ergebnisRow.createCell(VERANTWORTLICH_COL);
                verantwortlichCell.setCellStyle(defaultStyle);
                verantwortlichCell.setCellValue(getVerantwortlichFuerErgebnis(localizationEngine, ergebnis));
            }
        }
        return currentRow;
    }

    private String getVerantwortlichFuerErgebnis(LocalizationEngine localizationEngine, Ergebnis ergebnis) {
        List<String> verantwortlichFuerErgebnis = new ArrayList<String>();
        List<Rolle> verantwortlicheRollen = ergebnis.getVerantwortlicheRollen();
        for (Rolle rolle : verantwortlicheRollen) {
            verantwortlichFuerErgebnis.add(localizationEngine.localize(rolle.getPresentationName()));
        }
        String verantwortlich = StringUtil.join(verantwortlichFuerErgebnis, ", ");
        return verantwortlich;
    }

    private void addHeader(LocalizationEngine localizationEngine, XSSFWorkbook wb, XSSFSheet sheet) {
        XSSFRow headerRow = sheet.createRow(0);
        XSSFCellStyle headerStyle = getHeaderStyle(wb);
        addHeaderCell(headerRow, headerStyle, MAIN_COL,
                localizationEngine.text("al_projektstrukturplan_aufgabeergebnis"));
        addHeaderCell(headerRow, headerStyle, VERANTWORTLICH_COL,
                localizationEngine.text("al_projektstrukturplan_verantwortlich"));
    }

    private void autosize(XSSFSheet sheet) {
        sheet.autoSizeColumn(MAIN_COL);
        sheet.autoSizeColumn(VERANTWORTLICH_COL);
    }

    private void addHeaderCell(XSSFRow headerRow, XSSFCellStyle cellStyle, int i, String string) {
        XSSFCell cell = headerRow.createCell(i);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(string);
    }

    private XSSFCellStyle getErgebnisStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setItalic(true);
        font.setFontHeight(10);
        font.setFontName(fontName.getStringValue());
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle getModulStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        font.setFontName(fontName.getStringValue());
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle getDefaultStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setFontHeight(10);
        font.setFontName(fontName.getStringValue());
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle getHeaderStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(184, 204, 228)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont headerFont = wb.createFont();
        headerFont.setFontHeight(12);
        headerFont.setFontName(fontName.getStringValue());
        cellStyle.setFont(headerFont);
        return cellStyle;
    }

}
