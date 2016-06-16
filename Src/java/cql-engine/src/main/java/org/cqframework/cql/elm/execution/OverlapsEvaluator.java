package org.cqframework.cql.elm.execution;

import org.cqframework.cql.execution.Context;
import org.cqframework.cql.runtime.Interval;
import org.cqframework.cql.runtime.Value;

/**
* Created by Chris Schuler on 6/8/2016
*/
public class OverlapsEvaluator extends Overlaps {

  public static Boolean overlaps(Interval left, Interval right) {
    Object leftStart = left.getStart();
    Object leftEnd = left.getEnd();
    Object rightStart = right.getStart();
    Object rightEnd = right.getEnd();

    if (leftStart == null || leftEnd == null || rightStart == null || rightEnd == null) { return null; }

    return (Value.compareTo(leftStart, rightEnd, "<=") && Value.compareTo(rightStart, leftEnd, "<="));
  }

  @Override
  public Object evaluate(Context context) {
    Interval left = (Interval)getOperand().get(0).evaluate(context);
    Interval right = (Interval)getOperand().get(1).evaluate(context);

    if (left != null && right != null) {
      return overlaps(left, right);
    }
    return null;
  }
}