/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import ch.admin.isb.hermes5.epf.uma.schema.ContentDescription;
import ch.admin.isb.hermes5.epf.uma.schema.DeliveryProcess;
import ch.admin.isb.hermes5.epf.uma.schema.Discipline;
import ch.admin.isb.hermes5.epf.uma.schema.Guidance;
import ch.admin.isb.hermes5.epf.uma.schema.GuidanceDescription;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.epf.uma.schema.TaskDescription;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProductDescription;

public class SzenarioBuilder {

    public static Phase phase(String name) {
        return new Phase(umaphase(name), index());
    }

    public static Phase phaseFull(String name) {
        Phase phase = new Phase(umaphase(name), index());
        List<Aufgabe> aufgaben = phase.getAufgaben();
        Modul modul1 = modul("modul1");
        Ergebnis ergebnis1 = ergebnis("ergebnis1", "url/vorlage1|url/vorlage2|<a href=\"www.dummy.com\">Dummy Link</a>");
        ergebnis1.addVerantwortlicheRolle(rolle("rolle2"));
        Aufgabe aufgabe1 = aufgabe(task("task1"), modul1, ergebnis1, ergebnis("ergebnis2", "url/vorlage2|url/vorlage3"));
        aufgabe1.setVerantwortlicheRolle(rolle("rolle1"));
        aufgaben.add(aufgabe1);
        aufgaben.add(aufgabe(task("task2"), modul1, ergebnis("ergebnis3")));
        
        //The module has a task which don't belong to the actual phase
        aufgabe(task("task3"), modul1, ergebnis("ergebnis4"));
        
        return phase;
    }

    public static Modul modul(String name) {
        return new Modul(discipline(name), index());
    }

    private static Map<String, MethodElement> index(MethodElement... me) {
        HashMap<String, MethodElement> hashMap = new HashMap<String, MethodElement>();
        for (MethodElement methodElement : me) {
            hashMap.put(methodElement.getId(), methodElement);
        }
        return hashMap;
    }

    public static Szenario szenario(String name) {
        Szenario ergebnis = new Szenario(methodConfiguration(name));
        return ergebnis;
    }

    public static DeliveryProcess deliveryProcess(String name) {
        DeliveryProcess dp = new DeliveryProcess();
        dp.setId("dpid_" + name);
        dp.setName(name);
        return dp;
    }

    public static MethodConfiguration methodConfiguration(String name) {
        MethodConfiguration mc = new MethodConfiguration();
        mc.setId("dpid_" + name);
        mc.setName(name);
        return mc;
    }

    public static Ergebnis ergebnis(String name) {
        return new Ergebnis(workProduct(name), index(), false);
    }

    public static Ergebnis ergebnisRequired(String name) {
        return new Ergebnis(workProduct(name), index(), true);
    }

    public static Ergebnis ergebnisWithId(String name, String id) {
        Ergebnis ergebnis =ergebnis(name);
        ergebnis.getElement().setId(id);
        return ergebnis;
    }
    
    public static Ergebnis ergebnis(String name, String vorlagen) {
        Guidance gd = guidance(name, vorlagen);
        WorkProduct workProduct = workProduct(name);
        workProduct.getChecklistOrConceptOrExample().add(
                new JAXBElement<String>(new QName("Example"), String.class, gd.getId()));
        Ergebnis ergebnis = new Ergebnis(workProduct, index(gd), false);
        return ergebnis;
    }

    public static CustomErgebnis customErgebnis(String name, String vorlagen) {
        CustomErgebnis ergebnis = new CustomErgebnis(name, "beschreibung_"+name, vorlagen, "name".getBytes(), new ArrayList<String>(),
                new ArrayList<Rolle>());
        return ergebnis;
    }

    public static Ergebnis ergebnis(String name, Rolle... verantwortlich) {
        WorkProduct workProduct = workProduct(name);
        Ergebnis ergebnis = new Ergebnis(workProduct, index(), false);
        for (Rolle rolle : verantwortlich) {
            ergebnis.addVerantwortlicheRolle(rolle);
        }
        return ergebnis;
    }

