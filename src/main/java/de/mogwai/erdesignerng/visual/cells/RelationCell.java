/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.mogwai.erdesignerng.visual.cells;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

import de.mogwai.erdesignerng.model.Relation;

public class RelationCell extends DefaultEdge implements ModelCell<Relation> {

	public RelationCell(Relation aRelation, TableCell aImporting,
			TableCell aExporting) {

		super(aRelation);

		GraphConstants.setLineStyle(getAttributes(),
				GraphConstants.STYLE_ORTHOGONAL);
		GraphConstants.setConnectable(getAttributes(), false);
		GraphConstants.setDisconnectable(getAttributes(), false);
		GraphConstants.setLineBegin(getAttributes(),
				GraphConstants.ARROW_DIAMOND);
		GraphConstants.setLineEnd(getAttributes(), GraphConstants.ARROW_LINE);

		setSource(aImporting.getChildAt(0));
		setTarget(aExporting.getChildAt(0));
	}

	public void transferAttributesToProperties(Map aAttributes) {

		Relation theRelation = (Relation) getUserObject();

		Point2D theOffset = (Point2D) GraphConstants.getOffset(aAttributes);
		if (theOffset != null) {

			String theLocation = ((int) theOffset.getX()) + ":"
					+ ((int) theOffset.getY());

			theRelation.setProperty(theRelation.PROPERTY_TEXT_OFFSET,
					theLocation);

		}

		List thePoints = GraphConstants.getPoints(aAttributes);

		if (thePoints != null) {
			StringBuffer theBuffer = new StringBuffer();
			for (Object thePoint : thePoints) {
				Point2D theDoublePoint = (Point2D) thePoint;

				if (theBuffer.length() > 0) {
					theBuffer.append(",");
				}

				theBuffer.append(((int) theDoublePoint.getX()) + ":"
						+ ((int) theDoublePoint.getY()));
			}

			String thePointBuffer = theBuffer.toString();
			theRelation
					.setProperty(theRelation.PROPERTY_POINTS, thePointBuffer);
		}

	}

	public void transferPropertiesToAttributes(Relation aObject) {

		Point2D thePoint = TransferHelper.createPoint2DFromString(aObject
				.getProperty(aObject.PROPERTY_TEXT_OFFSET));
		if (thePoint != null) {
			GraphConstants.setOffset(getAttributes(), thePoint);
		}

		String thePoints = aObject.getProperty(aObject.PROPERTY_POINTS);
		if (thePoints != null) {
			List<Point2D> thePointList = new Vector<Point2D>();

			for (StringTokenizer theSt = new StringTokenizer(thePoints, ","); theSt
					.hasMoreTokens();) {
				thePoint = TransferHelper.createPoint2DFromString(theSt
						.nextToken());
				thePointList.add(thePoint);
			}

			GraphConstants.setPoints(getAttributes(), thePointList);
		}

	}

}
