package pkirill;

import pkirill.js.Fetch;
import pkirill.js.JsMemoryAccess;
import pkirill.js.Promise;
import pkirill.js.WebAssembly;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSError;
import org.teavm.jso.core.JSString;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.typedarrays.ArrayBuffer;
import pkirill.wasmTest.TestWasmDll;

import java.util.Arrays;

public class Client {

  @JSBody(params = { "s" }, script = "console.info(s);")
  public static native void consoleInfo(String s);

  @JSBody(params = { "s", "b" }, script = "console.info(s + b);")
  public static native void consoleInfo(String s, JSObject b);

  public static void main(String[] args) {
    Fetch.fetch(TestWasmDll.module)
        .then(Fetch.Response::arrayBuffer)
        .then(Client::instantiate)
        .then(Client::onLoad, Client::onError);
  }

  static Promise<WebAssembly.ModuleWithInstance<TestWasmDll.Exports>> instantiate(ArrayBuffer b) {
    return WebAssembly.instantiate(b, TestWasmDll.wasmImportObject(Client::toWasm));
  }

  static void onLoad(WebAssembly.ModuleWithInstance<TestWasmDll.Exports> exportsModuleWithInstance) {
    TestWasmDll.Exports exports = exportsModuleWithInstance.instance().exports();
    JsMemoryAccess access = exports.memory().memoryAccess();
    int r = exports.callToCpp();
    consoleInfo("onLoad: " + r);

    addDiv("getC8String returns: " + access.readByteString(exports.getC8String()));
    addDiv("getC16String returns: " + access.readChar16String(exports.getC16String()));
    float[] floatArray = access.floatArray(exports.getCFloatArray8(), 8);
    addDiv("float array: " + Arrays.toString(floatArray));
   }

  static void onError(JSError error) {
    consoleInfo("on error ", JSString.valueOf(error.getMessage()));
  }

  static int toWasm(int a, int b) {
    addDiv("a = " + a);
    addDiv("b = " + b);
    return a + b;
  }

  static void addDiv(String s) {
    HTMLDocument document = HTMLDocument.current();
    HTMLElement div = document.createElement("div");
    div.appendChild(document.createTextNode(s));
    document.getBody().appendChild(div);
  }
}