    public static Beschreibung beschreibung(String name) {
        return new Beschreibung(guidance(name), index());
    }

    private static Guidance guidance(String name, String vorlagen) {
        Guidance gd = new Guidance();
        gd.setId("gd_id_" + name);
        GuidanceDescription gdd = new GuidanceDescription();
        gd.setPresentation(gdd);
        gdd.setAttachment(vorlagen);
        return gd;
    }

    private static Guidance guidance(String name) {
        Guidance gd = new Guidance();
        gd.setId("gd_id_" + name);
        gd.setName("gd_name_" + name);
        return gd;
    }

    public static Rolle rolle(String name) {
        Rolle rolle = new Rolle(role(name), index());
        return rolle;
    }

    public static RollenGruppe rollenGruppe(String name) {
        RollenGruppe rolle = new RollenGruppe(roleset(name), index());
        return rolle;
    }

    public static RoleSet roleset(String name) {
        RoleSet roleSet = new RoleSet();
        roleSet.setName(name);
        return roleSet;
    }

    public static Rolle rolle(String name, String id) {
        Rolle rolle = new Rolle(role(name), index());
        rolle.getRole().setId(id);
        return rolle;
    }

    private static Role role(String name) {
        Role role = new Role();
        role.setId("roleid_" + name);
        role.setPresentation(presentation(name + "_presentation"));
        role.setName(name);
        return role;
    }

    private static ContentDescription presentation(String name) {
        ContentDescription contentDescription = new ContentDescription();
        contentDescription.setId("id_" + name);
        contentDescription.setName(name);
        contentDescription.setPresentationName("presentation_name_" + name);
        return contentDescription;
    }

    public static WorkProduct workProduct(String name) {
        WorkProduct wp = new WorkProduct();
        wp.setId("wpid_" + name);
        wp.setName(name);
        WorkProductDescription wpd = new WorkProductDescription();
        wpd.setId("presentation_id_" + name);
        wpd.setPurpose("purpose_"+name);
        wp.setPresentation(wpd);
        return wp;
    }

    public static Aufgabe aufgabe(Task task, Modul modul, Ergebnis... ergebnisse) {
        Aufgabe aufgabe = new Aufgabe(task, index());
        aufgabe.addModule(Arrays.asList(modul));
        for (Ergebnis ergebnis : ergebnisse) {
            aufgabe.addErgebnis(ergebnis);
        }
        return aufgabe;
    }

    public static Aufgabe aufgabe(String name, Modul modul, Ergebnis... ergebnisse) {
        return aufgabe(task(name), modul, ergebnisse);
    }

    public static Aufgabe aufgabe(String name, Modul modul, Rolle verantwortlich, Ergebnis... ergebnisse) {
        Aufgabe aufgabe = aufgabe(task(name), modul, ergebnisse);
        aufgabe.setVerantwortlicheRolle(verantwortlich);
        return aufgabe;
    }

    public static Task task(String name) {
        Task task = new Task();
        task.setId("taskid_" + name);
        task.setName(name);
        task.setBriefDescription("brief_description_" + name);
        TaskDescription taskDescription = new TaskDescription();
        taskDescription.setId("presentation_id_" + name);
        taskDescription.setPurpose("purpose_" + name);
        taskDescription.setAlternatives("alternatives_" + name);
        task.setPresentation(taskDescription);
        return task;
    }

    public static ch.admin.isb.hermes5.epf.uma.schema.Phase umaphase(String name) {
        ch.admin.isb.hermes5.epf.uma.schema.Phase phase = new ch.admin.isb.hermes5.epf.uma.schema.Phase();
        phase.setId("phaseid_" + name);
        phase.setName(name);
        return phase;
    }

    public static Discipline discipline(String name) {
        Discipline dis = new Discipline();
        dis.setId("disciplineid_" + name);
        dis.setName(name);
        dis.setBriefDescription("brief_description_" + name);
        dis.setPresentation(presentation(name));
        dis.getPresentation().setMainDescription("main_description_" + name);
        return dis;
    }

}
