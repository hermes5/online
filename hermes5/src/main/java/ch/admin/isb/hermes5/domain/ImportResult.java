/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.admin.isb.hermes5.util.StringUtil;

public class ImportResult implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<String> errors;
    private List<String> successResults;

    public boolean isSuccess() {
        return getErrors().isEmpty();
    }

    public void addError(String error) {
        getErrors().add(error);
    }

    public void addSuccessResult(String result) {
        getSuccessResults().add(result);
    }
    public int getNumberOfSuccessResults() {
        return getSuccessResults().size();
    }

    public List<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        return errors;
    }

    public List<String> getSuccessResults() {
        if (successResults == null) {
            successResults = new ArrayList<String>();
        }
        return successResults;
    }

    public void add(ImportResult other) {
        if (other != null) {
            this.getErrors().addAll(other.getErrors());
            this.getSuccessResults().addAll(other.getSuccessResults());
        }
    }

    @Override
    public String toString() {
        return "ImportResult\n Success: "+ isSuccess()+"\n" + StringUtil.join(getErrors(), "\n");
    }
}
