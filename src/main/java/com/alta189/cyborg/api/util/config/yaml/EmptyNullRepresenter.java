/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of cyborg
 *
 * cyborg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * cyborg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.cyborg.api.util.config.yaml;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 * A custom representer that represents null values as empty text instean of {@code null}
 */
public class EmptyNullRepresenter extends Representer {
	public EmptyNullRepresenter() {
		super();
		nullRepresenter = new EmptyRepresentNull();
	}

	protected class EmptyRepresentNull implements Represent {
		@Override
		public Node representData(Object data) {
			return representScalar(Tag.NULL, ""); // Changed "null" to "" so as to avoid writing nulls
		}
	}

	// Code borrowed from snakeyaml (http://code.google.com/p/snakeyaml/source/browse/src/test/java/org/yaml/snakeyaml/issues/issue60/SkipBeanTest.java)
	@Override
	protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
		NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
		Node valueNode = tuple.getValueNode();
		if (valueNode instanceof CollectionNode) {
			// Removed null check
			if (Tag.SEQ.equals(valueNode.getTag())) {
				SequenceNode seq = (SequenceNode) valueNode;
				if (seq.getValue().isEmpty()) {
					return null; // skip empty lists
				}
			}
			if (Tag.MAP.equals(valueNode.getTag())) {
				MappingNode seq = (MappingNode) valueNode;
				if (seq.getValue().isEmpty()) {
					return null; // skip empty maps
				}
			}
		}
		return tuple;
	}
	// End of borrowed code
}
