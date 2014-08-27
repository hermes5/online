/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alternative
public class ConfigurationProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger logger = LoggerFactory
			.getLogger(ConfigurationProducer.class);

	private String annotationValue;
	private String annotationFallback;

	ConfigurationProperty() {
	}

	public ConfigurationProperty(SystemProperty annotation) {
		this.annotationValue = annotation.value();
		this.annotationFallback = annotation.fallback();
	}

	public boolean isNullOrEmpty() {
		return getStringValue() == null || "".equals(getStringValue());
	}

	public boolean getBooleanValue() {
		return isNullOrEmpty() ? false : "true"
				.equalsIgnoreCase(getStringValue())
				|| "yes".equalsIgnoreCase(getStringValue());
	}

	public Integer getIntegerValue() {
		return isNullOrEmpty() ? null : Integer.valueOf(getStringValue());
	}

	public String toString() {
		return "Property (" + getStringValue() + ")";
	}

	public String getStringValue() {
		String key = annotationValue;
		String value = System.getProperty(key);

		if (value == null) {
			value = annotationFallback;

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Configuration property {} not found. Falling back to default value {}",
						key, value);
			}
		}

		return value;
	}

	public Float getFloatValue() {
		return isNullOrEmpty() ? null : Float.valueOf(getStringValue());
	}

    public Long getLongValue() {
        return isNullOrEmpty() ? null : Long.valueOf(getStringValue());
    }
}
