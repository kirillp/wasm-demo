package pkirill.js;

import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSError;

public interface Promise<T extends JSObject> extends JSObject {
  void then(F<T> onResult, F<JSError> onError);
  <R extends JSObject> Promise<R> then(PromiseF<R, T> supplier);

  @JSFunctor interface F<F extends JSObject> extends JSObject {
    void f(F t);
  }
  @JSFunctor interface PromiseF<R extends JSObject, F extends JSObject> extends JSObject {
    Promise<R> f(F t);
  }
}
