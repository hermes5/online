/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;

public class RollenAssembler {

    

    public List<Rolle> assembleRollen(Map<String, MethodElement> index, List<Role> roles, List<RoleSet> rolesets) {

        return getRollen(index, roles, getRollenGruppen(index, rolesets));
    }

    private List<Rolle> getRollen(Map<String, MethodElement> index, List<Role> roles, List<RollenGruppe> rollenGruppen) {
        List<Rolle> rollen = new ArrayList<Rolle>();
        for (Role role : roles) {
            rollen.add(assembleRolle(index, rollenGruppen, role));
        }
        return rollen;
    }

    private Rolle assembleRolle(Map<String, MethodElement> index, List<RollenGruppe> rollenGruppen, Role role) {
        Rolle rolle = new Rolle(role, index);
        for (RollenGruppe rollenGruppe : rollenGruppen) {
            if (rollenGruppe.getRollenIds().contains(rolle.getId())) {
                rolle.addRollenGruppe(rollenGruppe);
            }
        }
        return rolle;
    }

    private List<RollenGruppe> getRollenGruppen(Map<String, MethodElement> index, List<RoleSet> rolesets) {
        List<RollenGruppe> rollenGruppen = new ArrayList<RollenGruppe>();
        for (RoleSet roleset : rolesets) {
            rollenGruppen.add(new RollenGruppe(roleset, index));
        }
        return rollenGruppen;
    }

    
}
