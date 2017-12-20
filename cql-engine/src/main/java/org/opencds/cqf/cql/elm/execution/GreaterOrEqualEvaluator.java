package org.opencds.cqf.cql.elm.execution;

import org.opencds.cqf.cql.execution.Context;
import org.opencds.cqf.cql.runtime.*;

import java.math.BigDecimal;

/*
>=(left Integer, right Integer) Boolean
>=(left Decimal, right Decimal) Boolean
>=(left Quantity, right Quantity) Boolean
>=(left DateTime, right DateTime) Boolean
>=(left Time, right Time) Boolean
>=(left String, right String) Boolean

The greater or equal (>=) operator returns true if the first argument is greater than or equal to the second argument.
For comparisons involving quantities, the dimensions of each quantity must be the same, but not necessarily the unit.
  For example, units of 'cm' and 'm' are comparable, but units of 'cm2' and  'cm' are not.
For comparisons involving date/time or time values with imprecision, note that the result of the comparison may be null,
  depending on whether the values involved are specified to the level of precision used for the comparison.
If either argument is null, the result is null.
*/

/**
 * Created by Bryn on 5/25/2016.
 */
public class GreaterOrEqualEvaluator extends org.cqframework.cql.elm.execution.GreaterOrEqual {

  public static Boolean greaterOrEqual(Object left, Object right) {

    if (left == null || right == null) {
        return null;
    }

      if (left instanceof Integer && right instanceof Integer) {
          return ((Integer) left).compareTo((Integer) right) >= 0;
      }

      else if (left instanceof BigDecimal && right instanceof BigDecimal) {
          return ((BigDecimal) left).compareTo((BigDecimal) right) >= 0;
      }

      else if (left instanceof Quantity && right instanceof Quantity) {
          if (((Quantity) left).getValue() == null || ((Quantity) right).getValue() == null) {
              return null;
          }
          return ((Quantity) left).compareTo((Quantity) right) >= 0;
      }

      else if (left instanceof DateTime && right instanceof DateTime) {
          Integer i = ((DateTime) left).compareTo((DateTime) right);
          return i == null ? null : i >= 0;
      }

      else if (left instanceof Time && right instanceof Time) {
          Integer i = ((Time) left).compareTo((Time) right);
          return i == null ? null : i >= 0;
      }

      else if (left instanceof String && right instanceof String) {
          return ((String) left).compareTo((String) right) >= 0;
      }

      else if (left instanceof Uncertainty && right instanceof Integer) {
          if (InEvaluator.in(right, ((Uncertainty) left).getUncertaintyInterval(), null)) {
              return null;
          }
          return ((Integer)((Uncertainty) left).getUncertaintyInterval().getStart()).compareTo((Integer) right) >= 0;
      }

      throw new IllegalArgumentException(
              String.format("Cannot perform greater than or equal operator on types %s and %s",
                      left.getClass().getSimpleName(), right.getClass().getSimpleName()));
  }

    @Override
    public Object evaluate(Context context) {
        Object left = getOperand().get(0).evaluate(context);
        Object right = getOperand().get(1).evaluate(context);

        return context.logTrace(this.getClass(), greaterOrEqual(left, right), left, right);
    }
}
