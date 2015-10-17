package eu.timepit.refined
package internal

import eu.timepit.refined.api.{ RefType, Validate }

/**
 * Helper class that allows the type `T` to be inferred from calls like
 * `[[api.RefType.refine]][P](t)`.
 *
 * See [[http://tpolecat.github.io/2015/07/30/infer.html]] for a detailed
 * explanation of this trick.
 */
final class RefineAux[F[_, _], P](rt: RefType[F]) {

  def apply[T](t: T)(implicit v: Validate[T, P]): Either[String, F[T, P]] = {
    val res = v.validate(t)
    if (res.isPassed) Right(rt.unsafeWrap(t))
    else Left(v.showResult(t, res))
  }
}