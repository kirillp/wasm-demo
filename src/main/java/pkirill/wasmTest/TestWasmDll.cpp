

extern "C" {
  __attribute__((visibility("default"))) int callToCpp();
}

extern "C" {
  extern int jsFunction(int a, int b);
}

int callToCpp() {
  return jsFunction(22, 33);
}
