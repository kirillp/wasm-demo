package pkirill.wasmTest;

import pkirill.js.WebAssembly;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSMethod;
import org.teavm.jso.JSObject;

public class TestWasmDll {

  public static final String module = "test.wasm";

  @JSBody(
      params = {"f"},
      script = "return { env: { jsFunction: f }};"
  )
  public static native JSObject wasmImportObject(Imports.F f);

  public interface Imports {
    @JSFunctor interface F extends JSObject { int f(int a, int b); }
  }

  public interface Exports extends WebAssembly.MemoryContainer {
    @JSMethod int callToCpp();
  }
}
